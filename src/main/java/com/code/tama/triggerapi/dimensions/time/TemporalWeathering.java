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
 * dimensions, and the shared intensity field used to bias structural decay too.
 * <p>
 * One noise field serves both jobs deliberately: a wall standing in a patch of
 * terrain that weathered hard should decay harder itself, rather than the two
 * systems disagreeing about which parts of the world have aged.
 * {@link TemporalDiffLog#replayInto} samples the same instance via
 * {@link #sampleIntensity} for exactly this reason.
 */
public final class TemporalWeathering {

	private static final List<Integer> OCTAVES = List.of(-3, -2, -1);

	private final PerlinSimplexNoise mask;
	private final long dimSeed;
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
	 *            how many blocks down from the surface the terrain pass reaches
	 */
	public TemporalWeathering(long seed, int steps, double threshold, int depth) {
		this.mask = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(seed + steps * 31L)), OCTAVES);
		this.dimSeed = seed;
		this.steps = steps;
		this.threshold = threshold;
		this.depth = depth;
	}

	/**
	 * 0..1 decay intensity for a column, shared by both the terrain pass and
	 * structural replay. Below {@link #threshold} this is 0 (untouched); above it,
	 * scales up to 1 as the raw noise value approaches its ceiling.
	 */
	public double sampleIntensity(int x, int z) {
		double value = mask.getValue(x * 0.02, z * 0.02, false);
		if (value < threshold)
			return 0.0;
		return Math.min(1.0, (value - threshold) / (1.0 - threshold));
	}

	/**
	 * Weathers one chunk's natural terrain in place. Run before diff-log replay.
	 */
	public void apply(ServerLevel level, ChunkAccess chunk) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (int dx = 0; dx < 16; dx++) {
			for (int dz = 0; dz < 16; dz++) {
				int x = chunkPos.getMinBlockX() + dx;
				int z = chunkPos.getMinBlockZ() + dz;

				double intensity = sampleIntensity(x, z);
				if (intensity <= 0.0)
					continue;

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

					BlockState weathered = TemporalDecayRegistry.decay(state, columnSteps, cursor, dimSeed, intensity);
					if (weathered != state) {
						chunk.setBlockState(cursor, weathered, false);
					}
				}
			}
		}
	}

	/** Strips fragile blocks. Only called for {@code steps >= 2} (the future). */
	public static void stripFragile(ServerLevel level, ChunkAccess chunk) {
		ChunkPos chunkPos = chunk.getPos();
		BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

		for (int dx = 0; dx < 16; dx++) {
			for (int dz = 0; dz < 16; dz++) {
				int x = chunkPos.getMinBlockX() + dx;
				int z = chunkPos.getMinBlockZ() + dz;
				int surfaceY = chunk.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

				for (int y = surfaceY + 1; y >= surfaceY - 2; y--) {
					if (y < level.getMinBuildHeight() || y >= level.getMaxBuildHeight())
						continue;
					cursor.set(x, y, z);
					BlockState state = chunk.getBlockState(cursor);
					if (!state.isAir() && TemporalDecayRegistry.isFragile(state, chunk, cursor)) {
						chunk.setBlockState(cursor, Blocks.AIR.defaultBlockState(), false);
					}
				}
			}
		}
	}
}