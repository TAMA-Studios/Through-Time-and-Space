package com.code.tama.mtm.server.blocks.subsystems;

import com.code.tama.mtm.server.registries.MTMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class NetherReactorCoreSubsystem extends AbstractSubsystemBlock {
    public NetherReactorCoreSubsystem(Properties p_49795_) {
        super(p_49795_);
    }

    /**
     * When the subsystem is activated
     **/
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
    }

    /**
     * the Map uses a relative BlockPos, and the Default Blockstate that make up this subsystem
     **/
    @Override
    public Map<BlockPos, BlockState> BlockMap() {
        Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
        map.put(BlockPos.ZERO, MTMBlocks.NETHER_REACTOR_CORE.get().defaultBlockState());

        map.put(BlockPos.ZERO.above(), Blocks.COBBLESTONE.defaultBlockState());

        // Cobblestone + at the top
        map.put(BlockPos.ZERO.above(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.above().north(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.above().east(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.above().south(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.above().west(), Blocks.COBBLESTONE.defaultBlockState());

        // Cobblestone corners in the middle
        map.put(BlockPos.ZERO.north().east(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.north().west(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.south().east(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.south().west(), Blocks.COBBLESTONE.defaultBlockState());

        // Redstone under the reactor core
        map.put(BlockPos.ZERO.below(), Blocks.REDSTONE_BLOCK.defaultBlockState());

        // Cobble + at the bottom
        map.put(BlockPos.ZERO.below().north(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.below().east(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.below().south(), Blocks.COBBLESTONE.defaultBlockState());
        map.put(BlockPos.ZERO.below().west(), Blocks.COBBLESTONE.defaultBlockState());

        // Gold corners at the bottom
        map.put(BlockPos.ZERO.below().north().east(), Blocks.GOLD_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().north().west(), Blocks.GOLD_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().south().east(), Blocks.GOLD_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().south().west(), Blocks.GOLD_BLOCK.defaultBlockState());
        return map;
    }
}