/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class NetherReactorCoreBlock extends AbstractSubsystemBlock {
    public NetherReactorCoreBlock(Properties p_49795_) {
        super(p_49795_, new NetherReactorCoreSubsystem());
    }

    /**
     * When the subsystem is activated
     **/
    @Override
    public void OnActivate(Level level, BlockPos blockPos) {}
}
