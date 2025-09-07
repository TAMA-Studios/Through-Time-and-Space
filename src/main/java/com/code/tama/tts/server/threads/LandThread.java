/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import static com.code.tama.tts.server.blocks.ExteriorBlock.FACING;

import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.BlockHelper;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.registries.LandingTypeRegistry;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

public class LandThread extends Thread {
    ITARDISLevel itardisLevel;

    public LandThread(ITARDISLevel itardisLevel) {
        this.setName("Landing Thread");
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        if (!this.itardisLevel.GetLevel().isClientSide) {

            ServerLevel CurrentLevel =
                    this.itardisLevel.GetLevel().getServer().getLevel(this.itardisLevel.GetCurrentLevel());
            assert CurrentLevel != null;
            CurrentLevel.setChunkForced(
                    (int) (this.itardisLevel.GetDestination().GetX() / 16),
                    (int) (this.itardisLevel.GetDestination().GetZ() / 16),
                    true);

            BlockPos pos = BlockHelper.snapToGround(
                    this.itardisLevel.GetLevel(),
                    this.itardisLevel.GetDestination().GetBlockPos());

            this.itardisLevel.GetFlightTerminationPolicy().GetProtocol().OnLand(this.itardisLevel, pos, CurrentLevel);
            pos = this.itardisLevel.GetFlightTerminationPolicy().GetProtocol().GetLandPos();

            pos = LandingTypeRegistry.UP.GetLandingPos(pos, CurrentLevel);

            SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos);

            this.itardisLevel.SetExteriorLocation(coords);
            this.itardisLevel.SetDestination(coords);
            this.itardisLevel.SetFacing(this.itardisLevel.GetDestinationFacing());

            BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();

            ExteriorBlock exteriorBlock = (ExteriorBlock) exteriorBlockState
                    .setValue(FACING, this.itardisLevel.GetFacing())
                    .getBlock();
            exteriorBlock.SetInteriorKey(this.itardisLevel.GetLevel().dimension());

            CurrentLevel.setBlock(
                    this.itardisLevel.GetDestination().GetBlockPos(),
                    TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState(),
                    3);
            BlockState blockState = this.itardisLevel
                    .GetLevel()
                    .getBlockState(this.itardisLevel.GetExteriorLocation().GetBlockPos());
            this.itardisLevel.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), blockState);
            if (CurrentLevel.getBlockEntity(pos) != null) {
                BlockPos finalPos = pos; // pos used in lambda must be final or effectively final
                CurrentLevel.getServer()
                        .execute(new TickTask(
                                1,
                                () -> this.itardisLevel.SetExteriorTile(
                                        ((ExteriorTile) CurrentLevel.getBlockEntity(finalPos)))));
            }
            CurrentLevel.setChunkForced(
                    (int) (this.itardisLevel.GetDestination().GetX() / 16),
                    (int) (this.itardisLevel.GetDestination().GetZ() / 16),
                    false);
            this.itardisLevel.SetPlayRotorAnimation(false);
            this.itardisLevel.UpdateClient();
        }

        MinecraftForge.EVENT_BUS.post(new TardisEvent.Land(this.itardisLevel, TardisEvent.State.END));
        super.run();
    }
}
