/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds;

import static com.code.tama.tts.TTSMod.MODID;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class MBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_END_ZEITON_ORE = registerKey("add_end_zeiton_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_ZEITON_ORE = registerKey("add_nether_zeiton_ore");
    public static final ResourceKey<BiomeModifier> ADD_TREE_GALLIFREYAN_OAK = registerKey("add_tree_gallifreyan_oak");

    public static final ResourceKey<BiomeModifier> ADD_ZEITON_ORE = registerKey("add_zeiton_ore");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        // context.register(ADD_ZEITON_ORE, new
        // ForgeBiomeModifiers.AddFeaturesBiomeModifier(
        // biomes.getOrThrow(MBiomes.VAROS_PLAINS),
        // HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZEITON_ORE_PLACED_KEY)),
        // GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(
                ADD_ZEITON_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZEITON_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(
                ADD_NETHER_ZEITON_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_NETHER),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.NETHER_ZEITON_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(
                ADD_END_ZEITON_ORE,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_END),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.END_ZEITON_ORE_PLACED_KEY)),
                        GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(
                ADD_TREE_GALLIFREYAN_OAK,
                new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                        biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                        HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.GALLIFREYAN_OAK_PLACED_KEY)),
                        GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(MODID, name));
    }
}
