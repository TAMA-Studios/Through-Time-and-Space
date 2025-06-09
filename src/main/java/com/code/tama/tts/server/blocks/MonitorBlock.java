/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class MonitorBlock extends AbstractMonitorBlock {
    public MonitorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    private final VoxelRotatedShape SHAPE = new VoxelRotatedShape(createShape().optimize());

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE.GetShapeFromRotation(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return TTSTileEntities.MONITOR_TILE.get().create(blockPos, blockState);
    }

    public VoxelShape createShape() {
        return Stream.of(
                Block.box(0, 0, 0, 16, 16, 2),
                Block.box(7, 1.500000009613037, 1.9403799999999993, 9, 8.500000009613037, 14.94038),
                Block.box(7, 0, 12.94038, 9, 3, 14.94038)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }
}