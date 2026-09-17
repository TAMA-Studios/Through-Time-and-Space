/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

/**
 * Sends an Inter-TARDIS Communications message to another TARDIS dimension, as
 * typed into the terminal's Comms tab.
 */
public class TerminalSendMessagePacketC2S {

	private final String message;
	private final ResourceLocation recipient;

	public TerminalSendMessagePacketC2S(String message, ResourceLocation recipient) {
		this.message = message;
		this.recipient = recipient;
	}

	public TerminalSendMessagePacketC2S(FriendlyByteBuf buf) {
		this.message = buf.readUtf(256);
		this.recipient = buf.readResourceLocation();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(message, 256);
		buf.writeResourceLocation(recipient);
	}

	public static void handle(TerminalSendMessagePacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null)
				return;

			ITARDISLevel tardis = TARDISLevelCapability.GetTARDISCap(player.level());
			if (tardis == null)
				return;

			if (msg.message == null || msg.message.isBlank())
				return;

			tardis.sendInterCommMessage(msg.message, msg.recipient);
		});
		ctx.get().setPacketHandled(true);
	}
}
