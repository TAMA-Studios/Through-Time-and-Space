package com.code.tama.mtm.TARDIS.TerminationProtocol;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import com.code.tama.mtm.Client.CameraShakeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class QuickStopProtocol extends TerminationProtocol {
    public QuickStopProtocol() {}

    @Override
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        CameraShakeHandler.startShake(20, 40);
        this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 25));
    }
}