package com.code.tama.triggerapi;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
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
}