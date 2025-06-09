/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.TTSTileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MonitorTile extends AbstractMonitorTile {
    public MonitorTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.MONITOR_TILE.get(), pos, state);
    }
}
