/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCap;

import java.util.function.Supplier;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncCapLightLevelPacketS2C;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * Tells the server to sync the interior Light Level with the client level
 * capability
 */
public record TriggerSyncCapLightPacketC2S(ResourceKey<Level> TARDISLevel) {

	public static TriggerSyncCapLightPacketC2S decode(FriendlyByteBuf buffer) {
		return new TriggerSyncCapLightPacketC2S(buffer.readResourceKey(Registries.DIMENSION));
	}

	public static void encode(TriggerSyncCapLightPacketC2S packet, FriendlyByteBuf buffer) {
		buffer.writeResourceKey(packet.TARDISLevel);
	}

	public static void handle(TriggerSyncCapLightPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> Networking.sendPacketToDimension(packet.TARDISLevel, new SyncCapLightLevelPacketS2C(
				GetTARDISCap(ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)).GetLightLevel())));
		context.setPacketHandled(true);
	}
}
