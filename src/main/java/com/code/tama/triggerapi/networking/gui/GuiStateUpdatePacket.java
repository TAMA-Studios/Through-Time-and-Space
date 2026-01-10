/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import java.util.function.Supplier;

import com.code.tama.tts.server.networking.Networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.gui.GuiDefinition;
import com.code.tama.triggerapi.gui.GuiLoader;
import com.code.tama.triggerapi.gui.LuaScriptEngine;
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

			// Find the element
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				if (element.getId() != null && element.getId().equals(packet.elementId)) {
					handleAction(packet, player, element);
					break;
				}
			}
		});
		context.setPacketHandled(true);
	}

	private static void handleAction(GuiStateUpdatePacket packet, ServerPlayer player,
			GuiDefinition.GuiElement element) {

		switch (packet.action) {
			case "slider_change" -> {
				if (element.getOnChangeScript() != null) {
					executeScript(element.getOnChangeScript(), player, packet.guiId, packet.elementId, "value",
							packet.value);
				}
			}
			case "dropdown_select" -> {
				if (element.getOnSelectScript() != null) {
					executeScript(element.getOnSelectScript(), player, packet.guiId, packet.elementId, "selectedIndex",
							packet.value);
				}
			}
			case "switch_toggle", "checkbox_toggle" -> {
				if (element.getOnToggleScript() != null) {
					executeScript(element.getOnToggleScript(), player, packet.guiId, packet.elementId, "state",
							packet.value);
				}
			}
			case "text_change" -> {
				if (element.getOnTextChangeScript() != null) {
					executeScript(element.getOnTextChangeScript(), player, packet.guiId, packet.elementId, "text",
							packet.value);
				}
			}
			case "request_progress" -> {
				if (element.getProgressScript() != null) {
					float progress = getProgressValue(element.getProgressScript(), player, packet.guiId,
							packet.elementId);
					Networking.sendToClient(player, new ProgressUpdatePacket(packet.guiId, packet.elementId, progress));
				}
			}
		}
	}

	private static void executeScript(String scriptRef, ServerPlayer player, ResourceLocation guiId, String elementId,
			String paramName, String paramValue) {
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

		LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
		context.set("guiId", guiId.toString());
		context.set("elementId", elementId);
		context.set(paramName, paramValue);

		LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);

		if (!result.isSuccess()) {
			player.sendSystemMessage(Component.literal("§cScript Error: " + result.getMessage()));
		}
	}

	private static float getProgressValue(String scriptRef, ServerPlayer player, ResourceLocation guiId,
			String elementId) {
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

		LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
		context.set("guiId", guiId.toString());
		context.set("elementId", elementId);

		LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);

		if (result.isSuccess()) {
			try {
				return Float.parseFloat(result.getMessage());
			} catch (NumberFormatException e) {
				return 0.0f;
			}
		}

		return 0.0f;
	}
}