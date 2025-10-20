/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.biomes.surface;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.worlds.biomes.MBiomes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class MSurfaceRules {
	private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
	private static final SurfaceRules.RuleSource GALLIFREY_SAND = makeStateRule(TTSBlocks.GALLIFREYAN_SAND.get());
	private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
	private static final SurfaceRules.RuleSource RAW_ZEITON = makeStateRule(TTSBlocks.RAW_ZEITON_BLOCK.get());
	private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
	private static final SurfaceRules.RuleSource ZEITON = makeStateRule(TTSBlocks.ZEITON_BLOCK.get());

	private static SurfaceRules.RuleSource makeStateRule(Block block) {
		return SurfaceRules.state(block.defaultBlockState());
	}

	public static SurfaceRules.RuleSource makeRules() {
		SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

		SurfaceRules.RuleSource grassSurface = SurfaceRules
				.sequence(
						SurfaceRules.ifTrue(isAtOrAboveWaterLevel,
								SurfaceRules.ifTrue(SurfaceRules.isBiome(MBiomes.GALLIFREYAN_PLAINS),
										SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), GRASS_BLOCK))),
						DIRT);

		return SurfaceRules
				.sequence(
						SurfaceRules.sequence(
								SurfaceRules.ifTrue(SurfaceRules.isBiome(MBiomes.GALLIFREYAN_DESERT),
										SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
												SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(),
														GALLIFREY_SAND))),
								SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE)),

						// Default to a grass and dirt surface
						SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface));
	}
}
