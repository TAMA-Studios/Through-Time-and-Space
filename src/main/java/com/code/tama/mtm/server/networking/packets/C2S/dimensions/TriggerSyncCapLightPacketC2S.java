package com.code.tama.mtm.server.networking.packets.C2S.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.S2C.dimensions.SyncCapLightLevelPacketS2C;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

/**
 * Tells the server to sync the interior Light Level with the client level capability
 */
public class TriggerSyncCapLightPacketC2S {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapLightPacketC2S(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapLightPacketC2S packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapLightPacketC2S decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapLightPacketC2S(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapLightPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
                Networking.sendPacketToDimension(packet.TARDISLevel, new SyncCapLightLevelPacketS2C(
                ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                        .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null).GetLightLevel()
        )));
        context.setPacketHandled(true);
    }
}