package com.code.tama.mtm.server.networking.packets.S2C.exterior;

import com.code.tama.mtm.server.tileentities.ExteriorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncExteriorVariantPacketS2C {
    private final int variant, model;
    private final int blockX, blockY, blockZ; // Block position
    public SyncExteriorVariantPacketS2C(int model, int variant, int x, int y, int z) {
        this.variant = variant;
        this.model = model;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
    }

    public static void encode(SyncExteriorVariantPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.model);
        buffer.writeInt(packet.variant);
        buffer.writeInt(packet.blockX);
        buffer.writeInt(packet.blockY);
        buffer.writeInt(packet.blockZ);
    }

    public static SyncExteriorVariantPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncExteriorVariantPacketS2C(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        );
    }

    public static void handle(SyncExteriorVariantPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code runs on the client side
            BlockPos pos = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
            if (Minecraft.getInstance().level != null) {
                // Get the block entity at the given position.
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ExteriorTile exteriorTile) {
                    exteriorTile.setVariant(packet.variant);
                    exteriorTile.setModelIndex(packet.model);
                }
            }
        });
        context.setPacketHandled(true);
    }
}