/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
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

    public static float getHeightModifier(BlockState state) {
        if (state.getBlock().equals(Blocks.AIR)) return 1;
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

    public static float getReverseHeightModifier(BlockState state) {
        return 1 - getHeightModifier(state);
    }

    public static float getDifferenceInHeight(BlockState from, BlockState to) {
        return getHeightModifier(to) - getHeightModifier(from);
    }
}
