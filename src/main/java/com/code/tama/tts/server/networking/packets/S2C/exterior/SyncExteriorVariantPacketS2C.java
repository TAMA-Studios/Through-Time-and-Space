/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.exterior;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class SyncExteriorVariantPacketS2C {
    public static SyncExteriorVariantPacketS2C decode(FriendlyByteBuf buffer) {
        return new SyncExteriorVariantPacketS2C(
                buffer.readResourceLocation(),
                buffer.readInt(),
                buffer.readResourceKey(Registries.DIMENSION),
                buffer.readFloat(),
                buffer.readJsonWithCodec(BlockPos.CODEC),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt());
    }

    public static void encode(SyncExteriorVariantPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.model);
        buffer.writeInt(packet.variant);
        buffer.writeResourceKey(packet.level);
        buffer.writeFloat(packet.targetY);
        buffer.writeJsonWithCodec(BlockPos.CODEC, packet.targetPos);
        buffer.writeInt(packet.blockX);
        buffer.writeInt(packet.blockY);
        buffer.writeInt(packet.blockZ);
    }

    public static void handle(SyncExteriorVariantPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code runs on the client side
            BlockPos pos = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
            if (Minecraft.getInstance().level != null) {
                // Get the block entity at the given position.
                if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ExteriorTile exteriorTile) {
                    exteriorTile.targetY = packet.targetY;
                    exteriorTile.targetPos = packet.targetPos;
                    exteriorTile.targetLevel = packet.level;
                    exteriorTile.setVariant(packet.variant);
                    exteriorTile.setModelIndex(packet.model);
                }
            }
        });
        context.setPacketHandled(true);
    }

    private final int blockX, blockY, blockZ; // Block position

    private final ResourceLocation model;

    private final int variant;

    private final float targetY;

    private final ResourceKey<Level> level;

    private final BlockPos targetPos;

    public SyncExteriorVariantPacketS2C(
            ResourceLocation model,
            int variant,
            ResourceKey<Level> level,
            float targetY,
            BlockPos targetPos,
            int x,
            int y,
            int z) {
        this.variant = variant;
        this.model = model;
        this.level = level;
        this.targetY = targetY;
        this.targetPos = targetPos;
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
    }
}
