/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.entities;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.misc.containers.TIRBlockContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public record UpdateTIRPacketS2C(Map<UUID, TIRBlockContainer> container) {
	public static UpdateTIRPacketS2C decode(FriendlyByteBuf buffer) {
		return new UpdateTIRPacketS2C(buffer.readMap(FriendlyByteBuf::readUUID, UpdateTIRPacketS2C::readContainer));
	}

	public static void encode(UpdateTIRPacketS2C packet, FriendlyByteBuf buffer) {
		buffer.writeMap(packet.container, FriendlyByteBuf::writeUUID, UpdateTIRPacketS2C::writeContainer);
	}

	@SuppressWarnings("unchecked")
	public static void handle(UpdateTIRPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> Capabilities.getCap(Capabilities.LEVEL_CAPABILITY, Minecraft.getInstance().level).ifPresent(cap ->
				cap.SetTIRBlocks(packet.container)));
		context.setPacketHandled(true);
	}

	public static TIRBlockContainer readContainer(FriendlyByteBuf buf) {
		return new TIRBlockContainer(buf.readBlockPos(), buf.readUUID());
	}

	public static void writeContainer(FriendlyByteBuf buf, TIRBlockContainer container) {
		buf.writeBlockPos(container.getPos());
		buf.writeUUID(container.getTirUUID());
	}
}
