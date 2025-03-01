package com.code.tama.mtm.Helpers;

import com.code.tama.mtm.TriggerAPI.ReflectionBuddy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.joml.Vector3d;

@SuppressWarnings("deprecation")
public class WorldHelper {
    public static void teleportToWorld(ServerPlayer player, ServerLevel target, Vector3d pos, float yaw, float pitch) {
        // Teleport the player to the new world
        player.teleportTo(target, pos.x, pos.y, pos.z, yaw, pitch);

        // Batch the player's status effects into a single packet and send it
        player.getActiveEffects().forEach(effect ->
                player.connection.send(new ClientboundUpdateMobEffectPacket(player.getId(), effect))
        );
    }


    public static boolean IsDragonDead() {
        assert ServerLifecycleHooks.getCurrentServer().getLevel(Level.END) != null;
        return ReflectionBuddy.EndDragonFightAccess.dragonKilled.apply(ServerLifecycleHooks.getCurrentServer().getLevel(Level.END).getDragonFight());
    }

    private static int findSafeTopY(ServerLevel world, BlockPos pos) {
        int x = pos.getX();
        int z = pos.getZ();

        // Get the chunk at the specified coordinates
        LevelChunk chunk = world.getChunk(x >> 4, z >> 4);

        // Sample the heightmap for the given X and Z coordinates
        return chunk.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x & 15, z & 15) + 1;
    }


    private static boolean CanCollide(BlockState state) {
        return ReflectionBuddy.BlockAccess.hasCollision.apply(state.getBlock());
    }

    private static int SafeBottomY(ServerLevel world, BlockPos pos) {
        int minY = world.dimensionType().minY();
        int maxY = world.getMaxBuildHeight();

        BlockPos.MutableBlockPos cursor = pos.mutable().setY(minY + 2);

        if (cursor.getY() > maxY) return pos.getY(); // Ensure the starting position is valid

        BlockState floor = world.getBlockState(cursor.below());
        BlockState current = world.getBlockState(cursor);
        BlockState above;

        while (cursor.getY() <= maxY) {
            above = world.getBlockState(cursor.above());

            if (IsSolid(floor, current, above))
                return cursor.getY() - 1;

            cursor.move(Direction.UP);
            floor = current;
            current = above;
        }

        return pos.getY(); // Fallback if no safe position is found
    }

    private static boolean IsSolid(BlockState floor, BlockState block1, BlockState block2) {
        return floor.blocksMotion() && !block1.blocksMotion() && !block2.blocksMotion();
    }
}