/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.monitor;

import com.code.tama.tts.server.blocks.core.VoxelRotatedShape;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class MonitorBlock extends AbstractMonitorBlock {
    private final VoxelRotatedShape SHAPE = new VoxelRotatedShape(createShape().optimize());

    public MonitorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public VoxelShape createShape() {
        return Stream.of(
                        Block.box(0, 0, 2, 16, 16, 3),
                        Block.box(7, 6, 2, 9, 12, 14),
                        Block.box(0, 16, 1, 16, 16, 2),
                        Block.box(0, 0, 1, 16, 0, 2),
                        Block.box(0, 0, 1, 0, 16, 2),
                        Block.box(16, 0, 1, 16, 16, 2),
                        Block.box(-1, 7, 14, 17, 9, 32))
                .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
                .get();
    }

    public @NotNull VoxelShape getShape(
            @NotNull BlockState state,
            @NotNull BlockGetter getter,
            @NotNull BlockPos pos,
            @NotNull CollisionContext context) {
        return SHAPE.GetShapeFromRotation(state.getValue(FACING));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return TTSTileEntities.MONITOR_TILE.get().create(blockPos, blockState);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return AbstractMonitorTile::tick;
    }
}
