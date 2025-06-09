/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.portal;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
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

public class PortalChunkRequestPacketC2S {
    private final BlockPos portalPos;
    private final ResourceKey<Level> targetLevel;
    private final BlockPos targetPos;

    public PortalChunkRequestPacketC2S(BlockPos portalPos, ResourceKey<Level> targetLevel, BlockPos targetPos) {
        this.portalPos = portalPos;
        this.targetLevel = targetLevel;
        this.targetPos = targetPos;
    }

    public static void encode(PortalChunkRequestPacketC2S msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        buf.writeResourceLocation(msg.targetLevel.location());
        buf.writeBlockPos(msg.targetPos);
    }

    public static PortalChunkRequestPacketC2S decode(FriendlyByteBuf buf) {
        return new PortalChunkRequestPacketC2S(
                buf.readBlockPos(),
                ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation()),
                buf.readBlockPos()
        );
    }

    public static void handle(PortalChunkRequestPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
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
                            new PortalChunkDataPacketS2C(msg.portalPos, chunk, msg.targetPos)); // Pass targetPos
                } else {
                    System.out.println("Target level not loaded: " + msg.targetLevel.location());
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}