/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import java.util.Map;

import com.code.tama.tts.server.registries.TTSBlocks;

import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class NetherReactorCoreBlock extends AbstractSubsystemBlock {
    public NetherReactorCoreBlock(Properties p_49795_) {
        super(p_49795_, new NetherReactorCoreSubsystem());
    }

    /**
     * When the subsystem is activated
     **/
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {
    }
}