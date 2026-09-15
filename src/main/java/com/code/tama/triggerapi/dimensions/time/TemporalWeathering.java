/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

/**
 * The cosmetic aging pass run over naturally-generated terrain in downstream
 * dimensions.
 * <p>
 * Crucially this reuses {@link TemporalDecayRegistry} rather than defining its
 * own substitutions: player edits decay because they are <em>old</em>, and
 * terrain decays because the noise mask says <em>this patch has weathered</em>,
 * but both go through the same chain. A player-placed cobblestone sitting
 * inside a weathered patch therefore composes for free.
 * <p>
 * Runs per-column over the chunk's heightmap, so cost is O(256 * depth) per
 * chunk rather than a full-volume scan.
 */
public final class TemporalWeathering {

	/**
	 * Low-frequency, so weathering appears in broad patches rather than per-block
	 * static.
	 */
	private static final List<Integer> OCTAVES = List.of(-3, -2, -1);

	private final PerlinSimplexNoise mask;
	private final int steps;
	private final double threshold;
	private final int depth;

	/**
	 * @param seed
	 *            the target dimension's seed; mixed with {@code steps} so present
	 *            and future weather in different places rather than identically.
	 * @param steps
	 *            decay depth of this dimension (1 = present, 2 = future)
	 * @param threshold
	 *            noise value above which a column weathers; higher = rarer
	 * @param depth
	 *            how many blocks down from the surface to weather
	 */
	public TemporalWeathering(long seed, int steps, double threshold, int depth) {
		this.mask = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(seed + steps * 31L)), OCTAVES);
		this.steps = steps;
		this.threshold = threshold;
		this.depth = depth;
	}

	/**
	 * Weathers one chunk in place.
	 * <p>
	 * Should run <em>before</em> the diff-log replay, so that player edits land on
	 * top of weathered terrain rather than being chewed up by it.
	 */
	public void apply(ServerLevel level, ChunkAccess chunk) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (int dx = 0; dx < 16; dx++) {
			for (int dz = 0; dz < 16; dz++) {
				int x = chunkPos.getMinBlockX() + dx;
				int z = chunkPos.getMinBlockZ() + dz;

				double value = mask.getValue(x * 0.02, z * 0.02, false);
				if (value < threshold)
					continue;

				// Scale intensity with how far past the threshold this column sits, so patches
				// fade at their edges instead of stopping at a hard line.
				double intensity = (value - threshold) / (1.0 - threshold);
				int columnSteps = Math.max(1, (int) Math.ceil(intensity * steps));

				int surfaceY = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

				for (int dy = 0; dy < depth; dy++) {
					int y = surfaceY - dy;
					if (y < level.getMinBuildHeight())
						break;

					cursor.set(x, y, z);
					BlockState state = chunk.getBlockState(cursor);
					if (state.isAir())
						continue;

					BlockState weathered = TemporalDecayRegistry.decay(state, columnSteps);
					if (weathered != state) {
						chunk.setBlockState(cursor, weathered == null ? Blocks.AIR.defaultBlockState() : weathered,
								false);
					}
				}
			}
		}
	}

	/**
	 * Strips fragile blocks (flowers, torches, tall grass...) from a chunk.
	 * Separate from the weathering mask because fragility is unconditional -- a
	 * flower doesn't survive two decay steps regardless of whether its column
	 * happened to weather.
	 * <p>
	 * Only applied at {@code steps >= 2} (i.e. the future), so the present keeps
	 * its vegetation.
	 */
	public static void stripFragile(ServerLevel level, ChunkAccess chunk) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (int dx = 0; dx < 16; dx++) {
			for (int dz = 0; dz < 16; dz++) {
				int x = chunkPos.getMinBlockX() + dx;
				int z = chunkPos.getMinBlockZ() + dz;
				int surfaceY = chunk.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

				// Fragile things sit on the surface, so a shallow scan suffices.
				for (int y = surfaceY + 1; y >= surfaceY - 2; y--) {
					if (y < level.getMinBuildHeight() || y >= level.getMaxBuildHeight())
						continue;
					cursor.set(x, y, z);
					BlockState state = chunk.getBlockState(cursor);
					// Pass the chunk and a concrete position: offset-type blocks (grass,
					// flowers, pointed dripstone) dereference the position inside getShape.
					if (!state.isAir() && TemporalDecayRegistry.isFragile(state, chunk, cursor)) {
						chunk.setBlockState(cursor, Blocks.AIR.defaultBlockState(), false);
					}
				}
			}
		}
	}
}