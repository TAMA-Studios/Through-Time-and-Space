/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

/**
 * @author Codiak540 Why: Custom crater for Mars/Moon
 */
public class Crater extends Feature<ProbabilityFeatureConfiguration> {

	public static final ResourceLocation CRATER_ID = new ResourceLocation("ait", "crater");

	public Crater(Codec<ProbabilityFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
		LevelAccessor world = context.level();
		RandomSource random = context.random();
		ProbabilityFeatureConfiguration config = context.config();

		// Forge doesn't distinguish between client and server levels here — features
		// only generate server-side.
		if (random.nextFloat() >= config.probability) {
			return false;
		}

		int radius = 5 + random.nextInt(10);
		BlockPos origin = context.origin();
		BlockPos pos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin).above(radius / 3);
		pos = pos.above(random.nextInt(4));

		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				for (int z = -radius; z <= radius; z++) {
					mutablePos.setWithOffset(pos, x, y, z);
					if (mutablePos.closerThan(pos, radius)) {
						if (!world.getBlockState(mutablePos).isAir()) {
							world.setBlock(mutablePos, Blocks.AIR.defaultBlockState(), 2);
						}
					}
				}
			}
		}

		return true;
	}
}
