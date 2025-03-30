package com.code.tama.mtm.server.tardis.terminationprotocol;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PoliteTerminusProtocol extends TerminationProtocol {
    public PoliteTerminusProtocol() {}

    @Override
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5));
    }
}