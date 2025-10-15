/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import com.code.tama.triggerapi.helpers.world.WorldHelper;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LandingTypeUP extends LandingType {

    @Override
    public BlockPos GetLandingPos(SpaceTimeCoordinate CurrentLandingPos, ServerLevel level) {
        return CurrentLandingPos.GetBlockPos()
                .atY(WorldHelper.getSurfaceHeight(level, ((int) CurrentLandingPos.GetX()), ((int) CurrentLandingPos.GetX())) + 1);
    }

    @Override
    public BlockPos GetLandingPos(BlockPos CurrentLandingPos, ServerLevel level) {
        return CurrentLandingPos.atY(
                WorldHelper.getSurfaceHeight(level, ((int) CurrentLandingPos.getX()), ((int) CurrentLandingPos.getZ()))
                        + 1);
    }
}
