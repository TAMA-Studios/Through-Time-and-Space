/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalSyncPacketS2C;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.mojang.blaze3d.vertex.VertexBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class PortalTileEntity extends TickingTile {
    @OnlyIn(Dist.CLIENT)
    public VertexBuffer MODEL_VBO;

    @OnlyIn(Dist.CLIENT)
    public Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();

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
        if (this.targetLevel == null || this.targetPos == null)
            this.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
                if (this.getTargetLevel() != cap.GetCurrentLevel()
                        || this.getTargetPos() != cap.GetExteriorLocation().GetBlockPos())
                    this.setTargetLevel(
                            cap.GetCurrentLevel(), cap.GetExteriorLocation().GetBlockPos(), true);
            });
    }

    @OnlyIn(Dist.CLIENT)
    public void updateChunkDataFromServer(List<BotiChunkContainer> chunkData) {
        this.containers.clear();
        this.blockEntities.clear();
        chunkData.forEach(container -> {
            if (container.isIsTile()) {
                BlockEntity entity =
                        BlockEntity.loadStatic(container.getPos(), container.getState(), container.getEntityTag());
                this.blockEntities.put(container.getPos(), entity);
                containers.remove(container);
            }
        });
        this.containers.addAll(chunkData);
    }
}
