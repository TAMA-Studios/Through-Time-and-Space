/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.entities;

import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class SyncButtonAnimationSetPacketS2C {
    public static SyncButtonAnimationSetPacketS2C decode(FriendlyByteBuf buffer) {
        Map<Integer, Float> map = buffer.readMap(FriendlyByteBuf::readInt, FriendlyByteBuf::readFloat);
        return new SyncButtonAnimationSetPacketS2C((HashMap<Integer, Float>) map, buffer.readBlockPos());
    }

    public static void encode(SyncButtonAnimationSetPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeMap(packet.AnimationSet, FriendlyByteBuf::writeInt, FriendlyByteBuf::writeFloat);
    }

    public static void handle(SyncButtonAnimationSetPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getSender().level().getBlockEntity(packet.pos) != null) {
                ((AbstractConsoleTile) context.getSender().level().getBlockEntity(packet.pos)).ControlAnimationMap =
                        packet.AnimationSet;
            }
        });
        context.setPacketHandled(true);
    }

    HashMap<Integer, Float> AnimationSet = new HashMap<>();

    BlockPos pos;

    public SyncButtonAnimationSetPacketS2C(HashMap<Integer, Float> AnimationSet, BlockPos pos) {
        this.AnimationSet = AnimationSet;
        this.pos = pos;
    }
}
