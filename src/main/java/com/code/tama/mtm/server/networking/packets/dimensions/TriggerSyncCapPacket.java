package com.code.tama.mtm.server.networking.packets.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.networking.Networking;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

/**
 * Tells the server to sync the TARDIS cap with the client
 */
public class TriggerSyncCapPacket {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapPacket(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapPacket decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapPacket(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                    .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap ->
                            Networking.sendPacketToDimension(packet.TARDISLevel, new SyncTARDISCapPacket(
                                    cap.GetLightLevel(),
                                    cap.IsPoweredOn(),
                                    cap.IsInFlight(), cap.ShouldPlayRotorAnimation(),
                                    cap.GetDestination().GetBlockPos(),
                                    cap.GetExteriorLocation().GetBlockPos(), cap.GetCurrentLevel())));
        });
        context.setPacketHandled(true);
    }
}