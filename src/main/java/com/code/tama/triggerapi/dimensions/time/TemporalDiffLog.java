/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * The edit history of a base dimension, bucketed by chunk.
 * <p>
 * This is the core of the propagation design: rather than eagerly writing into
 * downstream levels (which would force-load chunks nobody has visited, or
 * silently drop the write), edits are appended here and <em>replayed
 * lazily</em> when a downstream chunk is first loaded. A downstream chunk is
 * therefore always: aged terrain from the generator, plus every recorded edit
 * for that chunk position at the appropriate decay depth.
 * <p>
 * Thematically convenient side effect: the future doesn't exist until somebody
 * goes and looks at it.
 * <p>
 * Stored on the <em>base</em> level, since that is the source of truth.
 */
public final class TemporalDiffLog extends SavedData {

	private static final String DATA_NAME = "tts_temporal_diff";

	/**
	 * chunk key ({@link ChunkPos#asLong}) -> edits in that chunk, in application
	 * order.
	 */
	private final Map<Long, List<BlockChangeRecord>> byChunk = new HashMap<>();

	public static TemporalDiffLog get(ServerLevel baseLevel) {
		return baseLevel.getDataStorage().computeIfAbsent(tag -> load(tag, baseLevel), TemporalDiffLog::new, DATA_NAME);
	}

	public TemporalDiffLog() {
	}

	/* ======================== Recording ======================== */

	/**
	 * Records an edit made in the base dimension.
	 *
	 * @param sourceLevel
	 *            the base level (used to sample its heightmap for anchoring)
	 */
	public void record(ServerLevel sourceLevel, BlockPos pos, BlockState newState) {
		int groundY = sourceLevel.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockChangeRecord record = BlockChangeRecord.of(pos, newState, groundY);

		List<BlockChangeRecord> bucket = byChunk.computeIfAbsent(ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4),
				k -> new ArrayList<>());

		// Collapse repeated edits to the same spot -- a player who places and breaks
		// the same
		// block fifty times should cost one record, not fifty.
		long key = record.positionKey();
		bucket.removeIf(existing -> existing.positionKey() == key);
		bucket.add(record);

		setDirty();
	}

	public List<BlockChangeRecord> forChunk(ChunkPos pos) {
		return byChunk.getOrDefault(pos.toLong(), List.of());
	}

	public boolean hasChunk(ChunkPos pos) {
		return byChunk.containsKey(pos.toLong());
	}

	/* ======================== Replay ======================== */

	/**
	 * Applies every recorded edit for {@code chunk}'s position into that chunk,
	 * decayed by {@code steps} and varied by {@code weathering}'s intensity field
	 * -- the same field the terrain pass uses, so a house standing in a
	 * heavily-weathered patch decays harder than one in an untouched one.
	 * <p>
	 * Writes go directly to the {@link ChunkAccess}, <strong>never</strong> through
	 * {@code ServerLevel#setBlock}. This is load-bearing: this method runs from
	 * {@code ChunkEvent.Load}, which fires while the chunk is still being promoted
	 * to FULL status inside {@code protoChunkToFullChunk}. A {@code setBlock} would
	 * route through {@code ServerChunkCache.getChunk(FULL)} and wait on the very
	 * chunk whose promotion is blocked on this event handler returning --
	 * deadlocking the chunk task and hanging the client on "waiting for chunk".
	 * <p>
	 * Idempotent: it writes fixed states from a log (each block's outcome is a
	 * deterministic function of its position, not of how many times this has run),
	 * so re-running it on a chunk that already has them is a no-op. That is why the
	 * caller replays on <em>every</em> load rather than guarding it -- a guard
	 * would silently drop edits made while the chunk was unloaded.
	 */
	public void replayInto(ServerLevel target, ChunkAccess chunk, int steps, TemporalWeathering weathering) {
		ChunkPos chunkPos = chunk.getPos();
		List<BlockChangeRecord> records = forChunk(chunkPos);
		if (records.isEmpty())
			return;

		var generator = target.getChunkSource().getGenerator();
		var randomState = target.getChunkSource().randomState();
		long dimSeed = target.getSeed();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (BlockChangeRecord record : records) {
			int groundY = 0;
			if (record.needsGroundLookup()) {
				groundY = generator.getBaseHeight(record.x(), record.z(), Heightmap.Types.WORLD_SURFACE, target,
						randomState);
			}
			int y = record.resolveY(groundY);
			if (y < target.getMinBuildHeight() || y >= target.getMaxBuildHeight())
				continue;

			cursor.set(record.x(), y, record.z());
			double intensity = weathering.sampleIntensity(record.x(), record.z());
			BlockState decayed = TemporalDecayRegistry.decay(record.sourceState(), steps, cursor, dimSeed, intensity);

			chunk.setBlockState(cursor, decayed, false);
		}

		chunk.setUnsaved(true);
	}

	/* ======================== Persistence ======================== */

	public static TemporalDiffLog load(CompoundTag tag, Level level) {
		TemporalDiffLog log = new TemporalDiffLog();
		var blocks = level.holderLookup(Registries.BLOCK);

		ListTag chunks = tag.getList("chunks", Tag.TAG_COMPOUND);
		for (int i = 0; i < chunks.size(); i++) {
			CompoundTag chunkTag = chunks.getCompound(i);
			long key = chunkTag.getLong("pos");

			ListTag entries = chunkTag.getList("records", Tag.TAG_COMPOUND);
			List<BlockChangeRecord> bucket = new ArrayList<>(entries.size());
			for (int j = 0; j < entries.size(); j++) {
				bucket.add(BlockChangeRecord.load(entries.getCompound(j), blocks));
			}
			log.byChunk.put(key, bucket);
		}
		return log;
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag chunks = new ListTag();
		byChunk.forEach((key, bucket) -> {
			if (bucket.isEmpty())
				return;
			CompoundTag chunkTag = new CompoundTag();
			chunkTag.putLong("pos", key);

			ListTag entries = new ListTag();
			bucket.forEach(record -> entries.add(record.save()));
			chunkTag.put("records", entries);

			chunks.add(chunkTag);
		});
		tag.put("chunks", chunks);
		return tag;
	}
}