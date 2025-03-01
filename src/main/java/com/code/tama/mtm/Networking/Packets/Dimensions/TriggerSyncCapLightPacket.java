package com.code.tama.mtm.Networking.Packets.Dimensions;

import com.code.tama.mtm.Capabilities.CapabilityConstants;
import com.code.tama.mtm.Networking.Networking;
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
public class TriggerSyncCapLightPacket {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapLightPacket(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapLightPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapLightPacket decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapLightPacket(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapLightPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
                Networking.sendPacketToDimension(packet.TARDISLevel, new SyncCapLightLevelPacket(
                ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                        .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null).GetLightLevel()
        )));
        context.setPacketHandled(true);
    }
}