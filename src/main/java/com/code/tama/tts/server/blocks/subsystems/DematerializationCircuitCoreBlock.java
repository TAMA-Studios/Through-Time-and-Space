/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DematerializationCircuitCoreBlock extends AbstractSubsystemBlock {
    public DematerializationCircuitCoreBlock() {
        super(BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.METAL), new DematerializationCircuit());
    }

    /**
     * When the subsystem is activated
     **/
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
        this.getSubsystem().OnActivate(level, blockPos);
    }

    @Override
    public void OnDeActivate(Level level, BlockPos blockPos) {
        this.getSubsystem().OnDeActivate(level, blockPos);
    }
}
