package com.code.tama.mtm.World;

import com.code.tama.mtm.Blocks.MBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

import static com.code.tama.mtm.mtm.MODID;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ZEITON_ORE_PLACED_KEY = registerKey("zeiton_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_ZEITON_ORE_PLACED_KEY = registerKey("nether_zeiton_ore_placed");
    public static final ResourceKey<PlacedFeature> END_ZEITON_ORE_PLACED_KEY = registerKey("end_zeiton_ore_placed");

    public static final ResourceKey<PlacedFeature> GALLIFREYAN_OAK_PLACED_KEY = registerKey("gallifreyan_oak_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, ZEITON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(MConfiguredFeatures.OVERWORLD_ZEITON_ORE_KEY),
                MOrePlacement.commonOrePlacement(42,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, NETHER_ZEITON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(MConfiguredFeatures.NETHER_ZEITON_ORE_KEY),
                MOrePlacement.commonOrePlacement(4,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(7))));

        register(context, END_ZEITON_ORE_PLACED_KEY, configuredFeatures.getOrThrow(MConfiguredFeatures.END_ZEITON_ORE_KEY),
                MOrePlacement.commonOrePlacement(12,
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80))));

        register(context, GALLIFREYAN_OAK_PLACED_KEY, configuredFeatures.getOrThrow(MConfiguredFeatures.GALLIFREYAN_OAK_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2),
                        MBlocks.GALLIFREYAN_SAPLING.get()));
    }


    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}