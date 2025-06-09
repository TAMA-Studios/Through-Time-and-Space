/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.tardis.control_lists.AbstractControlList;
import com.code.tama.tts.server.tardis.control_lists.ControlLists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ConsoleTile extends AbstractConsoleTile {
    public ConsoleTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TTSTileEntities.HUDOLIN_CONSOLE_TILE.get(), p_155229_, p_155230_);
    }

    @Override
    AbstractControlList GetControlList() {
        return ControlLists.GetHudolin();
    }
}
