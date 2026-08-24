/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.S2C.entities;

import java.util.function.Supplier;

import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.server.capabilities.Capabilities;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record UpdateTIRPacketC2S() {
	public static UpdateTIRPacketC2S decode(FriendlyByteBuf buffer) {
		return new UpdateTIRPacketC2S();
	}

	public static void encode(UpdateTIRPacketC2S packet, FriendlyByteBuf buffer) {
	}

	@SuppressWarnings("unchecked")
	public static void handle(UpdateTIRPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> Capabilities.getCap(Capabilities.LEVEL_CAPABILITY, Minecraft.getInstance().level)
				.ifPresent(cap -> {
					Networking.sendToPlayer(context.getSender(), new UpdateTIRPacketS2C(cap.GetTIRBlocks()));
				}));
		context.setPacketHandled(true);
	}
}
