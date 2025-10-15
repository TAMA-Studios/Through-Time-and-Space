/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.capabilities.Capabilities;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/** Used to sync the variant level between the server and the client */
public class SyncCapVariantPacketS2C {
    public static SyncCapVariantPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncCapVariantPacketS2C(buffer.readInt());
    }

    public static void encode(SyncCapVariantPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.variant);
    }

    public static void handle(SyncCapVariantPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance()
                        .level
                        .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                        .ifPresent(cap -> cap.GetData().SetExteriorVariant(ExteriorsRegistry.Get(packet.variant)));
            }
        });
        context.setPacketHandled(true);
    }

    private final int variant; // Block position

    public SyncCapVariantPacketS2C(int variant) {
        this.variant = variant;
    }
}
