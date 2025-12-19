/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetClientTARDISCapSupplier;

import java.util.function.Supplier;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DataFlightEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;
import com.code.tama.triggerapi.networking.ImAPacket;

/** Used to sync the TARDIS Cap data between the server and the client */
public class SyncTARDISFlightEventPacketS2C implements ImAPacket {

	DataFlightEvent flightEvent;

	public SyncTARDISFlightEventPacketS2C(DataFlightEvent event) {
		this.flightEvent = event;
	}
	public static SyncTARDISFlightEventPacketS2C decode(FriendlyByteBuf buffer) {
		return new SyncTARDISFlightEventPacketS2C(
				FriendlyByteBufOps.Helper.readWithCodec(buffer, DataFlightEvent.CODEC));
	}

	public static void encode(SyncTARDISFlightEventPacketS2C packet, FriendlyByteBuf buffer) {
		FriendlyByteBufOps.Helper.writeWithCodec(buffer, DataFlightEvent.CODEC, packet.flightEvent);
	}

	public static void handle(SyncTARDISFlightEventPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().level != null) {
				GetClientTARDISCapSupplier().ifPresent(cap -> {
					cap.GetFlightData().setFlightEvent(packet.flightEvent);
				});
			}
		});
		context.setPacketHandled(true);
	}
}
