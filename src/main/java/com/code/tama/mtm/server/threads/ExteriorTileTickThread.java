package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ExteriorTileTickThread extends Thread {
    Level level;
    BlockPos pos;
    BlockState state;
    ExteriorTile exteriorTile;
    boolean IsInstantiated = true;

    public ExteriorTileTickThread(Level level, BlockPos pos, BlockState state, ExteriorTile exteriorTile) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.exteriorTile = exteriorTile;
        this.IsInstantiated = false;
    }

    public ExteriorTileTickThread() {
        this.IsInstantiated = true;
    }

    public boolean IsInstantiated() {return this.IsInstantiated;}

    public void Init(Level level, BlockPos pos, BlockState state, ExteriorTile exteriorTile) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.exteriorTile = exteriorTile;
        this.IsInstantiated = false;
    }

    @Override
    public void run() {
        if(this.IsInstantiated) return;
        super.run();
        return;
    }
}
