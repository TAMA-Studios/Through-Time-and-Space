package com.code.tama.mtm.server.networking.packets.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.networking.Networking;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Supplier;

/**
 * Tells the server to sync the exterior variant with the client level capability
 */
public class TriggerSyncCapVariantPacket {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapVariantPacket(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapVariantPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapVariantPacket decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapVariantPacket(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapVariantPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
                Networking.sendPacketToDimension(packet.TARDISLevel, new SyncCapVariantPacket(
                ExteriorVariants.GetOrdinal(ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                        .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null).GetExteriorVariant())
        )));
        context.setPacketHandled(true);
    }
}