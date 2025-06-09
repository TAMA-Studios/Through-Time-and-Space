/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.core.abstractClasses.TickingTile;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalSyncPacketS2C;
import com.code.tama.tts.server.registries.TTSTileEntities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
import org.jetbrains.annotations.Nullable;

public class PortalTileEntity extends TickingTile {
    public Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public BakedModel chunkModel = null;

    public long lastRequestTime = 0;
    public long lastUpdateTime = 0;

    @Getter
    private ResourceKey<Level> targetLevel;

    @Getter
    private BlockPos targetPos;

    public PortalTileEntity(BlockPos pos, BlockState state) {
        super(TTSTileEntities.PORTAL_TILE_ENTITY.get(), pos, state);
        this.setTargetLevel(Level.OVERWORLD, new BlockPos(0, 70, 0), true);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("TargetLevel")) {
            targetLevel = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tag.getString("TargetLevel")));
            targetPos = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
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
        if (this.level.isClientSide) this.chunkModel = null;
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
        // this.level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap
        // -> {
        // if(this.getTargetLevel() != cap.GetCurrentLevel() || this.getTargetPos() !=
        // cap.GetExteriorLocation().GetBlockPos())
        // this.setTargetLevel(cap.GetCurrentLevel(),
        // cap.GetExteriorLocation().GetBlockPos(), true);
        // });
    }

    @OnlyIn(Dist.CLIENT)
    public void updateChunkModelFromServer(CompoundTag chunkData) {
        Minecraft mc = Minecraft.getInstance();
        BlockRenderDispatcher dispatcher = mc.getBlockRenderer();
        List<BakedQuad> quads = new ArrayList<>();
        ChunkPos chunkPos = new ChunkPos(targetPos);
        int baseY = targetPos.getY() & ~15;

        BlockState[][][] sectionStates = new BlockState[16][16][16];
        this.blockEntities.clear();
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos pos = new BlockPos(chunkPos.getMinBlockX() + x, baseY + y, chunkPos.getMinBlockZ() + z);
                    sectionStates[x][y][z] = getBlockStateFromChunkNBT(chunkData, pos);
                    String key = x + "_" + y + "_" + z;
                    if (chunkData.contains("block_entities")
                            && chunkData.getCompound("block_entities").contains(key)) {
                        CompoundTag beTag =
                                chunkData.getCompound("block_entities").getCompound(key);
                        BlockEntity be = BlockEntity.loadStatic(pos, sectionStates[x][y][z], beTag);
                        if (be != null) {
                            blockEntities.put(pos.subtract(chunkPos.getWorldPosition()), be);
                        }
                    }
                }
            }
        }

        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockState state = sectionStates[x][y][z];
                    if (state != null && !state.isAir() && !state.hasBlockEntity()) { // Skip block entities here
                        BakedModel model = dispatcher.getBlockModel(state);
                        BlockPos pos =
                                new BlockPos(chunkPos.getMinBlockX() + x, baseY + y, chunkPos.getMinBlockZ() + z);
                        List<BakedQuad> blockQuads = new ArrayList<>();

                        for (Direction side : Direction.values()) {
                            int adjX = x + side.getStepX();
                            int adjY = y + side.getStepY();
                            int adjZ = z + side.getStepZ();
                            boolean occluded = false;

                            if (adjX >= 0 && adjX < 16 && adjY >= 0 && adjY < 16 && adjZ >= 0 && adjZ < 16) {
                                BlockState adjState = sectionStates[adjX][adjY][adjZ];
                                occluded = adjState != null
                                        && adjState.isSolidRender(mc.level, pos.offset(side.getNormal()));
                            }

                            if (!occluded) {
                                List<BakedQuad> sideQuads = model.getQuads(state, side, RandomSource.create());
                                blockQuads.addAll(sideQuads);
                            }
                        }

                        List<BakedQuad> translatedQuads = translateQuads(blockQuads, x, y, z);
                        if (!translatedQuads.isEmpty()) {
                            quads.addAll(translatedQuads);
                        }
                    }
                }
            }
        }

        if (quads.isEmpty() && blockEntities.isEmpty()) {
            System.out.println(
                    "No quads or block entities generated for chunk at " + targetPos + " from data: " + chunkData);
        } else {
            this.chunkModel = new BakedModel() {
                @Override
                public ItemOverrides getOverrides() {
                    return ItemOverrides.EMPTY;
                }

                @Override
                public TextureAtlasSprite getParticleIcon() {
                    return mc.getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                            .apply(new ResourceLocation("minecraft", "stone"));
                }

                @Override
                public List<BakedQuad> getQuads(
                        @Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
                    return quads;
                }

                @Override
                public boolean isCustomRenderer() {
                    return false;
                }

                @Override
                public boolean isGui3d() {
                    return true;
                }

                @Override
                public boolean useAmbientOcclusion() {
                    return true;
                }

                @Override
                public boolean usesBlockLight() {
                    return true;
                }
            };
        }
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
}
