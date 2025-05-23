package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractMonitorTile extends BlockEntity {

    public AbstractMonitorTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    @Override
    public void onLoad() {
        this.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::UpdateClient);
        super.onLoad();
    }
}
