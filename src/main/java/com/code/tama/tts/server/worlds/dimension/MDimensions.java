/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.dimension;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.List;
import java.util.OptionalLong;

import com.code.tama.tts.server.worlds.biomes.MBiomes;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class MDimensions {

	public static final ResourceKey<DimensionType> GALLIFREY_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(MODID, "gallifrey_type"));
	public static final ResourceKey<Level> GALLIFREY_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(MODID, "gallifrey"));
	public static final ResourceKey<LevelStem> GALLIFREY_STEM = ResourceKey.create(Registries.LEVEL_STEM,
			new ResourceLocation(MODID, "gallifrey"));

	public static ResourceKey<DimensionType> TARDIS_NATURAL_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(MODID, "natural_tardis"));

	public static ResourceKey<DimensionType> TARDIS_ARTIFICIAL_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(MODID, "artificial_tardis"));
	public static final ResourceKey<Level> TARDIS_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(MODID, "tardis"));
	public static final ResourceKey<DimensionType> VAROS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
			new ResourceLocation(MODID, "varos_type"));

	public static final ResourceKey<LevelStem> VAROS_KEY = ResourceKey.create(Registries.LEVEL_STEM,
			new ResourceLocation(MODID, "varos"));
	public static final ResourceKey<Level> VAROS_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
			new ResourceLocation(MODID, "varos"));

	public static void bootstrapStem(BootstapContext<LevelStem> context) {
		HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
		HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
		HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

		NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
				new FixedBiomeSource(biomeRegistry.getOrThrow(MBiomes.GALLIFREYAN_PLAINS)),
				noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));

		NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
				MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<>(List.of(
						Pair.of(Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(MBiomes.GALLIFREYAN_PLAINS)),
						Pair.of(Climate.parameters(0.1F, 0.2F, 0.0F, 0.2F, 0.0F, 0.0F, 0.0F),
								biomeRegistry.getOrThrow(MBiomes.GALLIFREYAN_DESERT))))),
				noiseGenSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD));

		LevelStem stem = new LevelStem(dimTypes.getOrThrow(MDimensions.GALLIFREY_DIM_TYPE), noiseBasedChunkGenerator);

		context.register(GALLIFREY_STEM, stem);
	}

	public static void bootstrapType(BootstapContext<DimensionType> context) {
		context.register(GALLIFREY_DIM_TYPE, new DimensionType(OptionalLong.empty(), // fixedTime
				false, // hasSkylight
				false, // hasCeiling
				false, // ultraWarm
				true, // natural
				1.0, // coordinateScale
				true, // bedWorks
				false, // respawnAnchorWorks
				-80, // minY
				256, // height
				256, // logicalHeight
				BlockTags.INFINIBURN_OVERWORLD, // infiniburn
				DimensionEffects.GALLIFREY_EFFECTS.location(), // effectsLocation //
																// BuiltinDimensionTypes.OVERWORLD_EFFECTS,
				1.0f, // ambientLight
				new DimensionType.MonsterSettings(false, true, ConstantInt.of(0), 0)));

		context.register(VAROS_DIM_TYPE, new DimensionType(OptionalLong.empty(), // fixedTime
				false, // hasSkylight
				false, // hasCeiling
				false, // ultraWarm
				false, // natural
				1.0, // coordinateScale
				true, // bedWorks
				true, // respawnAnchorWorks
				-64, // minY
				256, // height
				256, // logicalHeight
				BlockTags.INFINIBURN_NETHER, // infiniburn
				DimensionEffects.VAROS_EFFECTS, // effectsLocation // BuiltinDimensionTypes.OVERWORLD_EFFECTS,
				1.0f, // ambientLight
				new DimensionType.MonsterSettings(true, false, ConstantInt.of(0), 0)));
	}

	public static class DimensionEffects {
		public static final ResourceKey<DimensionType> GALLIFREY_EFFECTS = ResourceKey.create(Registries.DIMENSION_TYPE,
				new ResourceLocation(MODID, "gallifrey"));
		public static ResourceKey<DimensionType> TARDIS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
				new ResourceLocation(MODID, "tardis"));
		public static final ResourceLocation VAROS_EFFECTS = new ResourceLocation(MODID, "varos");
	}
}
