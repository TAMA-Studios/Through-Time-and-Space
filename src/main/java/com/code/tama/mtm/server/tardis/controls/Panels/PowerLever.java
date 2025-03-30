package com.code.tama.mtm.server.tardis.controls.Panels;

import com.code.tama.mtm.server.blocks.VoxelRotatedShape;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWERED;

@SuppressWarnings("deprecation")
public class PowerLever extends FaceAttachedHorizontalDirectionalBlock {
    public PowerLever(Properties p_54120_) {
        super(p_54120_);
    }

    public VoxelRotatedShape SHAPE = new VoxelRotatedShape(createShapeON().optimize());

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(POWERED, false);
    }

    public @NotNull InteractionResult use(BlockState p_54640_, Level p_54641_, BlockPos p_54642_, Player p_54643_, InteractionHand p_54644_, BlockHitResult p_54645_) {
        BlockState blockstate1;
        if (p_54641_.isClientSide) {
            blockstate1 = (BlockState)p_54640_.cycle(POWERED);
            if ((Boolean)blockstate1.getValue(POWERED)) {
                makeParticle(blockstate1, p_54641_, p_54642_, 1.0F);
            }

            return InteractionResult.SUCCESS;
        } else {
            p_54641_.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap ->
                    cap.SetPowered(!p_54640_.getValue(POWERED)));

            blockstate1 = this.pull(p_54640_, p_54641_, p_54642_);
            p_54641_.playSound(null, p_54642_, SoundEvents.ARROW_HIT_PLAYER, SoundSource.BLOCKS);
            p_54641_.gameEvent(p_54643_, blockstate1.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, p_54642_);
            return InteractionResult.CONSUME;
        }
    }

    private void updateNeighbours(BlockState p_54681_, Level p_54682_, BlockPos p_54683_) {
        p_54682_.updateNeighborsAt(p_54683_, this);
        p_54682_.updateNeighborsAt(p_54683_.relative(getConnectedDirection(p_54681_).getOpposite()), this);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return SHAPE.GetShapeFromRotation(p_60555_.getValue(FACING));
    }

    private static void makeParticle(BlockState p_54658_, LevelAccessor p_54659_, BlockPos p_54660_, float p_54661_) {
        Direction direction = ((Direction)p_54658_.getValue(FACING)).getOpposite();
        Direction direction1 = getConnectedDirection(p_54658_).getOpposite();
        double d0 = (double)p_54660_.getX() + 0.5 + 0.1 * (double)direction.getStepX() + 0.2 * (double)direction1.getStepX();
        double d1 = (double)p_54660_.getY() + 0.5 + 0.1 * (double)direction.getStepY() + 0.2 * (double)direction1.getStepY();
        double d2 = (double)p_54660_.getZ() + 0.5 + 0.1 * (double)direction.getStepZ() + 0.2 * (double)direction1.getStepZ();
        p_54659_.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, p_54661_), d0, d1, d2, 0.0, 0.0, 0.0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state) {
        state.add(FACE, FACING, POWERED);
    }

    public boolean isSignalSource(BlockState p_54675_) {
        return true;
    }

    public int getSignal(BlockState p_54635_, BlockGetter p_54636_, BlockPos p_54637_, Direction p_54638_) {
        return p_54635_.getValue(POWERED) ? 15 : 0;
    }

    public void onRemove(BlockState p_54647_, Level p_54648_, BlockPos p_54649_, BlockState p_54650_, boolean p_54651_) {
        if (!p_54651_ && !p_54647_.is(p_54650_.getBlock())) {
            if ((Boolean)p_54647_.getValue(POWERED)) {
                this.updateNeighbours(p_54647_, p_54648_, p_54649_);
            }

            super.onRemove(p_54647_, p_54648_, p_54649_, p_54650_, p_54651_);
        }

    }

    public BlockState pull(BlockState p_54677_, Level p_54678_, BlockPos p_54679_) {
        p_54677_ = p_54677_.cycle(POWERED);
        p_54678_.setBlock(p_54679_, p_54677_, 3);
        this.updateNeighbours(p_54677_, p_54678_, p_54679_);
        return p_54677_;
    }

    public VoxelShape createShapeON() {
        return Block.box(2, -0.5000000000000001, 0, 14, 2.4999999999999996, 16);
    }
}
