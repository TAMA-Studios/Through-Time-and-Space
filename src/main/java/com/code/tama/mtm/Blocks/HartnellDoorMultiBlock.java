package com.code.tama.mtm.Blocks;

import com.code.tama.mtm.TileEntities.HartnellDoorTile;
import com.code.tama.mtm.TileEntities.HartnellDoorTilePlaceholder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class HartnellDoorMultiBlock extends Block implements EntityBlock {
    private BlockPos Controller;
    public boolean IsOpen;

    @Override
    public void neighborChanged(BlockState p_60509_, Level p_60510_, BlockPos p_60511_, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        HartnellDoorTile hartnellDoorTile = ((HartnellDoorTile) p_60510_.getBlockEntity(this.Controller));
        this.IsOpen = hartnellDoorTile.IsOpen();
        super.neighborChanged(p_60509_, p_60510_, p_60511_, p_60512_, p_60513_, p_60514_);
    }

    @Override
    protected boolean isAir(@NotNull BlockState state) {
        return this.IsOpen;
    }

    private final Supplier<? extends BlockEntityType<? extends HartnellDoorTilePlaceholder>> tile;

    public HartnellDoorMultiBlock(Supplier<? extends BlockEntityType<? extends HartnellDoorTilePlaceholder>> factory) {
        super(Properties.of().strength(3.0F).requiresCorrectToolForDrops());
        this.tile = factory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.tile.get().create(pos, state);
    }

    public void SetController(BlockPos pos) {
        this.Controller = pos;
    }

    public BlockPos GetController() {
        return this.Controller;
    }
}
