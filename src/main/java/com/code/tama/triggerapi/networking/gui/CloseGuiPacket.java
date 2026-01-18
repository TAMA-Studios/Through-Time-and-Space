/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.gui.GuiContextManager;
import com.code.tama.triggerapi.networking.ImAPacket;

public class CloseGuiPacket implements ImAPacket {
	private final ResourceLocation guiId;

	public CloseGuiPacket(ResourceLocation guiId) {
		this.guiId = guiId;
	}

	public static void encode(CloseGuiPacket packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.guiId);
	}

	public static CloseGuiPacket decode(FriendlyByteBuf buffer) {
		return new CloseGuiPacket(buffer.readResourceLocation());
	}

	public static void handle(CloseGuiPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			ServerPlayer player = context.getSender();
			if (player != null) {
				GuiContextManager.closeGui(player, packet.guiId);
			}
		});
		context.setPacketHandled(true);
	}
}