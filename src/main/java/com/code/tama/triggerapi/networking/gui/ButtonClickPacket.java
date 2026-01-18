/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import java.util.function.Supplier;

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

public class ButtonClickPacket implements ImAPacket {
	private final ResourceLocation guiId;
	private final String elementId;
	private final int button;

	public ButtonClickPacket(ResourceLocation guiId, String elementId, int button) {
		this.guiId = guiId;
		this.elementId = elementId;
		this.button = button;
	}

	public static void encode(ButtonClickPacket packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.guiId);
		buffer.writeUtf(packet.elementId);
		buffer.writeInt(packet.button);
	}

	public static ButtonClickPacket decode(FriendlyByteBuf buffer) {
		return new ButtonClickPacket(buffer.readResourceLocation(), buffer.readUtf(), buffer.readInt());
	}

	public static void handle(ButtonClickPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player == null)
				return;

			GuiDefinition definition = GuiLoader.getGuiDefinition(packet.guiId);
			if (definition == null || definition.getElements() == null)
				return;

			// Get the shared context for this GUI
			LuaScriptEngine.ScriptContext sharedContext = getPlayerGuiContext(player, packet.guiId);

			for (GuiDefinition.GuiElement element : definition.getElements()) {
				if (element.getId() != null && element.getId().equals(packet.elementId)) {
					if (element.getScript() != null) {
						executeScript(element.getScript(), player, packet.guiId, packet.elementId, packet.button,
								sharedContext);
					}
					break;
				}
			}
		});
		context.setPacketHandled(true);
	}

	private static LuaScriptEngine.ScriptContext getPlayerGuiContext(ServerPlayer player, ResourceLocation guiId) {
		return GuiContextManager.getGuiContext(player, guiId);
	}

	private static void executeScript(String scriptRef, ServerPlayer player, ResourceLocation guiId, String elementId,
			int button, LuaScriptEngine.ScriptContext sharedContext) {
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

		// Create a context that includes shared data + element-specific data
		LuaScriptEngine.ScriptContext context = new LuaScriptEngine.ScriptContext();
		context.getVariables().putAll(sharedContext.getVariables()); // Copy shared state
		context.set("guiId", guiId.toString());
		context.set("elementId", elementId);
		context.set("button", button);

		LuaScriptEngine.ScriptResult result = LuaScriptEngine.executeScript(script, player, context);

		if (!result.isSuccess()) {
			player.sendSystemMessage(Component.literal("§cScript Error: " + result.getMessage()));
		} else {
			// Copy back any new variables set in Lua back to shared context
			context.getVariables().forEach((key, value) -> {
				if (!key.equals("guiId") && !key.equals("elementId") && !key.equals("button")) {
					sharedContext.set(key, value);
				}
			});
		}
	}
}