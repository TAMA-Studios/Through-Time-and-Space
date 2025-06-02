package com.code.tama.mtm.server.networking.packets.S2C.entities;

import com.code.tama.mtm.server.tileentities.AbstractConsoleTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SyncButtonAnimationSetPacketS2C {
    HashMap<Vec3, Float> AnimationSet = new HashMap<>();
    BlockPos pos;

    public SyncButtonAnimationSetPacketS2C(HashMap<Vec3, Float> AnimationSet, BlockPos pos) {
        this.AnimationSet = AnimationSet;
        this.pos = pos;
    }

    public static void encode(SyncButtonAnimationSetPacketS2C packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeMap(packet.AnimationSet,
                (buf, vec) -> {
                    buf.writeDouble(vec.x);
                    buf.writeDouble(vec.y);
                    buf.writeDouble(vec.z);
                },
                FriendlyByteBuf::writeFloat
        );

    }

    public static SyncButtonAnimationSetPacketS2C decode(FriendlyByteBuf buffer) {
        Map<Vec3, Float> map = buffer.readMap(
                buf -> new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()),
                FriendlyByteBuf::readFloat);
        return new SyncButtonAnimationSetPacketS2C(
                (HashMap<Vec3, Float>) map,
                buffer.readBlockPos()
        );
    }

    public static void handle(SyncButtonAnimationSetPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
                if(context.getSender().level().getBlockEntity(packet.pos) != null) {
                    ((AbstractConsoleTile) context.getSender().level().getBlockEntity(packet.pos)).ControlAnimationMap = packet.AnimationSet;
                }
        });
        context.setPacketHandled(true);
    }
}