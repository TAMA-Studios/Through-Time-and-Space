/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import static com.code.tama.triggerapi.lua.LuaExecutable.DEBUG;

import java.util.function.Supplier;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.networking.Networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.gui.GuiContextManager;
import com.code.tama.triggerapi.gui.GuiDefinition;
import com.code.tama.triggerapi.gui.GuiLoader;
import com.code.tama.triggerapi.lua.LuaScriptEngine;
import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Packet for updating GUI element states (sliders, text boxes, dropdowns, etc.)
 */
public class GuiStateUpdatePacket implements ImAPacket {
	private final ResourceLocation guiId;
	private final String elementId;
	private final String action; // "slider_change", "dropdown_select", "switch_toggle", "checkbox_toggle",
	// "text_change", "request_progress"
	private final String value;

	public GuiStateUpdatePacket(ResourceLocation guiId, String elementId, String action, String value) {
		this.guiId = guiId;
		this.elementId = elementId;
		this.action = action;
		this.value = value;
	}

	public static void encode(GuiStateUpdatePacket packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.guiId);
		buffer.writeUtf(packet.elementId);
		buffer.writeUtf(packet.action);
		buffer.writeUtf(packet.value != null ? packet.value : "");
	}

	public static GuiStateUpdatePacket decode(FriendlyByteBuf buffer) {
		ResourceLocation guiId = buffer.readResourceLocation();
		String elementId = buffer.readUtf();
		String action = buffer.readUtf();
		String value = buffer.readUtf();
		return new GuiStateUpdatePacket(guiId, elementId, action, value.isEmpty() ? null : value);
	}

	public static void handle(GuiStateUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player == null)
				return;

			GuiDefinition definition = GuiLoader.getGuiDefinition(packet.guiId);
			if (definition == null || definition.getElements() == null)
				return;

			// Get the shared context for this GUI instance
			LuaScriptEngine.ScriptContext sharedContext = GuiContextManager.getGuiContext(player, packet.guiId);

			// Find the element
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				if (element.getId() != null && element.getId().equals(packet.elementId)) {
					handleAction(packet, player, element, sharedContext);
					break;
				}
			}
		});
		context.setPacketHandled(true);
	}

	private static void handleAction(GuiStateUpdatePacket packet, ServerPlayer player, GuiDefinition.GuiElement element,
			LuaScriptEngine.ScriptContext sharedContext) {

		switch (packet.action) {
			case "slider_change" -> {
				if (element.getOnChangeScript() != null) {
					executeScript(element.getOnChangeScript(), player, packet.guiId, packet.elementId, "value",
							packet.value, sharedContext);
				}
			}
			case "dropdown_select" -> {
				if (element.getOnSelectScript() != null) {
					executeScript(element.getOnSelectScript(), player, packet.guiId, packet.elementId, "selectedIndex",
							packet.value, sharedContext);
				}
			}
			case "switch_toggle", "checkbox_toggle" -> {
				if (element.getOnToggleScript() != null) {
					executeScript(element.getOnToggleScript(), player, packet.guiId, packet.elementId, "state",
							packet.value, sharedContext);
				}
			}
			case "text_change" -> {
				if (element.getOnTextChangeScript() != null) {
					executeScript(element.getOnTextChangeScript(), player, packet.guiId, packet.elementId, "text",
							packet.value, sharedContext);
				}
			}
			case "request_progress" -> {
				if (element.getProgressScript() != null) {
					float progress = getProgressValue(element.getProgressScript(), player, packet.guiId,
							packet.elementId, sharedContext);
					Networking.sendToClient(player, new ProgressUpdatePacket(packet.guiId, packet.elementId, progress));
					// Sync context after progress calculation
					Networking.sendToClient(player, new SyncContextPacket(packet.guiId, sharedContext.getVariables()));
				}
			}
		}
	}

	private static void executeScript(String scriptRef, ServerPlayer player, ResourceLocation guiId, String elementId,
			String paramName, String paramValue, LuaScriptEngine.ScriptContext sharedContext) {
		String script;

		boolean isFileReference = scriptRef.endsWith(".lua");

		if (isFileReference) {
			script = GuiLoader.getLuaScript(scriptRef);
			if (script == null) {
				player.sendSystemMessage(Component.literal("§cScript not found: " + scriptRef));
				return;
			}
		} else {
			script = scriptRef;
		}

		// Create a new context that includes shared data
		LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
		context.getVariables().putAll(sharedContext.getVariables()); // Copy shared state
		context.set("guiId", guiId.toString());
		context.set("elementId", elementId);
		context.set(paramName, paramValue); // This sets "value", "text", "state", "selectedIndex", etc.

		// DEBUG: Verify the parameter is in context
		Object retrievedValue = context.get(paramName);
		DEBUG("[GUI Context Debug] Setting " + paramName + " = " + paramValue + ", retrieved = " + retrievedValue);

		LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);

		if (!result.isSuccess()) {
			player.sendSystemMessage(Component.literal("§cScript Error: " + result.getMessage()));
		} else {
			// Debug: print what we're passing
			TTSMod.LOGGER.error("§6[GUI Debug] {} = {}", paramName, paramValue);

			// Copy back new variables to shared context (skip system variables)
			context.getVariables().forEach((key, value) -> {
				if (!key.equals("guiId") && !key.equals("elementId") && !key.equals(paramName)) {
					sharedContext.set(key, value);
				}
			});

			// Send updated context back to client
			Networking.sendToClient(player, new SyncContextPacket(guiId, sharedContext.getVariables()));
		}
	}

	private static float getProgressValue(String scriptRef, ServerPlayer player, ResourceLocation guiId,
			String elementId, LuaScriptEngine.ScriptContext sharedContext) {
		String script;

		boolean isFileReference = scriptRef.endsWith(".lua");

		if (isFileReference) {
			script = GuiLoader.getLuaScript(scriptRef);
			if (script == null) {
				return 0.0f;
			}
		} else {
			script = scriptRef;
		}

		// Create a new context that includes shared data
		LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();

		context.getVariables().putAll(sharedContext.getVariables()); // Copy shared state
		context.set("guiId", guiId.toString());
		context.set("elementId", elementId);

		LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);

		if (result.isSuccess()) {
			try {
				// Copy back new variables to shared context
				context.getVariables().forEach((key, value) -> {
					if (!key.equals("guiId") && !key.equals("elementId")) {
						sharedContext.set(key, value);
					}
				});

				return Float.parseFloat(result.getMessage());
			} catch (NumberFormatException e) {
				return 0.0f;
			}
		}

		return 0.0f;
	}
}