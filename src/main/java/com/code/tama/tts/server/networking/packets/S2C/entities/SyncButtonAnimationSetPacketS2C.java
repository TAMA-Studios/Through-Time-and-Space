/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.entities;

import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class SyncButtonAnimationSetPacketS2C {
    private final HashMap<Integer, Float> animationSet;
    private final BlockPos pos;

    public static void encode(SyncButtonAnimationSetPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeMap(packet.animationSet, FriendlyByteBuf::writeInt, FriendlyByteBuf::writeFloat);
    }

    public static SyncButtonAnimationSetPacketS2C decode(FriendlyByteBuf buffer) {
        BlockPos pos = buffer.readBlockPos();
        Map<Integer, Float> map = buffer.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readFloat);
        return new SyncButtonAnimationSetPacketS2C(new HashMap<>(map), pos);
    }

    public static void handle(SyncButtonAnimationSetPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null && mc.level.getBlockEntity(packet.pos) instanceof AbstractConsoleTile tile) {
                tile.ControlAnimationMap = packet.animationSet;
            }
        });
        context.setPacketHandled(true);
    }

    public SyncButtonAnimationSetPacketS2C(HashMap<Integer, Float> animationSet, BlockPos pos) {
        this.animationSet = animationSet;
        this.pos = pos;
    }
}
