package com.code.tama.mtm.server.networking.packets.dimensions;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Used to sync the light level between the server and the client
 */
public class SyncCapLightLevelPacket {
    private final float level; // Block position

    public SyncCapLightLevelPacket(float LightLevel) {
        this.level = LightLevel;
    }

    public static void encode(SyncCapLightLevelPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.level);
    }

    public static SyncCapLightLevelPacket decode(FriendlyByteBuf buffer) {
        return new SyncCapLightLevelPacket(
                buffer.readFloat()
        );
    }

    public static void handle(SyncCapLightLevelPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
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