package com.code.tama.tts.server.tardis.subsystems;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DematerializationCircuit extends AbstractSubsystem {
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
        this.Activated = true;
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> cap.GetSubsystemsData().setDematerializationCircuit(this));

    }

    @Override
    public Map<BlockPos, BlockState> BlockMap() {
        Map<BlockPos, BlockState> map = new java.util.HashMap<>(Map.of());
        map.put(BlockPos.ZERO.above(), Blocks.IRON_BLOCK.defaultBlockState());

        return map;
    }
}
