/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Tracks which chunks of a downstream level have already had the weathering
 * pass applied.
 * <p>
 * This <strong>must</strong> persist. Weathering is destructive and
 * non-idempotent -- it walks the decay chain, so running it twice turns stone
 * into cobblestone into gravel. An in-memory set would re-weather every chunk
 * after each restart and the world would keep crumbling until everything was
 * gravel.
 * <p>
 * Contrast with the diff-log replay, which <em>is</em> idempotent (it writes
 * fixed states from a log) and therefore runs on every single chunk load with
 * no guard at all.
 */
public final class TemporalChunkState extends SavedData {

	private static final String DATA_NAME = "tts_temporal_chunk_state";

	private final LongSet weathered = new LongOpenHashSet();

	public static TemporalChunkState get(ServerLevel downstreamLevel) {
		return downstreamLevel.getDataStorage().computeIfAbsent(TemporalChunkState::load, TemporalChunkState::new,
				DATA_NAME);
	}

	public TemporalChunkState() {
	}

	/**
	 * Marks a chunk weathered.
	 *
	 * @return true if this chunk had <em>not</em> been weathered before (i.e. the
	 *         caller should go ahead and weather it now).
	 */
	public boolean markWeathered(ChunkPos pos) {
		boolean added = weathered.add(pos.toLong());
		if (added)
			setDirty();
		return added;
	}

	public boolean isWeathered(ChunkPos pos) {
		return weathered.contains(pos.toLong());
	}

	/* ======================== Persistence ======================== */

	public static TemporalChunkState load(CompoundTag tag) {
		TemporalChunkState state = new TemporalChunkState();
		for (long key : tag.getLongArray("weathered")) {
			state.weathered.add(key);
		}
		return state;
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		tag.putLongArray("weathered", weathered.toLongArray());
		return tag;
	}
}