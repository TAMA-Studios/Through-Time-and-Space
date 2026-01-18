/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import com.code.tama.tts.server.networking.Networking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import com.code.tama.triggerapi.networking.gui.OpenGuiPacket;
import com.code.tama.triggerapi.networking.gui.SyncContextPacket;

/**
 * Central registry for opening GUIs
 */
public class GuiRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuiRegistry.class);

	public static void openGui(ServerPlayer player, ResourceLocation guiId) {
		GuiDefinition def = GuiLoader.getGuiDefinition(guiId);
		if (def == null) {
			LOGGER.error("GUI definition not found: {}", guiId);
			return;
		}

		// Create/get the shared context
		var sharedContext = GuiContextManager.getGuiContext(player, guiId);

		String type = def.getType();
		if ("container".equals(type)) {
			player.openMenu(new ContainerGuiProvider(def, guiId));
		} else {
			// For custom GUIs, send context sync packet before opening GUI
			Networking.sendToClient(player, new SyncContextPacket(guiId, sharedContext.getVariables()));
			Networking.sendToClient(player, new OpenGuiPacket(guiId));
		}
	}
}