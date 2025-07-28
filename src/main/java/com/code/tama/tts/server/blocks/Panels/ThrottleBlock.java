/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.Panels;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.blocks.VoxelRotatedShape;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class ThrottleBlock extends HorizontalDirectionalBlock {
    public VoxelRotatedShape SHAPE_OFF = new VoxelRotatedShape(createShapeOFF().optimize());

    public VoxelRotatedShape SHAPE_ON = new VoxelRotatedShape(createShapeON().optimize());

    public ThrottleBlock(Properties p_54120_) {
        super(p_54120_);
    }

    public VoxelShape createShapeOFF() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Block.box(5.5, 0, 4, 10.5, 5, 12), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(5.5, 5, 5, 10.5, 6, 11), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(6, 6, 5.5, 10, 7, 10.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 4.65, 9.25, 11.5, 5.65, 11.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 3.6500000000000004, 8.5, 11.5, 4.65, 10.75), BooleanOp.OR);
        shape = Shapes.join(
                shape, Block.box(4.5, 2.6500000000000004, 7.5, 11.5, 3.6500000000000004, 9.75), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 5.65, 10.25, 11.5, 6.65, 12.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 6.65, 11.25, 11.5, 7.65, 13.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 7.65, 12, 11.5, 8.65, 14.25), BooleanOp.OR);
        return Shapes.join(shape, Block.box(4.5, 8.65, 13, 11.5, 9.65, 14.5), BooleanOp.OR);
    }

    public VoxelShape createShapeON() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Block.box(5.5, 0, 4, 10.5, 5, 12), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(5.5, 5, 5, 10.5, 6, 11), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(6, 6, 5.5, 10, 7, 10.5), BooleanOp.OR);
        shape = Shapes.join(
                shape, Block.box(4.5, 2.6500000000000004, 5.5, 11.5, 3.6500000000000004, 8.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 3.6500000000000004, 3.5, 11.5, 4.65, 6.5), BooleanOp.OR);
        shape = Shapes.join(shape, Block.box(4.5, 4.65, 1, 11.5, 5.65, 4), BooleanOp.OR);
        return Shapes.join(shape, Block.box(4.5, 5.65, 0, 11.5, 6.65, 3), BooleanOp.OR);
    }

    @Override
    public @NotNull VoxelShape getShape(
            BlockState p_60555_,
            @NotNull BlockGetter p_60556_,
            @NotNull BlockPos p_60557_,
            @NotNull CollisionContext p_60558_) {
        if (p_60555_.getValue(POWERED)) return SHAPE_ON.GetShapeFromRotation(p_60555_.getValue(FACING));
        else return SHAPE_OFF.GetShapeFromRotation(p_60555_.getValue(FACING));
    }

    @Override
    public int getSignal(
            BlockState p_54635_,
            @NotNull BlockGetter p_54636_,
            @NotNull BlockPos p_54637_,
            @NotNull Direction p_54638_) {
        return p_54635_.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(POWERED, false);
    }

    @Override
    public boolean isSignalSource(@NotNull BlockState p_54675_) {
        return true;
    }

    @Override
    public @NotNull BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
    }

    @Override
    public void onRemove(
            @NotNull BlockState p_54647_,
            @NotNull Level p_54648_,
            @NotNull BlockPos p_54649_,
            @NotNull BlockState p_54650_,
            boolean p_54651_) {
        if (!p_54651_ && !p_54647_.is(p_54650_.getBlock())) {
            if (p_54647_.getValue(POWERED)) {
                this.updateNeighbours(p_54648_, p_54649_);
            }

            super.onRemove(p_54647_, p_54648_, p_54649_, p_54650_, p_54651_);
        }
    }

    @Override
    public @NotNull BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
    }

    @Override
    public @NotNull InteractionResult use(
            @NotNull BlockState state,
            @NotNull Level level,
            @NotNull BlockPos pos,
            @NotNull Player player,
            InteractionHand hand,
            @NotNull BlockHitResult blockRayTraceResult) {
        if (hand.equals(InteractionHand.OFF_HAND)) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.PASS;
        boolean Power = !state.getValue(POWERED);

        AtomicReference<InteractionResult> interactionResultAtomicReference = new AtomicReference<>();
        interactionResultAtomicReference.set(InteractionResult.FAIL);

        state.setValue(POWERED, Power);

        AtomicReference<SoundState> soundState = new AtomicReference<>(SoundState.FAIL);
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            POWER:
            if (!cap.IsInFlight()) {
                cap.Dematerialize();
                soundState.set(SoundState.ON);
            } else if (cap.IsInFlight()) {
                cap.Land();
                soundState.set(SoundState.OFF);
            } else {
                soundState.set(SoundState.FAIL);
            }

            level.setBlockAndUpdate(pos, state.setValue(POWERED, Power));
            interactionResultAtomicReference.set(InteractionResult.SUCCESS);
        });

        // level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F,
        // 0.5f);
        switch (soundState.get()) {
            case ON -> level.playSound(null, pos, TTSSounds.THROTTLE_ON.get(), SoundSource.BLOCKS);
            case OFF -> level.playSound(null, pos, TTSSounds.THROTTLE_OFF.get(), SoundSource.BLOCKS);
            default -> level.playSound(null, pos, SoundEvents.NOTE_BLOCK_BIT.get(), SoundSource.BLOCKS);
        }
        level.gameEvent(player, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        return interactionResultAtomicReference.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
        super.createBlockStateDefinition(StateDefinition);
        StateDefinition.add(FACING);
        StateDefinition.add(POWERED);
    }

    private void updateNeighbours(Level p_54682_, BlockPos p_54683_) {
        p_54682_.updateNeighborsAt(p_54683_, this);
    }

    public enum SoundState {
        ON,
        OFF,
        FAIL;
    }
}
