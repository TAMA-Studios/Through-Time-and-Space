/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.blocks.subsystems.AbstractSubsystemBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FragmentLinksBlock extends Block {

    public FragmentLinksBlock(Properties p_49795_) {
        super(p_49795_);
    }

    /**
     * @param direction If this isn't the master link, it WON'T check the opposite direction to prevent recursion
     * @param Master    If this is the "Master" links, or the very first link that checks the others.
     */
    public void LoopTest(Level level, BlockPos pos, Direction direction, boolean Master, int iteration) {
        for (Direction testing : Direction.values()) {
            if (!testing.equals(direction.getOpposite()) || Master) {
                if (level.getBlockState(pos.relative(testing, 1)).getBlock() instanceof FragmentLinksBlock fragmentLinks) {
                    if (iteration > 14)
                        return;

                    fragmentLinks.LoopTest(level, pos.relative(testing, 1), testing, false, iteration + 1);
                    makeParticle(level, pos.above());

                    System.out.println(iteration);
                }

                if (level.getBlockState(pos.relative(testing, 1)).getBlock() instanceof AbstractSubsystemBlock abstractSubsystemBlock) {
                    abstractSubsystemBlock.OnIntegration(level, pos.relative(testing, 1));
                }
            }
        }
    }

    private static void makeParticle(LevelAccessor levelAccessor, BlockPos blockPos) {
        Direction direction = Direction.NORTH;
        Direction direction1 = Direction.NORTH;
        double d0 = (double)blockPos.getX() + 0.5D + 0.1D * (double)direction.getStepX() + 0.2D * (double)direction1.getStepX();
        double d1 = (double)blockPos.getY() + 0.5D + 0.1D * (double)direction.getStepY() + 0.2D * (double)direction1.getStepY();
        double d2 = (double)blockPos.getZ() + 0.5D + 0.1D * (double)direction.getStepZ() + 0.2D * (double)direction1.getStepZ();
        levelAccessor.addParticle(new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0F), d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        this.LoopTest(p_60567_, p_60568_, Direction.NORTH, true, 0);
    }
}