/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.blocks.subsystems.AbstractSubsystemBlock;
import com.code.tama.tts.server.enums.SonicInteractionType;
import com.code.tama.tts.server.items.SonicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FragmentLinksBlock extends Block {

    private static void makeParticle(LevelAccessor levelAccessor, BlockPos blockPos) {
        Direction direction = Direction.NORTH;
        Direction direction1 = Direction.NORTH;
        double d0 = (double) blockPos.getX()
                + 0.5D
                + 0.1D * (double) direction.getStepX()
                + 0.2D * (double) direction1.getStepX();
        double d1 = (double) blockPos.getY()
                + 0.5D
                + 0.1D * (double) direction.getStepY()
                + 0.2D * (double) direction1.getStepY();
        double d2 = (double) blockPos.getZ()
                + 0.5D
                + 0.1D * (double) direction.getStepZ()
                + 0.2D * (double) direction1.getStepZ();
        levelAccessor.addParticle(
                new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0F),
                d0,
                d1,
                d2,
                0.0D,
                0.0D,
                0.0D);
    }

    public FragmentLinksBlock(Properties p_49795_) {
        super(p_49795_);
    }

    /**
     * @param direction
     *            If this isn't the master link, it WON'T check the opposite
     *            direction to prevent recursion
     * @param Master
     *            If this is the "Master" links, or the very first link that checks
     *            the others.
     */
    public void LoopTest(Level level, BlockPos pos, Direction direction, boolean Master, int iteration) {
        if (direction == null) direction = Direction.NORTH;
        for (Direction testing : Direction.values()) {
            if (!testing.equals(direction.getOpposite()) || Master) {
                if (level.getBlockState(pos.relative(testing, 1)).getBlock()
                        instanceof FragmentLinksBlock fragmentLinks) {
                    if (iteration > 14) return;

                    fragmentLinks.LoopTest(level, pos.relative(testing, 1), testing, false, iteration + 1);
                    makeParticle(level, pos.above());

                    System.out.println(iteration);
                }

                if (level.getBlockState(pos.relative(testing, 1)).getBlock()
                        instanceof AbstractSubsystemBlock abstractSubsystemBlock) {
                    abstractSubsystemBlock.OnIntegration(level, pos.relative(testing, 1));
                }
            }
        }
    }

    public boolean TestForEngine(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).getBlock() instanceof EngineBlock) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState state, Level level, BlockPos blockPos, BlockState state1, boolean p_60570_) {
        super.onPlace(state, level, blockPos, state1, p_60570_);
        this.LoopTest(level, blockPos, Direction.NORTH, true, 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos blockPos,
            Player player,
            InteractionHand interactionHand,
            BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).getItem() instanceof SonicItem sonicItem) {
            if (!sonicItem.InteractionType.equals(SonicInteractionType.BLOCKS))
                return super.use(state, level, blockPos, player, interactionHand, blockHitResult);
            if (!this.TestForEngine(level, blockPos))
                return super.use(state, level, blockPos, player, interactionHand, blockHitResult);

            // TODO: Add sound
            this.LoopTest(level, blockPos, null, true, 0);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, blockPos, player, interactionHand, blockHitResult);
    }
}
