package com.code.tama.mtm.Networking.Packets.Entities;

import com.code.tama.mtm.TileEntities.AbstractConsoleTile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Used to sync the TARDIS Cap between the server and the client
 */
public class SyncButtonAnimationSetPacket {
    HashMap<Vec3, Float> AnimationSet = new HashMap<>();
    BlockPos pos;

    public SyncButtonAnimationSetPacket(HashMap<Vec3, Float> AnimationSet, BlockPos pos) {
        this.AnimationSet = AnimationSet;
        this.pos = pos;
    }

    public static void encode(SyncButtonAnimationSetPacket packet, FriendlyByteBuf buffer) {
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

    public static SyncButtonAnimationSetPacket decode(FriendlyByteBuf buffer) {
        Map<Vec3, Float> map = buffer.readMap(
                buf -> new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble()),
                FriendlyByteBuf::readFloat);
        return new SyncButtonAnimationSetPacket(
                (HashMap<Vec3, Float>) map,
                buffer.readBlockPos()
        );
    }

    public static void handle(SyncButtonAnimationSetPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                if(context.getSender().level().getServer().getLevel(context.getSender().level().dimension()).getBlockEntity(packet.pos) != null) {
                    ((AbstractConsoleTile) context.getSender().level().getServer().getLevel(context.getSender().level().dimension()).getBlockEntity(packet.pos)).ControlAnimationMap = packet.AnimationSet;


                }
            }
        });
        context.setPacketHandled(true);
    }
}