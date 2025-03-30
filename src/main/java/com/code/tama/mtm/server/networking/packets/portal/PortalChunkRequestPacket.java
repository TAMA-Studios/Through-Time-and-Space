package com.code.tama.mtm.server.networking.packets.portal;

import com.code.tama.mtm.server.networking.Networking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class PortalChunkRequestPacket {
    private final BlockPos portalPos;
    private final ResourceKey<Level> targetLevel;
    private final BlockPos targetPos;

    public PortalChunkRequestPacket(BlockPos portalPos, ResourceKey<Level> targetLevel, BlockPos targetPos) {
        this.portalPos = portalPos;
        this.targetLevel = targetLevel;
        this.targetPos = targetPos;
    }

    public static void encode(PortalChunkRequestPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        buf.writeResourceLocation(msg.targetLevel.location());
        buf.writeBlockPos(msg.targetPos);
    }

    public static PortalChunkRequestPacket decode(FriendlyByteBuf buf) {
        return new PortalChunkRequestPacket(
                buf.readBlockPos(),
                ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation()),
                buf.readBlockPos()
        );
    }

    public static void handle(PortalChunkRequestPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ServerLevel level = player.server.getLevel(msg.targetLevel);
                if (level != null) {
                    ChunkPos chunkPos = new ChunkPos(msg.targetPos);
                    level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true); // Force load chunk
                    LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
//                    System.out.println("Server sending chunk data for " + chunkPos + " in " + msg.targetLevel.location());
                    Networking.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),
                            new PortalChunkDataPacket(msg.portalPos, chunk, msg.targetPos)); // Pass targetPos
                } else {
                    System.out.println("Target level not loaded: " + msg.targetLevel.location());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}