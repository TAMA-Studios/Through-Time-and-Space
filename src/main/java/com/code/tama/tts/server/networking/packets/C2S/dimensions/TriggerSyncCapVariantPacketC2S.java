/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.function.Supplier;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncCapVariantPacketS2C;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * Tells the server to sync the exterior variant with the client level
 * capability
 */
public class TriggerSyncCapVariantPacketC2S {
	ResourceKey<Level> TARDISLevel;

	public TriggerSyncCapVariantPacketC2S(ResourceKey<Level> TARDISLevel) {
		this.TARDISLevel = TARDISLevel;
	}

	public static TriggerSyncCapVariantPacketC2S decode(FriendlyByteBuf buffer) {
		return new TriggerSyncCapVariantPacketC2S(buffer.readResourceKey(Registries.DIMENSION));
	}

	public static void encode(TriggerSyncCapVariantPacketC2S packet, FriendlyByteBuf buffer) {
		buffer.writeResourceKey(packet.TARDISLevel);
	}

	public static void handle(TriggerSyncCapVariantPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> GetTARDISCapSupplier(
				ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel))
				.ifPresent(cap -> Networking.sendPacketToDimension(packet.TARDISLevel,
						new SyncCapVariantPacketS2C(ExteriorsRegistry.GetOrdinal(cap.GetData().getExteriorModel())))));

		context.setPacketHandled(true);
	}
}
