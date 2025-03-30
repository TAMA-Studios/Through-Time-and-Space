package com.code.tama.mtm.server.networking.packets.exterior;

import com.code.tama.mtm.ExteriorVariants;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncExteriorVariantPacket {
    private final int variant;
    private final int blockX, blockY, blockZ; // Block position

    public SyncExteriorVariantPacket(int variant, int x, int y, int z) {
        this.variant = variant;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
    }

    public static void encode(SyncExteriorVariantPacket packet, FriendlyByteBuf buffer) {
        buffer.writeFloat(packet.variant);
        buffer.writeInt(packet.blockX);
        buffer.writeInt(packet.blockY);
        buffer.writeInt(packet.blockZ);
    }

    public static SyncExteriorVariantPacket decode(FriendlyByteBuf buffer) {
        return new SyncExteriorVariantPacket(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        );
    }

    public static void handle(SyncExteriorVariantPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code runs on the client side
            BlockPos pos = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
            if (Minecraft.getInstance().level != null) {
                // Get the block entity at the given position.
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ExteriorTile transparentBlockEntity) {
                    transparentBlockEntity.setVariant(ExteriorVariants.GetOrdinal(ExteriorVariants.Variants.get(packet.variant)));
                }
            }
        });
        context.setPacketHandled(true);
    }
}