/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WorldHelper {
    public static BlockPos findSolidBlockBelow(Level world, BlockPos startPos, int maxDepth) {
        BlockPos currentPos = startPos.below();
        for (int i = 0; i < maxDepth; i++) {
            BlockState state = world.getBlockState(currentPos);
            if (state.isSolid()) {
                return currentPos;
            }
            currentPos = currentPos.below();
        }
        return null;
    }

    public static void teleportEntity(Entity entity, BlockPos targetPos, float yaw, float pitch) {
        if (entity == null || entity.level().isClientSide) return;
        entity.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
        entity.setYRot(yaw);
        entity.setXRot(pitch);
        Logger.info("Teleported %s to %s", entity.getName().getString(), targetPos.toString());
    }

    public static boolean isSafeLocation(Level world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return !blockState.isSolid() && !world.getBlockState(pos.above()).isSolid();
    }

    public static int getSurfaceHeight(Level world, int x, int z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, world.getMaxBuildHeight(), z);
        while (pos.getY() > world.getMinBuildHeight()) {
            if (!world.isEmptyBlock(pos)) {
                return pos.getY();
            }
            pos.move(0, -1, 0);
        }
        return world.getMinBuildHeight();
    }

    public static int getHighestBuildableY(Level world, int x, int z) {
        int surface = getSurfaceHeight(world, x, z);
        return Math.min(surface + 1, world.getMaxBuildHeight() - 1);
    }

    public static BlockPos findFirstAirAbove(Level world, BlockPos startPos, int maxHeight) {
        BlockPos.MutableBlockPos pos = startPos.mutable();
        while (pos.getY() <= maxHeight && pos.getY() < world.getMaxBuildHeight()) {
            if (world.isEmptyBlock(pos)) {
                return pos.immutable();
            }
            pos.move(0, 1, 0);
        }
        return null;
    }

    public static boolean isExposedToSky(Level world, BlockPos pos) {
        BlockPos.MutableBlockPos checkPos = pos.above().mutable();
        while (checkPos.getY() < world.getMaxBuildHeight()) {
            if (!world.isEmptyBlock(checkPos)) {
                return false;
            }
            checkPos.move(0, 1, 0);
        }
        return true;
    }
}