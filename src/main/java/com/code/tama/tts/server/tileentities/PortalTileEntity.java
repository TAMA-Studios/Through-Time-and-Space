/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.client.BlockBakedModel;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalSyncPacketS2C;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class PortalTileEntity extends TickingTile {
    public Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();
    public Map<BlockPos, BlockState> stateMap = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public Map<BakedModel, Integer> chunkModels = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public List<BotiChunkContainer> containers = new ArrayList<>();

    public long lastRequestTime = 0;
    public long lastUpdateTime = 0;

    @Getter
    private ResourceKey<Level> targetLevel;

    @Getter
    private BlockPos targetPos;

    public PortalTileEntity(BlockPos pos, BlockState state) {
        super(TTSTileEntities.PORTAL_TILE_ENTITY.get(), pos, state);
        //        this.setTargetLevel(Level.OVERWORLD, new BlockPos(0, 70, 0), true);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if (tag.contains("TargetLevel")) {
            targetLevel = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("TargetLevel")));
            targetPos = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if (targetLevel != null) {
            tag.putString("TargetLevel", targetLevel.location().toString());
            tag.putInt("TargetX", targetPos.getX());
            tag.putInt("TargetY", targetPos.getY());
            tag.putInt("TargetZ", targetPos.getZ());
        }
    }

    public void setTargetLevel(ResourceKey<Level> levelKey, BlockPos targetPos, boolean markDirty) {
        if (this.level == null) return;
        this.targetLevel = levelKey;
        this.targetPos = targetPos;
        if (this.level.isClientSide) this.chunkModels = null;
        if (this.blockEntities != null) this.blockEntities.clear();
        if (markDirty && !level.isClientSide()) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            Networking.INSTANCE.send(
                    PacketDistributor.ALL.noArg(), new PortalSyncPacketS2C(worldPosition, targetLevel, targetPos));
        }
    }

    @Override
    public void tick() {
        //        assert this.level != null;
        if (this.targetLevel == null || this.targetPos == null)
            this.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
                if (this.getTargetLevel() != cap.GetCurrentLevel()
                        || this.getTargetPos() != cap.GetExteriorLocation().GetBlockPos())
                    this.setTargetLevel(
                            cap.GetCurrentLevel(), cap.GetExteriorLocation().GetBlockPos(), true);
            });
    }

    @SuppressWarnings("deprecation")
    @OnlyIn(Dist.CLIENT)
    public void updateChunkModelFromServer(List<BotiChunkContainer> chunkData) {
        Minecraft mc = Minecraft.getInstance();
        BlockRenderDispatcher dispatcher = mc.getBlockRenderer();
        List<BakedQuad> quads = new ArrayList<>();
        ChunkPos chunkPos = new ChunkPos(targetPos);
        int baseY = targetPos.getY() & ~15;

        this.containers.clear();
        this.containers.addAll(chunkData);
        //        CompoundTag containers = chunkData.getCompound("containers");
        //        for (int i = 0; i < containers.getInt("size"); i++) {
        //            BotiChunkContainer container = new
        // BotiChunkContainer(containers.getCompound(Integer.toString(i)));
        //            this.containers.add(container);
        //        }

        if (true) return; // Rest is disabled till I can get a VBO in

        //        BlockState[][][] sectionStates = new BlockState[16][16][16];
        //        this.blockEntities.clear();
        //        for (int y = 0; y < 16; y++) {
        //            for (int x = 0; x < 16; x++) {
        //                for (int z = 0; z < 16; z++) {
        //                    BlockPos pos = new BlockPos(chunkPos.getMinBlockX() + x, baseY + y,
        // chunkPos.getMinBlockZ() + z);
        //                    //                    sectionStates[x][y][z] = getBlockStateFromChunkNBT(chunkData, pos);
        //                    String key = x + "_" + y + "_" + z;
        //                    if (chunkData.contains("block_entities")
        //                            && chunkData.getCompound("block_entities").contains(key)) {
        //                        CompoundTag beTag =
        //                                chunkData.getCompound("block_entities").getCompound(key);
        //                        BlockEntity be = BlockEntity.loadStatic(pos, sectionStates[x][y][z], beTag);
        //                        if (be != null) {
        //                            blockEntities.put(pos.subtract(chunkPos.getWorldPosition()), be);
        //                        }
        //                    }
        //                }
        //            }
        //        }
        //
        //        this.stateMap.clear();
        //        this.stateMap = sectionStatesToLocalMap(sectionStates);
        //
        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer modelRenderer = blockRenderer.getModelRenderer();
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        RandomSource rand = getLevel().random;
        Direction[] directions = Direction.values();

        stateMap.forEach((pos, state) -> {
            BakedModel model = blockRenderer.getBlockModel(state);

            int color = blockColors.getColor(state, Minecraft.getInstance().level, pos, 0);
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            VertexConsumer vc =
                    Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.translucent());

            List<BakedQuad> blockQuads = new java.util.ArrayList<>(List.of());
            // render only non-occluded faces
            for (Direction dir : directions) {
                BlockPos neighbourPos = pos.relative(dir);
                BlockState neighbourState = stateMap.get(neighbourPos);
                boolean occluded = neighbourState != null
                        && neighbourState.isSolidRender(Minecraft.getInstance().level, neighbourPos);
                if (neighbourState != null && !neighbourState.canOcclude() && !state.canOcclude()) occluded = true;

                if (!occluded) {
                    List<BakedQuad> raw = model.getQuads(state, dir, rand);
                    List<BakedQuad> translatedQuads = translateQuads(raw, pos.getX(), pos.getY(), pos.getZ());
                    blockQuads.addAll(translatedQuads);
                }
            }

            this.chunkModels.put(
                    new BlockBakedModel(blockQuads),
                    Minecraft.getInstance().getBlockColors().getColor(state, level, pos));
        });
    }

    private BlockState getBlockStateFromChunkNBT(CompoundTag chunkData, BlockPos pos) {
        if (chunkData.contains("block_states")) {
            CompoundTag blockStates = chunkData.getCompound("block_states");
            if (blockStates.contains("palette") && blockStates.contains("data")) {
                ListTag palette = blockStates.getList("palette", Tag.TAG_COMPOUND);
                long[] data = blockStates.getLongArray("data");
                if (data.length == 0 || palette.isEmpty()) {
                    System.out.println("Empty data or palette in chunk NBT for pos " + pos + ": data=" + data.length
                            + ", palette=" + palette.size());
                    return Blocks.AIR.defaultBlockState();
                }

                int bitsPerEntry = blockStates.contains("bitsPerEntry")
                        ? blockStates.getInt("bitsPerEntry")
                        : Math.max(2, (int) Math.ceil(Math.log(palette.size()) / Math.log(2)));
                int x = pos.getX() & 15;
                int y = pos.getY() & 15;
                int z = pos.getZ() & 15;
                int index = y * 256 + z * 16 + x;

                int entriesPerLong = 64 / bitsPerEntry;
                int longIndex = index / entriesPerLong;
                if (longIndex >= data.length) {
                    System.out.println("Long index out of bounds: " + longIndex + " >= " + data.length + " (index="
                            + index + ", bitsPerEntry=" + bitsPerEntry + ")");
                    return Blocks.AIR.defaultBlockState();
                }
                int offset = (index % entriesPerLong) * bitsPerEntry;
                long value = (data[longIndex] >> offset) & ((1L << bitsPerEntry) - 1);

                if (value >= 0 && value < palette.size()) {
                    CompoundTag stateTag = palette.getCompound((int) value);
                    return BlockState.CODEC
                            .parse(NbtOps.INSTANCE, stateTag)
                            .result()
                            .orElse(Blocks.AIR.defaultBlockState());
                } else {
                    System.out.println(
                            "State value out of palette bounds at " + pos + ": " + value + " >= " + palette.size());
                    return Blocks.AIR.defaultBlockState();
                }
            } else {
                System.out.println("Missing palette or data in block_states: " + blockStates);
            }
        } else {
            System.out.println("No block_states in chunk data: " + chunkData);
        }
        return Blocks.AIR.defaultBlockState();
    }

    private List<BakedQuad> translateQuads(List<BakedQuad> quads, int xOffset, int yOffset, int zOffset) {
        List<BakedQuad> translated = new ArrayList<>();
        for (BakedQuad quad : quads) {
            int[] vertexData = quad.getVertices().clone();
            for (int i = 0; i < vertexData.length; i += 8) {
                float x = Float.intBitsToFloat(vertexData[i]) + xOffset;
                float y = Float.intBitsToFloat(vertexData[i + 1]) + yOffset;
                float z = Float.intBitsToFloat(vertexData[i + 2]) + zOffset;
                vertexData[i] = Float.floatToRawIntBits(x);
                vertexData[i + 1] = Float.floatToRawIntBits(y);
                vertexData[i + 2] = Float.floatToRawIntBits(z);
            }
            translated.add(new BakedQuad(
                    vertexData, quad.getTintIndex(), quad.getDirection(), quad.getSprite(), quad.isShade()));
        }
        return translated;
    }

    public static Map<BlockPos, BlockState> sectionStatesToLocalMap(BlockState[][][] sectionStates) {
        Map<BlockPos, BlockState> map = new HashMap<>();

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockState state = sectionStates[x][y][z];
                    if (state != null && state.getBlock() != Blocks.AIR) {
                        // local coordinates (0–15)
                        BlockPos pos = new BlockPos(x, y, z);
                        map.put(pos, state);
                    }
                }
            }
        }

        return map;
    }

    public static List<BakedQuad> tintQuads(List<BakedQuad> original, float r, float g, float b) {
        List<BakedQuad> tinted = new ArrayList<>();
        for (BakedQuad quad : original) {
            tinted.add(recolorQuad(quad, r, g, b));
        }
        return tinted;
    }

    public static BakedQuad recolorQuad(BakedQuad original, float r, float g, float b) {
        int[] oldData = original.getVertices();
        int[] newData = new int[oldData.length];
        int stride = oldData.length / 4;

        for (int v = 0; v < 4; v++) {
            int base = v * stride;

            // Copy position (0,1,2)
            newData[base] = oldData[base];
            newData[base + 1] = oldData[base + 1];
            newData[base + 2] = oldData[base + 2];

            // Recolor
            int oldColor = oldData[base + 3];
            int a = (oldColor >> 24) & 0xFF;
            int nr = Math.min(255, (int) (((oldColor >> 16) & 0xFF) * r));
            int ng = Math.min(255, (int) (((oldColor >> 8) & 0xFF) * g));
            int nb = Math.min(255, (int) ((oldColor & 0xFF) * b));
            newData[base + 3] = (a << 24) | (nr << 16) | (ng << 8) | nb;

            // Copy UVs (4,5)
            newData[base + 4] = oldData[base + 4];
            newData[base + 5] = oldData[base + 5];

            // Copy lightmap (6)
            newData[base + 6] = oldData[base + 6];

            // Copy normal/extra (7)
            newData[base + 7] = oldData[base + 7];
        }

        // Create new quad with tintIndex -1 (forces vertex color usage)
        return new BakedQuad(newData, -1, original.getDirection(), original.getSprite(), original.isShade());
    }
}
