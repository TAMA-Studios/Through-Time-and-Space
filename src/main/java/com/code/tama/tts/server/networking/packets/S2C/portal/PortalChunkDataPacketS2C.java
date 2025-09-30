/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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

public class PortalChunkDataPacketS2C {
    public CompoundTag chunkData;

    private final BlockPos portalPos;

    public PortalChunkDataPacketS2C(BlockPos portalPos, CompoundTag chunkData) {
        this.portalPos = portalPos;
        this.chunkData = chunkData;
    }

    @OnlyIn(Dist.CLIENT)
    public static Supplier<Runnable> Data(PortalChunkDataPacketS2C msg) {
        return () -> () -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;

            if (level.getBlockEntity(msg.portalPos) instanceof PortalTileEntity portal)
                portal.updateChunkModelFromServer(msg.chunkData);
            else TTSMod.LOGGER.warn("No PortalTileEntity at {}", msg.portalPos);
        };
    }

    public static PortalChunkDataPacketS2C decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        CompoundTag data = buf.readNbt();
        return new PortalChunkDataPacketS2C(pos, data);
    }

    public static void encode(PortalChunkDataPacketS2C msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        buf.writeNbt(msg.chunkData);
    }

    public static void handle(PortalChunkDataPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, PortalChunkDataPacketS2C.Data(msg)));
        ctx.get().setPacketHandled(true);
    }

    public PortalChunkDataPacketS2C(BlockPos portalPos, LevelChunk chunk, BlockPos targetPos) {
        this.portalPos = portalPos;
        Level level = chunk.getLevel();
        LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));
        ChunkPos chunkPos = new ChunkPos(targetPos);

        try {
            List<BotiChunkContainer> containers = new ArrayList<>();

            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        if (!state.isAir()) {
                            BlockPos pos = new BlockPos(x, y, z);
                            containers.add(new BotiChunkContainer(
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
            CompoundTag containersTag = new CompoundTag();
            for (int i = 0; i < containers.size(); i++) {
                BotiChunkContainer container = containers.get(i);
                containersTag.put(Integer.toString(i), container.serializeNBT());
            }

            containersTag.putInt("size", containers.size());

            this.chunkData = containersTag;
        } catch (Exception e) {
            TTSMod.LOGGER.error("Exception in packet construction: {}", e.getMessage());
            e.printStackTrace();
            this.chunkData = new CompoundTag();
        }
    }
}
