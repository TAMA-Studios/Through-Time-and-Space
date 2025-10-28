/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.List;

import com.code.tama.tts.server.registries.forge.TTSBlocks;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {
	public static final ResourceKey<PlacedFeature> END_ZEITON_ORE_PLACED_KEY = registerKey("end_zeiton_ore_placed");
	public static final ResourceKey<PlacedFeature> GALLIFREYAN_OAK_PLACED_KEY = registerKey("gallifreyan_oak_placed");
	public static final ResourceKey<PlacedFeature> NETHER_ZEITON_ORE_PLACED_KEY = registerKey(
			"nether_zeiton_ore_placed");

	public static final ResourceKey<PlacedFeature> ZEITON_ORE_PLACED_KEY = registerKey("zeiton_ore_placed");

	public static final ResourceKey<PlacedFeature> CRATER_PLACED_KEY = registerKey("crater");

	private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
			Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
		context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
	}

	private static ResourceKey<PlacedFeature> registerKey(String name) {
		return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MODID, name));
	}

	public static void bootstrap(BootstapContext<PlacedFeature> context) {
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

		register(context, CRATER_PLACED_KEY, configuredFeatures.getOrThrow(MConfiguredFeatures.CRATER_KEY),
				List.of(RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
						HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)));

		register(context, ZEITON_ORE_PLACED_KEY,
				configuredFeatures.getOrThrow(MConfiguredFeatures.OVERWORLD_ZEITON_ORE_KEY),
				MOrePlacement.commonOrePlacement(48,
						HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(80))));

		register(context, NETHER_ZEITON_ORE_PLACED_KEY,
				configuredFeatures.getOrThrow(MConfiguredFeatures.NETHER_ZEITON_ORE_KEY),
				MOrePlacement.commonOrePlacement(4,
						HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(7))));

		register(context, END_ZEITON_ORE_PLACED_KEY,
				configuredFeatures.getOrThrow(MConfiguredFeatures.END_ZEITON_ORE_KEY), MOrePlacement.commonOrePlacement(
						12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(124))));

		register(context, GALLIFREYAN_OAK_PLACED_KEY,
				configuredFeatures.getOrThrow(MConfiguredFeatures.GALLIFREYAN_OAK_KEY), VegetationPlacements
						.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), TTSBlocks.GALLIFREYAN_SAPLING.get()));
	}
}
