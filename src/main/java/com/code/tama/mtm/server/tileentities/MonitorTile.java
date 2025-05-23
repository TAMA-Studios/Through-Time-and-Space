package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.registries.MTMTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MonitorTile extends AbstractMonitorTile {
    public MonitorTile(BlockPos pos, BlockState state) {
        super(MTMTileEntities.MONITOR_TILE.get(), pos, state);
    }
}
