/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds;

import java.util.List;

import net.minecraft.world.level.levelgen.placement.*;

public class MOrePlacement {
	public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
		return orePlacement(CountPlacement.of(pCount), pHeightRange);
	}

	public static List<PlacementModifier> orePlacement(PlacementModifier placementModifier,
			PlacementModifier placementModifier1) {
		return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
	}

	public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
		return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
	}
}
