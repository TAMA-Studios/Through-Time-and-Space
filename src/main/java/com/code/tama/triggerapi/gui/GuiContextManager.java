/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import com.code.tama.triggerapi.lua.LuaScriptEngine;

/**
 * Manages shared script contexts for GUI instances Each player can have
 * multiple GUIs open, each with its own shared context
 */
public class GuiContextManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(GuiContextManager.class);

	// Map: Player UUID -> (GUI ResourceLocation -> ScriptContext)
	private static final Map<UUID, Map<ResourceLocation, LuaScriptEngine.ScriptContext>> playerGuiContexts = new HashMap<>();

	/**
	 * Get or create a shared context for a GUI instance
	 *
	 * @param player
	 *            The player opening the GUI
	 * @param guiId
	 *            The GUI's resource location
	 * @return The shared context for this GUI instance
	 */
	public static LuaScriptEngine.ScriptContext getGuiContext(ServerPlayer player, ResourceLocation guiId) {
		UUID playerUUID = player.getUUID();

		// Get or create the map for this player
		Map<ResourceLocation, LuaScriptEngine.ScriptContext> playerContexts = playerGuiContexts
				.computeIfAbsent(playerUUID, k -> new HashMap<>());

		// Get or create the context for this GUI
		return playerContexts.computeIfAbsent(guiId, k -> {
			LOGGER.debug("Created new GUI context for player {} and GUI {}", playerUUID, guiId);
			return new LuaScriptEngine.ScriptContext();
		});
	}

	/**
	 * Clean up a GUI context when the GUI is closed
	 *
	 * @param player
	 *            The player closing the GUI
	 * @param guiId
	 *            The GUI's resource location
	 */
	public static void closeGui(ServerPlayer player, ResourceLocation guiId) {
		UUID playerUUID = player.getUUID();

		Map<ResourceLocation, LuaScriptEngine.ScriptContext> playerContexts = playerGuiContexts.get(playerUUID);
		if (playerContexts != null) {
			LuaScriptEngine.ScriptContext removed = playerContexts.remove(guiId);
			if (removed != null) {
				LOGGER.debug("Closed GUI context for player {} and GUI {}", playerUUID, guiId);
			}

			// Clean up empty player entries
			if (playerContexts.isEmpty()) {
				playerGuiContexts.remove(playerUUID);
			}
		}
	}

	/**
	 * Clean up all contexts for a player (e.g., when they log out)
	 *
	 * @param player
	 *            The player logging out
	 */
	public static void cleanupPlayer(ServerPlayer player) {
		UUID playerUUID = player.getUUID();
		Map<ResourceLocation, LuaScriptEngine.ScriptContext> removed = playerGuiContexts.remove(playerUUID);
		if (removed != null) {
			LOGGER.debug("Cleaned up {} GUI contexts for player {}", removed.size(), playerUUID);
		}
	}

	/**
	 * Get the number of active GUI contexts (for debugging)
	 *
	 * @return Total number of active contexts across all players
	 */
	public static int getContextCount() {
		return playerGuiContexts.values().stream().mapToInt(Map::size).sum();
	}
}