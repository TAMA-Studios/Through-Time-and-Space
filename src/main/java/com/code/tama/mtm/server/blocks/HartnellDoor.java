package com.code.tama.mtm.server.blocks;

import com.code.tama.mtm.server.MTMBlocks;
import com.code.tama.mtm.server.tileentities.HartnellDoorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class HartnellDoor extends Block implements EntityBlock {
    private final Supplier<? extends BlockEntityType<? extends HartnellDoorTile>> tile;
    private boolean IsOpen = false;

    @Override
    protected boolean isAir(@NotNull BlockState state) {
        return this.IsOpen;
    }

    public HartnellDoor(Supplier<? extends BlockEntityType<? extends HartnellDoorTile>> factory) {
        super(Properties.of().strength(3.0F).requiresCorrectToolForDrops());
        this.tile = factory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.tile.get().create(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, InteractionHand p_60507_, @NotNull BlockHitResult blockHitResult) {
        if (p_60507_.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;

        this.IsOpen = !this.IsOpen;
        HartnellDoorTile hartnellDoorTile = ((HartnellDoorTile) level.getBlockEntity(blockPos));
        assert hartnellDoorTile != null;
        hartnellDoorTile.SetIsOpen(this.IsOpen);
        level.updateNeighborsAt(blockPos, MTMBlocks.HARTNELL_DOOR_PLACEHOLDER.get());
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            if (placeMultiblockStructure(level.getServer().getLevel(level.dimension()), pos)) {
                level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                System.out.println("Multiblock door automatically placed!");
            }
        }
    }

    private boolean placeMultiblockStructure(ServerLevel level, BlockPos pos) {
        BlockState blockToPlace = MTMBlocks.HARTNELL_DOOR_PLACEHOLDER.get().defaultBlockState(); // Use the same block for all parts

        // Check if space is clear
        for (int y = 0; y < 3; y++) { // Height (3)
            for (int x = 0; x < 2; x++) { // Width (2)
                BlockPos placePos = pos.offset(x, y, 0);
                if (placePos != pos)
                    if (!level.getBlockState(placePos).isAir()) { // Prevent placing over existing blocks
                        return false;
                    }
            }
        }

        // Place blocks in a 2x3 structure
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                BlockPos placePos = pos.offset(x, y, 0);
                if(placePos != pos) {
                    level.setBlockAndUpdate(placePos, blockToPlace);
                    ((HartnellDoorMultiBlock) blockToPlace.getBlock()).SetController(pos);
                }
            }
        }

        return true;
    }

    public boolean IsOpen() {
        return this.IsOpen;
    }

    public void SetIsOpen(boolean IsOpen) {
        this.IsOpen = IsOpen;
    }
}