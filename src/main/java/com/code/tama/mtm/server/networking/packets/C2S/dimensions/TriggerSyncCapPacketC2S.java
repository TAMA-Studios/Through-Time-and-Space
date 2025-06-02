package com.code.tama.mtm.server.networking.packets.C2S.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.S2C.dimensions.SyncTARDISCapPacketS2C;
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
public class TriggerSyncCapPacketC2S {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapPacketC2S(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapPacketC2S packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapPacketC2S decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapPacketC2S(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                    .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap ->
                            Networking.sendPacketToDimension(packet.TARDISLevel, new SyncTARDISCapPacketS2C(
                                    cap.GetLightLevel(),
                                    cap.IsPoweredOn(),
                                    cap.IsInFlight(), cap.ShouldPlayRotorAnimation(),
                                    cap.GetDestination().GetBlockPos(),
                                    cap.GetExteriorLocation().GetBlockPos(), cap.GetCurrentLevel(),
                                    cap.GetExteriorModel().ModelName)));
        });
        context.setPacketHandled(true);
    }
}