/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.triggerapi.WorldHelper;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class LandThread extends Thread {
    ITARDISLevel itardisLevel;

    public LandThread(ITARDISLevel itardisLevel) {
        this.setName("Landing Thread");
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        if (this.itardisLevel.GetLevel().isClientSide) return;

        ServerLevel CurrentLevel =
                this.itardisLevel.GetLevel().getServer().getLevel(this.itardisLevel.GetCurrentLevel());
        CurrentLevel.setChunkForced(
                (int) (this.itardisLevel.GetDestination().GetX() / 16),
                (int) (this.itardisLevel.GetDestination().GetZ() / 16),
                true);
        BlockPos pos = this.itardisLevel
                .GetDestination()
                .GetBlockPos()
                .atY(WorldHelper.SafeBottomY(
                        CurrentLevel,
                        this.itardisLevel.GetDestination().GetBlockPos())); // BlockHelper.snapToGround(CurrentLevel,
        // this.itardisLevel.GetDestination().GetBlockPos());
        pos.offset(
                0,
                WorldHelper.getSurfaceHeight(
                        CurrentLevel, ((int) this.itardisLevel.GetDestination().GetX()), ((int)
                                this.itardisLevel.GetDestination().GetZ())),
                0);

        SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos);

        this.itardisLevel.SetExteriorLocation(coords);
        this.itardisLevel.SetDestination(coords);
        this.itardisLevel.SetFacing(this.itardisLevel.GetDestinationFacing());

        BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK
                .get()
                .defaultBlockState()
                .rotate(TARDISLevelCapability.DirectionToRotation(this.itardisLevel.GetFacing()));

        ExteriorBlock exteriorBlock = (ExteriorBlock) exteriorBlockState.getBlock();
        exteriorBlockState.setValue(ExteriorBlock.FACING, this.itardisLevel.GetFacing());
        exteriorBlock.SetInteriorKey(this.itardisLevel.GetLevel().dimension());

        CurrentLevel.setBlock(
                this.itardisLevel.GetDestination().GetBlockPos(),
                TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState(),
                3);
        BlockState blockState = this.itardisLevel
                .GetLevel()
                .getBlockState(this.itardisLevel.GetExteriorLocation().GetBlockPos());
        this.itardisLevel.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), blockState);
        if (CurrentLevel.getBlockEntity(pos) != null)
            CurrentLevel.getServer()
                    .execute(new TickTask(
                            1,
                            () -> this.itardisLevel.SetExteriorTile(
                                    ((ExteriorTile) CurrentLevel.getBlockEntity(pos)))));
        CurrentLevel.setChunkForced(
                (int) (this.itardisLevel.GetDestination().GetX() / 16),
                (int) (this.itardisLevel.GetDestination().GetZ() / 16),
                false);
        this.itardisLevel.SetPlayRotorAnimation(false);
        this.itardisLevel.UpdateClient();
        super.run();
    }
}
