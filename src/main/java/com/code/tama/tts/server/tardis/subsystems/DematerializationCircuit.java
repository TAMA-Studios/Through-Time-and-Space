/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class DematerializationCircuit extends AbstractSubsystem {
    @Override
    public Map<BlockPos, BlockState> BlockMap() {
        Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
        map.put(BlockPos.ZERO.below(), Blocks.GOLD_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().south(), Blocks.REDSTONE_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().west(), Blocks.REDSTONE_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().east(), Blocks.REDSTONE_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().north(), Blocks.LAPIS_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().north().east(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().north().west(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.below().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());

        map.put(BlockPos.ZERO.south(), Blocks.IRON_TRAPDOOR.defaultBlockState());
        map.put(BlockPos.ZERO.west(), Blocks.IRON_TRAPDOOR.defaultBlockState());
        map.put(BlockPos.ZERO.east(), Blocks.IRON_TRAPDOOR.defaultBlockState());
        map.put(BlockPos.ZERO.north(), Blocks.AIR.defaultBlockState());
        map.put(BlockPos.ZERO.north().east(), Blocks.IRON_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.north().west(), Blocks.IRON_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.south().east(), Blocks.IRON_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.south().east(), Blocks.IRON_BLOCK.defaultBlockState());

        map.put(BlockPos.ZERO.above(), Blocks.REDSTONE_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.above().south(), Blocks.REDSTONE_LAMP.defaultBlockState());
        map.put(BlockPos.ZERO.above().west(), Blocks.REDSTONE_LAMP.defaultBlockState());
        map.put(BlockPos.ZERO.above().east(), Blocks.REDSTONE_LAMP.defaultBlockState());
        map.put(BlockPos.ZERO.above().north(), Blocks.REDSTONE_LAMP.defaultBlockState());
        map.put(BlockPos.ZERO.above().north().east(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.above().north().west(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.above().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());
        map.put(BlockPos.ZERO.above().south().east(), Blocks.COPPER_BLOCK.defaultBlockState());

        return map;
    }

    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
        this.Activated = true;
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> cap.GetSubsystemsData().setDematerializationCircuit(this));
    }
}
