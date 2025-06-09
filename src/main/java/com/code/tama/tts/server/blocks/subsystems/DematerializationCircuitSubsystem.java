/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class DematerializationCircuitSubsystem extends AbstractSubsystemBlock {
    public DematerializationCircuitSubsystem() {
        super(BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.METAL));
    }

    /**
     * When the subsystem is activated
     **/
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
        this.Activated = true;
        if(this.IsValid(level, blockPos))
                 level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> cap.GetSubsystemsData().setDematerializationCircuit(this));
    }

    /**
     * the Map uses a relative BlockPos, and the Default Blockstate that make up this subsystem
     **/
    @Override
    public Map<BlockPos, BlockState> BlockMap() {
        Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
        map.put(BlockPos.ZERO.above(), Blocks.IRON_BLOCK.defaultBlockState());

        return map;
    }
}