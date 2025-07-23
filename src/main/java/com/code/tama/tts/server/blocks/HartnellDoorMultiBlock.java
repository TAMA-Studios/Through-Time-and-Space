/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.tileentities.HartnellDoorTile;
import com.code.tama.tts.server.tileentities.HartnellDoorTilePlaceholder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class HartnellDoorMultiBlock extends Block implements EntityBlock {
    public boolean IsOpen;
    private BlockPos Controller;

    private final Supplier<? extends BlockEntityType<? extends HartnellDoorTilePlaceholder>> tile;

    public HartnellDoorMultiBlock(Supplier<? extends BlockEntityType<? extends HartnellDoorTilePlaceholder>> factory) {
        super(Properties.of().strength(3.0F).requiresCorrectToolForDrops());
        this.tile = factory;
    }

    public BlockPos GetController() {
        return this.Controller;
    }

    public void SetController(BlockPos pos) {
        this.Controller = pos;
    }

    @Override
    public void destroy(LevelAccessor level, BlockPos blockPos, BlockState state) {
        super.destroy(level, blockPos, state);
        if (level.getBlockEntity(blockPos) instanceof HartnellDoorTilePlaceholder hartnellDoorTilePlaceholder) {
            if (level.getBlockState(hartnellDoorTilePlaceholder.Master).getBlock()
                    instanceof HartnellDoor hartnellDoor) {
                hartnellDoor.destroyMultiblockStructure((ServerLevel) level, hartnellDoorTilePlaceholder.Master);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (this.IsOpen) return Shapes.empty();
        return super.getCollisionShape(state, blockGetter, blockPos, collisionContext);
    }

    @Override
    public void neighborChanged(
            BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean p_60514_) {
        HartnellDoorTile hartnellDoorTile = ((HartnellDoorTile) level.getBlockEntity(this.Controller));
        if(hartnellDoorTile != null)
            this.IsOpen = hartnellDoorTile.IsOpen();
        HartnellDoorTilePlaceholder tile = (HartnellDoorTilePlaceholder) level.getBlockEntity(blockPos);
        if (tile != null) tile.SetIsOpen(this.IsOpen);

        level.sendBlockUpdated(blockPos, state, state, UPDATE_CLIENTS);
        super.neighborChanged(state, level, blockPos, block, blockPos1, p_60514_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.tile.get().create(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(
            @NotNull BlockState state,
            Level level,
            @NotNull BlockPos blockPos,
            @NotNull Player player,
            @NotNull InteractionHand interactionHand,
            @NotNull BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof HartnellDoorTilePlaceholder hartnellDoorTilePlaceholder) {
            if (level.getBlockState(hartnellDoorTilePlaceholder.Master).getBlock()
                    instanceof HartnellDoor hartnellDoor) {
                hartnellDoor.use(
                        state, level, hartnellDoorTilePlaceholder.Master, player, interactionHand, blockHitResult);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
