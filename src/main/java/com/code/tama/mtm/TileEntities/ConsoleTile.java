package com.code.tama.mtm.TileEntities;

import com.code.tama.mtm.TARDIS.ControlLists.AbstractControlList;
import com.code.tama.mtm.TARDIS.ControlLists.ControlLists;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class ConsoleTile extends AbstractConsoleTile {

    public ConsoleTile(BlockPos p_155229_, BlockState p_155230_) {
        super(MTileEntities.HUDOLIN_CONSOLE_TILE.get(), p_155229_, p_155230_);
    }

    @Override
    AbstractControlList GetControlList() {
        return ControlLists.GetHudolin();
    }
}