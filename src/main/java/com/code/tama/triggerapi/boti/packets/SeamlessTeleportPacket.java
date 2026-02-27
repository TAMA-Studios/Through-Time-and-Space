/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets;

import com.code.tama.triggerapi.networking.ImAPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SeamlessTeleportPacket(ResourceLocation dimension, double x, double y, double z, float yaw,
									 float pitch) implements ImAPacket {

	public static void encode(SeamlessTeleportPacket msg, FriendlyByteBuf buf) {
		buf.writeResourceLocation(msg.dimension());
		buf.writeDouble(msg.x());
		buf.writeDouble(msg.y());
		buf.writeDouble(msg.z());
		buf.writeFloat(msg.yaw());
		buf.writeFloat(msg.pitch());
	}

	public static SeamlessTeleportPacket decode(FriendlyByteBuf buf) {
		return new SeamlessTeleportPacket(
				buf.readResourceLocation(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readDouble(),
				buf.readFloat(),
				buf.readFloat());
	}

	public static void handle(SeamlessTeleportPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> ClientSeamlessTeleport.handle(msg));
		ctx.setPacketHandled(true);
	}
}