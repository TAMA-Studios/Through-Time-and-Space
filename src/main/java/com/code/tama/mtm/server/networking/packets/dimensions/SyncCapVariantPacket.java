package com.code.tama.mtm.server.networking.packets.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.ExteriorVariants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used to sync the variant level between the server and the client
 */
public class SyncCapVariantPacket {
    private final int variant; // Block position

    public SyncCapVariantPacket(int variant) {
        this.variant = variant;
    }

    public static void encode(SyncCapVariantPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.variant);
    }

    public static SyncCapVariantPacket decode(FriendlyByteBuf buffer) {
        return new SyncCapVariantPacket(
                buffer.readInt()
        );
    }

    public static void handle(SyncCapVariantPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(
                        cap -> cap.SetExteriorVariant(ExteriorVariants.Get(packet.variant)));
            }
        });
        context.setPacketHandled(true);
    }
}