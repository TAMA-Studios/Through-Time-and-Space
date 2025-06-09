/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.registries.TTSTileEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class MonitorPanel extends AbstractMonitorBlock {
    public MonitorPanel(Properties p_49795_) {
        super(p_49795_);
    }

    private static final VoxelRotatedShape SHAPE = new VoxelRotatedShape(Block.box(0.25, 0.25, 15, 15.75, 15.75, 16).optimize());

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE.GetShapeFromRotation(state.getValue(FACING));
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return TTSTileEntities.MONITOR_PANEL_TILE.get().create(blockPos, blockState);
    }
}