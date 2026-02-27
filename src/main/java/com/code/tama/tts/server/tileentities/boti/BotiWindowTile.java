/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.boti;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

public class BotiWindowTile extends AbstractPortalTile {

    // Cached master pos — null means "not computed yet", equal to getBlockPos() means "I am master"
    @Nullable
    private BlockPos masterPos = null;

    public BotiWindowTile(BlockEntityType<?> type, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        super(type, pos, state);
    }

    /** Call whenever neighbors change to invalidate cached master. */
    public void invalidateCluster() {
        masterPos = null;
        BotiWindowRenderer.invalidateStencilVBO();
    }

    /** Returns true if this tile is the elected master of its cluster. */
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