/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.monitors;

import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class MonitorPanelTile extends AbstractMonitorTile {
    public MonitorPanelTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.MONITOR_PANEL_TILE.get(), pos, state);
    }
}
