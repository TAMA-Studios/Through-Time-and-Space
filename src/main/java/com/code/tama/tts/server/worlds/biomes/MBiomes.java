/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.biomes;

import com.code.tama.tts.server.worlds.ModPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

import static com.code.tama.tts.TTSMod.MODID;

public class MBiomes {
    public static final ResourceKey<Biome> GALLIFREYAN_PLAINS = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(MODID, "gallifreyan_plains"));
    public static final ResourceKey<Biome> GALLIFREYAN_DESERT = ResourceKey.create(Registries.BIOME,
            new ResourceLocation(MODID, "gallifreyan_desert"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(GALLIFREYAN_PLAINS, GallifreyanPlainsBiome(context));
        context.register(GALLIFREYAN_DESERT, GallifreyanDesertBiome(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome GallifreyanPlainsBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAT, 5, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 5, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 5, 4, 4));

        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
//        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
//        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
//        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.GALLIFREYAN_OAK_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.2f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xE2a879)
                        .waterFogColor(0xE2A879)
                        .skyColor(0xE2a879)
                        .grassColorOverride(0xA24507)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0xE2a879)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.AMBIENT_WARPED_FOREST_MOOD))
                        .build())
                .build();
    }

    public static Biome GallifreyanDesertBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        BiomeDefaultFeatures.desertSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH_2);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_CACTUS_DECORATED);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .downfall(0.0f)
                .temperature(0.8f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xE2a879)
                        .waterFogColor(0xE2A879)
                        .skyColor(0xE2a879)
                        .grassColorOverride(0xA24507)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0xE2a879)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD))
                        .build())
                .build();
    }
}