/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DematerializationCircuit extends AbstractSubsystem {
    @Override
    public Map<BlockPos, BlockState> BlockMap() {
        Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
        map.put(BlockPos.ZERO.above(), Blocks.IRON_BLOCK.defaultBlockState());

        return map;
    }

    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
        this.Activated = true;
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> cap.GetSubsystemsData().setDematerializationCircuit(this));
    }
}
