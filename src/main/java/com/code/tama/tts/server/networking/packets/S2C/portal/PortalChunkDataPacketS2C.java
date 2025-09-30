/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PortalChunkDataPacketS2C {
    public List<BotiChunkContainer> containersL = new ArrayList<>();

    private final BlockPos portalPos;

    public PortalChunkDataPacketS2C(BlockPos portalPos, List<BotiChunkContainer> containers) {
        this.portalPos = portalPos;
        this.containersL = containers;
    }

    @OnlyIn(Dist.CLIENT)
    public static Supplier<Runnable> Data(PortalChunkDataPacketS2C msg) {
        return () -> () -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;

            if (level.getBlockEntity(msg.portalPos) instanceof PortalTileEntity portal)
                portal.updateChunkDataFromServer(msg.containersL);
            else TTSMod.LOGGER.warn("No PortalTileEntity at {}", msg.portalPos);
        };
    }

    public static PortalChunkDataPacketS2C decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        List<BotiChunkContainer> data = BotiChunkContainer.decodeList(buf);
        return new PortalChunkDataPacketS2C(pos, data);
    }

    public static void encode(PortalChunkDataPacketS2C msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        BotiChunkContainer.encodeList(msg.containersL, buf);
    }

    public static void handle(PortalChunkDataPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, PortalChunkDataPacketS2C.Data(msg)));
        ctx.get().setPacketHandled(true);
    }

    public PortalChunkDataPacketS2C(BlockPos portalPos, Level level, BlockPos targetPos) {
        this.portalPos = portalPos;
        try {
            List<BotiChunkContainer> containers = new ArrayList<>();

            int chunksToRender = 8;
            for(int u = -chunksToRender / 2; u < chunksToRender / 2; u++) { // turn either the u or the v to = 0 based on the direction you're viewing from
                for (int v = -chunksToRender / 2; v < chunksToRender / 2; v++) {
                    ChunkPos chunkPos = new ChunkPos(new BlockPos(targetPos.getX() + (u * 16), targetPos.getY(), targetPos.getZ() + (v * 16)));
                    level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true); // Force load chunk
                    LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
                    LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

                    for (int y = 0; y < 16; y++) {
                        for (int x = 0; x < 16; x++) {
                            for (int z = 0; z < 16; z++) {
                                BlockState state = section.getBlockState(x, y, z);
                                if (!state.isAir()) {
                                    BlockPos pos = new BlockPos(x + (u * 16), y, z + (v * 16));
//                                    if(level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
//                                            .atY(targetPos.getY())) != null) {
//                                        BlockEntity entity = level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
//                                                .atY(targetPos.getY()));
//                                        containers.add(new BotiChunkContainer(level,
//                                                state,
//                                                pos,
//                                                BlockUtils.getPackedLight(
//                                                        level,
//                                                        BlockUtils.fromChunkAndLocal(chunkPos, pos)
//                                                                .atY(targetPos.getY())), true, entity.saveWithFullMetadata()));
//                                    }
                                    containers.add(new BotiChunkContainer(level,
                                            state,
                                            pos,
                                            BlockUtils.getPackedLight(
                                                    level,
                                                    BlockUtils.fromChunkAndLocal(chunkPos, pos)
                                                            .atY(targetPos.getY()))));
                                }
                            }
                        }
                    }
                }
            }

            this.containersL = containers;

        } catch (Exception e) {
            TTSMod.LOGGER.error("Exception in packet construction: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
