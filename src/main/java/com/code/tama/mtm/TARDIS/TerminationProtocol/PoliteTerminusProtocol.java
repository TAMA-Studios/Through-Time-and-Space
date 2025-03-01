package com.code.tama.mtm.TARDIS.TerminationProtocol;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PoliteTerminusProtocol extends TerminationProtocol {
    public PoliteTerminusProtocol() {}

    @Override
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5));
    }
}