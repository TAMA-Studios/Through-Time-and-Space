/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.joml.Vector3d;

public class WorldHelper {
    public static boolean CanCollide(BlockState state) {
        return ReflectionBuddy.BlockAccess.hasCollision.apply(state.getBlock());
    }

    public static boolean IsDragonDead() {
        assert ServerLifecycleHooks.getCurrentServer().getLevel(Level.END) != null;
        return ReflectionBuddy.EndDragonFightAccess.dragonKilled.apply(
                ServerLifecycleHooks.getCurrentServer().getLevel(Level.END).getDragonFight());
    }

    public static boolean IsSolid(BlockState floor, BlockState block1, BlockState block2) {
        return floor.blocksMotion() && !block1.blocksMotion() && !block2.blocksMotion();
    }

    public static int SafeBottomY(ServerLevel world, BlockPos pos) {
        int minY = world.dimensionType().minY();
        int maxY = world.getMaxBuildHeight();

        BlockPos.MutableBlockPos cursor = pos.mutable().setY(minY + 2);

        if (cursor.getY() > maxY) return pos.getY(); // Ensure the starting position is valid

        BlockState floor = world.getBlockState(cursor.below());
        BlockState current = world.getBlockState(cursor);
        BlockState above;

        while (cursor.getY() <= maxY) {
            above = world.getBlockState(cursor.above());

            if (IsSolid(floor, current, above)) return cursor.getY() - 1;

            cursor.move(Direction.UP);
            floor = current;
            current = above;
        }

        return pos.getY(); // Fallback if no safe position is found
    }

    public static int SafeTopY(ServerLevel world, BlockPos pos) {
        int x = pos.getX();
        int z = pos.getZ();

        // Get the chunk at the specified coordinates
        LevelChunk chunk = world.getChunk(x >> 4, z >> 4);

        // Sample the heightmap for the given X and Z coordinates
        return chunk.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x & 15, z & 15) + 1;
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

    public static int getHighestBuildableY(Level world, int x, int z) {
        int surface = getSurfaceHeight(world, x, z);
        return Math.min(surface + 1, world.getMaxBuildHeight() - 1);
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

    public static boolean isSafeLocation(Level world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return !blockState.isSolid() && !world.getBlockState(pos.above()).isSolid();
    }

    public static void teleportEntity(Entity entity, BlockPos targetPos, float yaw, float pitch) {
        if (entity == null || entity.level().isClientSide) return;
        entity.teleportTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
        entity.setYRot(yaw);
        entity.setXRot(pitch);
        Logger.info("Teleported %s to %s", entity.getName().getString(), targetPos.toString());
    }

    public static void teleportToWorld(ServerPlayer player, ServerLevel target, Vector3d pos, float yaw, float pitch) {
        // Teleport the player to the new world
        player.teleportTo(target, pos.x, pos.y, pos.z, yaw, pitch);

        // Batch the player's status effects into a single packet and send it
        player.getActiveEffects()
                .forEach(
                        effect -> player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect)));
    }
}
