/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.networking;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Simple implementation of a packet. Register with <br />
 * <code>UniversalCommon.Networking.registerMsg(PacketIMPL.class)</code>
 */
public record PacketIMPL(Integer anInt, Boolean aBool) implements ImAPacket {
	public static void encode(PacketIMPL packet, FriendlyByteBuf buf) {
		PacketUtils.encode(buf, packet.anInt, packet.aBool);
	}

	public static PacketIMPL decode(FriendlyByteBuf buffer) {
		// Grab the data from the buffer in order of: INTEGER, BOOLEAN
		PacketUtils.DecodedDataHolder data = PacketUtils.decode(buffer, PacketUtils.DATATYPE.INTEGER,
				PacketUtils.DATATYPE.BOOLEAN);

		// Turn the data into an array for ease of access
		Object arr[] = PacketUtils.arrFromHolder(data);

		return new PacketIMPL((Integer) arr[0], (Boolean) arr[1]);
	}

	public static void handle(PacketIMPL packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			System.out.println(packet.anInt);
			System.out.println(packet.aBool);
		});
		context.setPacketHandled(true);
	}
}
