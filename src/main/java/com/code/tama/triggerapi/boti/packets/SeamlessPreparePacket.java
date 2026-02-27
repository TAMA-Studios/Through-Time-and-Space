/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;
import com.code.tama.triggerapi.networking.ImAPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SeamlessPreparePacket implements ImAPacket {

	public static final ResourceLocation ID = new ResourceLocation("yourmod", "seamless_prepare");

	public SeamlessPreparePacket() {
	}

	public static void encode(SeamlessPreparePacket msg, FriendlyByteBuf buf) {
		// no payload
	}

	public static SeamlessPreparePacket decode(FriendlyByteBuf buf) {
		return new SeamlessPreparePacket();
	}

	public static void handle(SeamlessPreparePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(ClientSeamlessTeleportState::setPending);
		ctx.setPacketHandled(true);
	}
}