/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.monitors;

import com.code.tama.tts.server.registries.forge.TTSTileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CRTMonitorTile extends AbstractMonitorTile {
	public CRTMonitorTile(BlockPos pos, BlockState state) {
		super(TTSTileEntities.CRT_MONITOR_TILE.get(), pos, state);
	}
}
