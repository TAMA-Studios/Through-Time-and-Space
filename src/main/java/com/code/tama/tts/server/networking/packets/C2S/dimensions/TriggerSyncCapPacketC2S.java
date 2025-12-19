/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

/** Tells the server to sync the TARDIS cap with the client */
public class TriggerSyncCapPacketC2S {
	ResourceKey<Level> TARDISLevel;

	int toUpdate;

	public TriggerSyncCapPacketC2S(ResourceKey<Level> TARDISLevel, int toUpdate) {
		this.toUpdate = toUpdate;
		this.TARDISLevel = TARDISLevel;
	}

	public static TriggerSyncCapPacketC2S decode(FriendlyByteBuf buffer) {
		return new TriggerSyncCapPacketC2S(buffer.readResourceKey(Registries.DIMENSION), buffer.readInt());
	}
	public static void encode(TriggerSyncCapPacketC2S packet, FriendlyByteBuf buffer) {
		buffer.writeResourceKey(packet.TARDISLevel);
		buffer.writeInt(packet.toUpdate);
	}

	public static void handle(TriggerSyncCapPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			GetTARDISCapSupplier(packet.TARDISLevel).ifPresent(cap -> cap.UpdateClient(packet.toUpdate));
		});
		context.setPacketHandled(true);
	}
}
