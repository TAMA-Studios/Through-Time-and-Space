/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.boti;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BotiWindowTile extends AbstractPortalTile {

    // Cached master pos — null means "not computed yet", equal to getBlockPos() means "I am master"
    @Nullable
    private BlockPos masterPos = null;
    boolean invalidated = false;

    // Rotation in degrees, always a multiple of 45. Range 0–315.
    @Getter
    private int rotationDegrees = 0;

    public BotiWindowTile(BlockEntityType<?> type, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        if(this.isMaster())
            super.tick();
    }

    /** Cycle rotation by +45 degrees (wraps at 360). Called server-side by the configurator. */
    public void cycleRotation() {
        rotationDegrees = (rotationDegrees + 45) % 360;
        setChanged();
        if (level != null && !level.isClientSide()) {
            // Push update to client
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("RotationDegrees", rotationDegrees);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        rotationDegrees = tag.getInt("RotationDegrees");
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putInt("RotationDegrees", rotationDegrees);
        return tag;
    }

    @Override
    public @Nullable ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) rotationDegrees = tag.getInt("RotationDegrees");
    }

    /** Call whenever neighbors change to invalidate cached master. */
    public void invalidateCluster() {
        masterPos = null;
        this.invalidated = true;
    }

    /** Returns true if this tile is The Master.. */
    public boolean isMaster() {
        if (masterPos == null) electMaster();
        return masterPos != null && masterPos.equals(getBlockPos());
    }

    /** Returns the master pos for this cluster. */
    @Nullable
    public BlockPos getMasterPos() {
        if (masterPos == null) electMaster();
        return masterPos;
    }

    private void electMaster() {
        if (level == null) return;
        this.invalidateCluster();
        java.util.List<BlockPos> cluster = BotiWindowCluster.findCluster(level, getBlockPos());
        BlockPos center = BotiWindowCluster.electMaster(cluster);
        // Broadcast the result to all tiles in the cluster
        for (BlockPos pos : cluster) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BotiWindowTile tile) {
                tile.masterPos = center;
            }
        }
    }
}