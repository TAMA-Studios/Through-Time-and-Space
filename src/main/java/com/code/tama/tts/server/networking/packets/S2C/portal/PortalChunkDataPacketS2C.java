/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class PortalChunkDataPacketS2C {
    @OnlyIn(Dist.CLIENT)
    public static void Data(PortalChunkDataPacketS2C msg) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            BlockEntity be = level.getBlockEntity(msg.portalPos);
            if (be instanceof PortalTileEntity portal) {
                portal.updateChunkModelFromServer(msg.chunkData);
            } else {
                System.out.println("No PortalTileEntity at " + msg.portalPos);
            }
        }
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
        ctx.get()
                .enqueueWork(() ->
                        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PortalChunkDataPacketS2C.Data(msg)));
        ctx.get().setPacketHandled(true);
    }

    public final CompoundTag chunkData;

    private final BlockPos portalPos;

    public PortalChunkDataPacketS2C(BlockPos portalPos, CompoundTag chunkData) {
        this.portalPos = portalPos;
        this.chunkData = chunkData;
    }

    public PortalChunkDataPacketS2C(BlockPos portalPos, LevelChunk chunk, BlockPos targetPos) {
        this.portalPos = portalPos;
        this.chunkData = new CompoundTag();
        CompoundTag blockStates = new CompoundTag();
        CompoundTag blockEntities = new CompoundTag();
        Level level = chunk.getLevel();
        int targetY = targetPos.getY();
        int baseY = targetY & ~15;
        int sectionIndex = chunk.getSectionIndex(targetY);
        LevelChunkSection section = chunk.getSection(sectionIndex);
        ChunkPos chunkPos = new ChunkPos(targetPos);

        try {
            // Build palette and block states
            List<BlockState> paletteList = new ArrayList<>();
            Map<BlockState, Integer> stateToIndex = new HashMap<>();
            paletteList.add(Blocks.AIR.defaultBlockState()); // Index 0 = air
            stateToIndex.put(Blocks.AIR.defaultBlockState(), 0);
            List<BotiChunkContainer> containers = new ArrayList<>();

            BlockState[][][] sectionStates = new BlockState[16][16][16];
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        sectionStates[x][y][z] = state;
                        if (!state.isAir() && !stateToIndex.containsKey(state)) {
//                            stateToIndex.put(state, paletteList.size());
//                            paletteList.add(state);

                            BlockPos pos = new BlockPos(x, y, z);
                            containers.add(new BotiChunkContainer(state, pos, getPackedLight(level, BlockUtils.fromChunkAndLocal(chunkPos, pos).atY(targetY))));

                        }
                    }
                }
            }

            // Collect block entity data
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockPos worldPos =
                                new BlockPos(chunkPos.getMinBlockX() + x, baseY + y, chunkPos.getMinBlockZ() + z);
                        BlockEntity be = chunk.getBlockEntity(worldPos);
                        if (be != null) {
                            CompoundTag beTag = be.saveWithFullMetadata();
                            String key = x + "_" + y + "_" + z;
                            blockEntities.put(key, beTag);
                        }
                    }
                }
            }

            CompoundTag containersTag = new CompoundTag();
            for(int i = 0; i < containers.size(); i++) {
                BotiChunkContainer container = containers.get(i);
                containersTag.put(Integer.toString(i), container.serializeNBT());
            }

            containersTag.putInt("size", containers.size());

            this.chunkData.put("containers", containersTag);
//            this.chunkData.put("block_states", blockStates);
            if (!blockEntities.isEmpty()) {
                this.chunkData.put("block_entities", blockEntities);
            }
        } catch (Exception e) {
            System.out.println("Exception in packet construction: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public static int getPackedLight(Level level, BlockPos pos) {
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight   = level.getBrightness(LightLayer.SKY, pos);

        // Clamp to 0–15 (should already be, but just in case)
        blockLight = Mth.clamp(blockLight, 0, 15);
        skyLight   = Mth.clamp(skyLight, 0, 15);

        // Pack into the same format LevelRenderer.getLightColor uses:
        // block << 4 into low bits, sky << 20 into high bits
        return (blockLight << 4) | (skyLight << 20);
    }
}
