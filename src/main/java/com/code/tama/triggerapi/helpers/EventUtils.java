/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.util.function.Consumer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;

public class EventUtils {
	public static void onBlockBreak(Consumer<BlockEvent.BreakEvent> callback) {
		MinecraftForge.EVENT_BUS.addListener((BlockEvent.BreakEvent event) -> callback.accept(event));
	}

	public static void onBlockLeftClick(Consumer<PlayerInteractEvent.LeftClickBlock> callback) {
		MinecraftForge.EVENT_BUS.addListener((PlayerInteractEvent.LeftClickBlock event) -> callback.accept(event));
	}
}
