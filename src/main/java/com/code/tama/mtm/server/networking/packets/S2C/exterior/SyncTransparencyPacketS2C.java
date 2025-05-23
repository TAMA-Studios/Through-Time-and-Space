package com.code.tama.mtm.server.networking.packets.S2C.exterior;

import com.code.tama.mtm.server.tileentities.ExteriorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncTransparencyPacketS2C {
    private final float transparency;
    private final int blockX, blockY, blockZ; // Block position

    public SyncTransparencyPacketS2C(float transparency, int x, int y, int z) {
        this.transparency = transparency;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
    }

    public static void encode(SyncTransparencyPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.transparency);
        buffer.writeInt(packet.blockX);
        buffer.writeInt(packet.blockY);
        buffer.writeInt(packet.blockZ);
    }

    public static SyncTransparencyPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncTransparencyPacketS2C(
                buffer.readFloat(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        );
    }

    public static void handle(SyncTransparencyPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code runs on the client side
            BlockPos pos = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
            if (Minecraft.getInstance().level != null) {
                // Get the block entity at the given position.
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ExteriorTile transparentBlockEntity) {
                    transparentBlockEntity.setClientTransparency(packet.transparency);
                }
            }
        });
        context.setPacketHandled(true);
    }
}