/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.code.tama.tts.server.tileentities.PortalTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;


public class PortalChunkDataPacketS2C {
    private final BlockPos portalPos;
    public final CompoundTag chunkData;

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
            BlockState[][][] sectionStates = new BlockState[16][16][16];
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState state = section.getBlockState(x, y, z);
                        sectionStates[x][y][z] = state;
                        if (state != null && !state.isAir() && !stateToIndex.containsKey(state)) {
                            stateToIndex.put(state, paletteList.size());
                            paletteList.add(state);
                        }
                    }
                }
            }

            // Build palette NBT
            ListTag palette = new ListTag();
            for (BlockState state : paletteList) {
                CompoundTag stateTag = (CompoundTag) BlockState.CODEC.encodeStart(NbtOps.INSTANCE, state)
                        .result().orElseThrow(() -> new IllegalStateException("Failed to encode state " + state));
                palette.add(stateTag);
            }

            int paletteSize = palette.size();
            int bitsPerEntry = Math.max(1, (int) Math.ceil(Math.log(paletteSize) / Math.log(2))); // Allow 1 bit for small palettes
            int entriesPerLong = 64 / bitsPerEntry;
            int dataLength = (int) Math.ceil(4096.0 / entriesPerLong); // 16x16x16 = 4096 entries

            // Build data array
            long[] data = new long[dataLength];
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    for (int x = 0; x < 16; x++) {
                        int index = y * 256 + z * 16 + x;
                        int longIndex = index / entriesPerLong;
                        int offset = (index % entriesPerLong) * bitsPerEntry;
                        BlockState state = sectionStates[x][y][z];
                        int paletteIndex = stateToIndex.getOrDefault(state, 0); // Default to air
                        data[longIndex] |= ((long) paletteIndex & ((1L << bitsPerEntry) - 1)) << offset;
                    }
                }
            }

            // Collect block entity data
            for (int y = 0; y < 16; y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockPos worldPos = new BlockPos(chunkPos.getMinBlockX() + x, baseY + y, chunkPos.getMinBlockZ() + z);
                        BlockEntity be = chunk.getBlockEntity(worldPos);
                        if (be != null) {
                            CompoundTag beTag = be.saveWithFullMetadata();
                            String key = x + "_" + y + "_" + z;
                            blockEntities.put(key, beTag);
                        }
                    }
                }
            }

            blockStates.put("palette", palette);
            blockStates.putLongArray("data", data);
            blockStates.putInt("bitsPerEntry", bitsPerEntry);
            this.chunkData.put("block_states", blockStates);
            if (!blockEntities.isEmpty()) {
                this.chunkData.put("block_entities", blockEntities);
            }
        } catch (Exception e) {
            System.out.println("Exception in packet construction: " + e.getMessage());
            e.printStackTrace();
            ListTag palette = new ListTag();
            palette.add(BlockState.CODEC.encodeStart(NbtOps.INSTANCE, Blocks.STONE.defaultBlockState())
                    .result().orElseThrow(() -> new IllegalStateException("Failed to encode stone state")));
            long[] fullData = new long[256];
            java.util.Arrays.fill(fullData, 0);
            blockStates.put("palette", palette);
            blockStates.putLongArray("data", fullData);
            System.out.println("Using fallback stone data due to serialization failure");
            this.chunkData.put("block_states", blockStates);
        }
    }

    public PortalChunkDataPacketS2C(BlockPos portalPos, CompoundTag chunkData) {
        this.portalPos = portalPos;
        this.chunkData = chunkData;
    }

    public static void encode(PortalChunkDataPacketS2C msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.portalPos);
        buf.writeNbt(msg.chunkData);
    }

    public static PortalChunkDataPacketS2C decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        CompoundTag data = buf.readNbt();
        return new PortalChunkDataPacketS2C(pos, data);
    }

    public static void handle(PortalChunkDataPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> PortalChunkDataPacketS2C.Data(msg)));
        ctx.get().setPacketHandled(true);
    }

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
}