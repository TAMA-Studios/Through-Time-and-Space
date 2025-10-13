/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.triggerapi.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LandingTypeUP extends LandingType {

    @Override
    public BlockPos GetLandingPos(SpaceTimeCoordinate CurrentLandingPos, ServerLevel level) {
        return CurrentLandingPos.GetBlockPos()
                .atY(WorldHelper.getSurfaceHeight(level, ((int) CurrentLandingPos.X), ((int) CurrentLandingPos.X)) + 1);
    }

    @Override
    public BlockPos GetLandingPos(BlockPos CurrentLandingPos, ServerLevel level) {
        return CurrentLandingPos.atY(
                WorldHelper.getSurfaceHeight(level, ((int) CurrentLandingPos.getX()), ((int) CurrentLandingPos.getZ()))
                        + 1);
    }
}
