/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import com.mojang.serialization.DynamicOps;

import net.minecraft.core.Holder;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.*;

/**
 * Builds the downstream dimension's {@link LevelStem}.
 * <p>
 * Rather than reading a hand-authored {@code noise_settings} JSON (which would
 * only work for one specific dimension), this reads the <em>base level's
 * own</em> {@link NoiseRouter} and wraps just the piece it wants to perturb.
 * That means it works identically whether the base is the overworld, the
 * nether, or some other mod's dimension.
 * <p>
 * Only {@link NoiseRouter#erosion()} is biased. Erosion shapes surface
 * topology; cave and aquifer generation live downstream in {@code finalDensity}
 * via separate functions, so underground terrain is left exactly where it was.
 * That is what lets {@link BlockChangeRecord.Anchor#ABSOLUTE} be safe for deep
 * edits.
 */
public final class AgedGenerator {

	private AgedGenerator() {
	}

	/**
	 * Builds a level stem whose terrain is the base level's, slightly worn down.
	 * <p>
	 * Falls back to a plain codec round-trip (identical terrain) when the base
	 * generator is not noise-based -- flat worlds and fully custom
	 * {@code ChunkGenerator} subclasses can't be reshaped this way, and get pure
	 * post-process weathering instead.
	 *
	 * @param erosionBias
	 *            negative values read as "more eroded". See
	 *            {@code TimeTravelConfig}.
	 */
	public static LevelStem createAgedStem(MinecraftServer server, ServerLevel baseLevel, double erosionBias) {
		ChunkGenerator baseGen = baseLevel.getChunkSource().getGenerator();

		if (!(baseGen instanceof NoiseBasedChunkGenerator noiseGen)) {
			return new LevelStem(baseLevel.dimensionTypeRegistration(), copyGenerator(server, baseGen));
		}

		NoiseGeneratorSettings baseSettings = noiseGen.generatorSettings().value();
		NoiseRouter agedRouter = biasErosion(baseSettings.noiseRouter(), erosionBias);

		NoiseGeneratorSettings agedSettings = new NoiseGeneratorSettings(baseSettings.noiseSettings(),
				baseSettings.defaultBlock(), baseSettings.defaultFluid(), agedRouter, baseSettings.surfaceRule(),
				baseSettings.spawnTarget(), baseSettings.seaLevel(), baseSettings.disableMobGeneration(),
				baseSettings.aquifersEnabled(), baseSettings.oreVeinsEnabled(), baseSettings.useLegacyRandomSource());

		// Same BiomeSource -- biomes must line up across the chain, only the shape
		// changes.
		NoiseBasedChunkGenerator agedGen = new NoiseBasedChunkGenerator(noiseGen.getBiomeSource(),
				Holder.direct(agedSettings));

		return new LevelStem(baseLevel.dimensionTypeRegistration(), agedGen);
	}

	/**
	 * Wraps {@code erosion()} with a constant offset, leaving every other route
	 * untouched.
	 */
	private static NoiseRouter biasErosion(NoiseRouter base, double bias) {
		DensityFunction agedErosion = DensityFunctions.add(base.erosion(), DensityFunctions.constant(bias));

		return new NoiseRouter(base.barrierNoise(), base.fluidLevelFloodednessNoise(), base.fluidLevelSpreadNoise(),
				base.lavaNoise(), base.temperature(), base.vegetation(), base.continents(), agedErosion, base.depth(),
				base.ridges(), base.initialDensityWithoutJaggedness(), base.finalDensity(), base.veinToggle(),
				base.veinRidged(), base.veinGap());
	}

	/**
	 * Codec round-trip, so the copy doesn't share mutable state with the original.
	 */
	private static ChunkGenerator copyGenerator(MinecraftServer server, ChunkGenerator source) {
		DynamicOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, server.registryAccess());
		return ChunkGenerator.CODEC.encodeStart(ops, source).flatMap(nbt -> ChunkGenerator.CODEC.parse(ops, nbt))
				.getOrThrow(false, s -> {
					throw new IllegalStateException("Error copying chunk generator: " + s);
				});
	}
}