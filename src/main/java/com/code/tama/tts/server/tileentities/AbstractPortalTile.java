/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.triggerapi.rendering.FBOHelper;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalSyncPacketS2C;
import com.mojang.blaze3d.vertex.VertexBuffer;
import lombok.Getter;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Other tiles implement this to get data for portals
 **/
@OnlyIn(Dist.CLIENT)
public abstract class AbstractPortalTile extends TickingTile {
    public AbstractPortalTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @OnlyIn(Dist.CLIENT)
    public VertexBuffer MODEL_VBO;

    @OnlyIn(Dist.CLIENT)
    private FBOHelper FBOContainer;

    public FBOHelper getFBOContainer() {
        return this.FBOContainer == null ? this.FBOContainer = new FBOHelper() : this.FBOContainer;
    }

    @OnlyIn(Dist.CLIENT)
    public Map<BlockPos, BlockEntity> blockEntities = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public Map<BakedModel, Integer> chunkModels = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public List<BotiChunkContainer> containers = new ArrayList<>();

    public long lastRequestTime = 0;

    public long lastUpdateTime = 0;

    @Getter
    public ResourceKey<Level> targetLevel;

    public DimensionType type;

    public ResourceKey<DimensionType> dimensionTypeId;

    public Vec3 SkyColor = Vec3.ZERO;

    @Getter
    public BlockPos targetPos = new BlockPos(0, 128, 0);

    public float targetY = 0;

    private final List<Integer> recievedPackets = new ArrayList<>();

    @OnlyIn(Dist.CLIENT)
    public void updateChunkDataFromServer(List<BotiChunkContainer> chunkData, int packetIndex, int totalPackets) {
        if (packetIndex > totalPackets || this.recievedPackets.contains(packetIndex)) {
            TTSMod.LOGGER.warn("Portal tile received packet not meant for it, or it's updating too quickly... ruh roh");
            return;
        } else recievedPackets.add(packetIndex);

        chunkData.forEach(container -> {
            if (container.isIsTile()) {
                BlockEntity entity =
                        BlockEntity.loadStatic(container.getPos(), container.getState(), container.getEntityTag());
                blockEntities.put(container.getPos(), entity);
                containers.remove(container);
            }
        });
        containers.addAll(chunkData);

        if (recievedPackets.size() >= totalPackets) { // If we've got all the packets
            this.recievedPackets.clear();
            this.MODEL_VBO = BOTIUtils.buildModelVBO(this.containers, this);
        }
    }

    public void setTargetLevel(ResourceKey<Level> levelKey, BlockPos targetPos, float yRot, boolean markDirty) {
        if (this.level == null) return;
        this.targetLevel = levelKey;
        this.targetPos = targetPos;
        this.type = ServerLifecycleHooks.getCurrentServer().getLevel(levelKey).dimensionType();
        this.dimensionTypeId = ServerLifecycleHooks.getCurrentServer().getLevel(levelKey).dimensionTypeId();
        this.targetY = yRot;

        chunkModels.clear();
        blockEntities.clear();

        if (markDirty && !level.isClientSide()) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            Networking.INSTANCE.send(
                    PacketDistributor.DIMENSION.with(() -> this.level.dimension()),
                    new PortalSyncPacketS2C(worldPosition, targetLevel, type, targetPos, dimensionTypeId, targetY));
        }
    }

    @Override
    public void tick() {
        if (this.targetLevel != null) return;
        assert this.level != null;
        this.level
                .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> this.setTargetLevel(
                        cap.GetCurrentLevel(),
                        cap.GetNavigationalData().GetExteriorLocation().GetBlockPos(),
                        targetY, true));
    }
}