package com.code.tama.mtm.server.networking.packets.C2S.dimensions;

import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.networking.Networking;
import com.code.tama.mtm.server.networking.packets.S2C.dimensions.SyncCapVariantPacketS2C;
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
public class TriggerSyncCapVariantPacketC2S {
    ResourceKey<Level> TARDISLevel;

    public TriggerSyncCapVariantPacketC2S(ResourceKey<Level> TARDISLevel) {
        this.TARDISLevel = TARDISLevel;
    }

    public static void encode(TriggerSyncCapVariantPacketC2S packet, FriendlyByteBuf buffer) {
        buffer.writeResourceKey(packet.TARDISLevel);
    }

    public static TriggerSyncCapVariantPacketC2S decode(FriendlyByteBuf buffer) {
        return new TriggerSyncCapVariantPacketC2S(buffer.readResourceKey(Registries.DIMENSION));
    }

    public static void handle(TriggerSyncCapVariantPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() ->
                ServerLifecycleHooks.getCurrentServer().getLevel(packet.TARDISLevel)
                        .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> Networking.sendPacketToDimension(packet.TARDISLevel, new SyncCapVariantPacketS2C(
                                ExteriorVariants.GetOrdinal(cap.GetExteriorVariant()))

                        )));
        context.setPacketHandled(true);
    }
}