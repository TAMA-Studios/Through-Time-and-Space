package com.code.tama.triggerapi.universal;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;


public class UniversalServerOnly {
    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static class Level {
        public static void breakBlock(net.minecraft.world.level.Level world, BlockPos pos) {
            if (!world.isClientSide) {
                world.destroyBlock(pos, true);
            }
        }
    }
}
