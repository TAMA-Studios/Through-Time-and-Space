package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.MTMTileEntities;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MonitorTile extends BlockEntity {
    public MonitorTile(BlockPos pos, BlockState state) {
        super(MTMTileEntities.MONITOR_TILE.get(), pos, state);
    }

    @Override
    public void onLoad() {
        this.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::UpdateClient);
        super.onLoad();
    }
}
