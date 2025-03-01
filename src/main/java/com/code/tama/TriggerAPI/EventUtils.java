package com.code.tama.TriggerAPI;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;

import java.util.function.Consumer;

public class EventUtils {
    public static void onBlockLeftClick(Consumer<PlayerInteractEvent.LeftClickBlock> callback) {
        MinecraftForge.EVENT_BUS.addListener((PlayerInteractEvent.LeftClickBlock event) -> callback.accept(event));
    }

    public static void onBlockBreak(Consumer<BlockEvent.BreakEvent> callback) {
        MinecraftForge.EVENT_BUS.addListener((BlockEvent.BreakEvent event) -> callback.accept(event));
    }
}