/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockUtils {
	public static void breakBlock(Level world, BlockPos pos) {
		if (!world.isClientSide) {
			world.destroyBlock(pos, true);
		}
	}

	public static BlockPos fromChunkAndLocal(ChunkPos chunkPos, BlockPos localPos) {
		int worldX = (chunkPos.x << 4) + localPos.getX();
		int worldY = localPos.getY();
		int worldZ = (chunkPos.z << 4) + localPos.getZ();
		return new BlockPos(worldX, worldY, worldZ);
	}

	public static float getDifferenceInHeight(BlockState from, BlockState to) {
		return getHeightModifier(to) - getHeightModifier(from);
	}

	public static float getHeightModifier(BlockState state) {
		if (state.getBlock().equals(Blocks.AIR))
			return 1;
		if (state.getBlock() instanceof net.minecraft.world.level.block.SlabBlock) {
			return 0.5f;
		}

		if (state.getBlock() instanceof SnowLayerBlock) {
			int layers = state.getValue(SnowLayerBlock.LAYERS);
			return layers * 0.125f; // Each layer is 1/8th of a block
		}

		if (state.getBlock() instanceof CarpetBlock) {
			return 0.0625f; // Carpet is 1/16th of a block
		}

		return 1.0f;
	}

	public static int getLight(Level level, BlockPos pos) {
		return Mth.clamp(level.getBrightness(LightLayer.BLOCK, pos), 0, 15);
	}

	public static int getPackedLight(Level level, BlockPos pos) {
		int sky = level.getBrightness(LightLayer.SKY, pos.above());
		int block = level.getBrightness(LightLayer.BLOCK, pos.above());
		return (Mth.clamp(block, 1, 15) << 20) | (Mth.clamp(sky, 0, 15) << 4);
	}

	public static BlockPos getRelativeBlockPos(BlockPos basePos, BlockPos offsetPos) {
		return new BlockPos(basePos.getX() + offsetPos.getX(), basePos.getY() + offsetPos.getY(),
				basePos.getZ() + offsetPos.getZ());
	}

	public static float getReverseHeightModifier(BlockState state) {
		return 1 - getHeightModifier(state);
	}

	public static boolean isBlock(Level world, BlockPos pos, BlockState expected) {
		return world.getBlockState(pos).is(expected.getBlock());
	}

	public static boolean placeBlock(Level world, BlockPos pos, BlockState state) {
		if (world.isEmptyBlock(pos)) {
			world.setBlock(pos, state, 3);
			return true;
		}
		return false;
	}
}
