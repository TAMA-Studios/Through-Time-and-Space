/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.networking.gui;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.gui.CustomGuiScreen;
import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Packet sent from server to client to update progress bar values
 */
public class ProgressUpdatePacket implements ImAPacket {
	private final ResourceLocation guiId;
	private final String elementId;
	private final float progress;

	public ProgressUpdatePacket(ResourceLocation guiId, String elementId, float progress) {
		this.guiId = guiId;
		this.elementId = elementId;
		this.progress = progress;
	}

	public static void encode(ProgressUpdatePacket packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.guiId);
		buffer.writeUtf(packet.elementId);
		buffer.writeFloat(packet.progress);
	}

	public static ProgressUpdatePacket decode(FriendlyByteBuf buffer) {
		return new ProgressUpdatePacket(buffer.readResourceLocation(), buffer.readUtf(), buffer.readFloat());
	}

	public static void handle(ProgressUpdatePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().screen instanceof CustomGuiScreen guiScreen) {
				guiScreen.updateProgress(packet.elementId, packet.progress);
			}
		});
		context.setPacketHandled(true);
	}
}