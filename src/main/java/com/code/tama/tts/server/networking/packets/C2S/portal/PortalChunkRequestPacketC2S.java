/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.portal;

import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.server.tileentities.AbstractPortalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PortalChunkRequestPacketC2S {
    public static PortalChunkRequestPacketC2S decode(FriendlyByteBuf buf) {
        return new PortalChunkRequestPacketC2S(
                buf.readBlockPos(),
                ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation()),
                buf.readBlockPos());
    }

    public static void encode(PortalChunkRequestPacketC2S msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        buf.writeResourceLocation(msg.targetLevel.location());
        buf.writeBlockPos(msg.targetPos);
    }

    public static void handle(PortalChunkRequestPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ServerLevel level = player.server.getLevel(msg.targetLevel);
                if (level != null) {
//                    Networking.INSTANCE.send(
//                            PacketDistributor.PLAYER.with(() -> player),
//                            new PortalChunkDataPacketS2C(
//                                    (AbstractPortalTile)
//                                            ctx.get().getSender().level().getBlockEntity(msg.portalPos),
//                                    level));
                    BOTIUtils.PortalChunkDataPacketS2C(player, (AbstractPortalTile)
                                            ctx.get().getSender().level().getBlockEntity(msg.portalPos), level);
                } else {
                    System.out.println("Target level not loaded: " + msg.targetLevel.location());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private final BlockPos portalPos;

    private final ResourceKey<Level> targetLevel;

    private final BlockPos targetPos;

    public PortalChunkRequestPacketC2S(BlockPos portalPos, ResourceKey<Level> targetLevel, BlockPos targetPos) {
        this.portalPos = portalPos;
        this.targetLevel = targetLevel;
        this.targetPos = targetPos;
    }
}
