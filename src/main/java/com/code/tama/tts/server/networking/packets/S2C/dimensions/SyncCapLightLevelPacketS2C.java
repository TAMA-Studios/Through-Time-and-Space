/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used to sync the light level between the server and the client
 */
public class SyncCapLightLevelPacketS2C {
    private final float level; // Block position

    public SyncCapLightLevelPacketS2C(float LightLevel) {
        this.level = LightLevel;
    }

    public static void encode(SyncCapLightLevelPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.level);
    }

    public static SyncCapLightLevelPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncCapLightLevelPacketS2C(
                buffer.readFloat()
        );
    }

    public static void handle(SyncCapLightLevelPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(
                        cap -> cap.SetLightLevel(packet.level));
            }
        });
        context.setPacketHandled(true);
    }
}