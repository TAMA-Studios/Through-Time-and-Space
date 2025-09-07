/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class TerminationProtocolHandler {
    FlightTerminationProtocol protocol;
    public BlockPos landPos;

    public TerminationProtocolHandler(FlightTerminationProtocol protocol) {
        this.protocol = protocol;
    }

    public BlockPos GetLandPos() {
        return this.landPos;
    }

    /**
     * Called when the TARDIS flight sequence is terminated (Right before the
     * exterior tile is placed)
     *
     * @param blockPos
     *            the destination position
     * @param itardisLevel
     *            the capability of the TARDIS interior level
     * @param level
     *            The level that the Exterior will be placed in
     **/
    public void OnLand(ITARDISLevel itardisLevel, BlockPos blockPos, Level level) {
        CameraShakeHandler.startShake(this.protocol.LandShakeAmount, 40);
        if (this.ShouldBeInaccurate())
            this.SetLandPos(level.getBlockRandomPos(
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(), (int) ((this.protocol.Accuracy - 1) * 10)));
    }

    public void SetLandPos(BlockPos pos) {
        this.landPos = pos;
    }

    public boolean ShouldBeInaccurate() {
        return new Random(System.currentTimeMillis()).nextFloat(1) < this.protocol.Accuracy;
    }
}
