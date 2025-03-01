package com.code.tama.mtm.TARDIS.TerminationProtocol;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import com.code.tama.mtm.Client.CameraShakeHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class EmergencyStopProtocol extends TerminationProtocol {
    public EmergencyStopProtocol() {}

    @Override
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        // 50/50 chance of crashing
        if(level.getRandom().nextInt(2) > 1)
            itardisLevel.Crash();

        CameraShakeHandler.startShake(20, 40);
        this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 50));
    }
}
