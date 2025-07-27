/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class PoliteTerminusProtocol extends TerminationProtocol {
    public PoliteTerminusProtocol() {}

    @Override
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        CameraShakeHandler.startShake(10, 40);
        this.SetLandPos(level.getBlockRandomPos(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5));
    }
}
