/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.events.TardisEvent;
import com.code.tama.tts.server.misc.BlockHelper;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tardis.data.DataUpdateValues;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;

import static com.code.tama.tts.server.blocks.ExteriorBlock.FACING;

public class CrashThread extends Thread {
    ITARDISLevel itardisLevel;

    public CrashThread(ITARDISLevel itardisLevel) {
        this.setName("Crash Thread");
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        if (this.itardisLevel.GetLevel().isClientSide) return;

        ServerLevel CurrentLevel =
                this.itardisLevel.GetLevel().getServer().getLevel(this.itardisLevel.GetCurrentLevel());
        assert CurrentLevel != null;
        CurrentLevel.setChunkForced(
                (int) (this.itardisLevel.GetNavigationalData().getDestination().GetX() / 16),
                (int) (this.itardisLevel.GetNavigationalData().getDestination().GetZ() / 16),
                true);
        BlockPos pos = BlockHelper.snapToGround(
                this.itardisLevel.GetLevel(),
                this.itardisLevel.GetNavigationalData().getDestination().GetBlockPos());

        this.itardisLevel
                .GetFlightData()
                .getFlightTerminationProtocol()
                .getTerminationProtocolHandler()
                .OnLand(this.itardisLevel, pos, CurrentLevel);
        pos = this.itardisLevel
                .GetFlightData()
                .getFlightTerminationProtocol()
                .getTerminationProtocolHandler()
                .GetLandPos();

        pos = BlockHelper.snapToGround(this.itardisLevel.GetLevel(), pos);

        SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos);
        this.itardisLevel.GetNavigationalData().SetExteriorLocation(coords);
        this.itardisLevel.GetNavigationalData().setDestination(coords);
        this.itardisLevel
                .GetNavigationalData()
                .setFacing(this.itardisLevel.GetNavigationalData().getDestinationFacing());

        BlockState exteriorBlockState = TTSBlocks.EXTERIOR_BLOCK
                .get()
                .defaultBlockState()
                .setValue(FACING, this.itardisLevel.GetNavigationalData().getFacing());

        ExteriorBlock exteriorBlock = (ExteriorBlock) exteriorBlockState.getBlock();
        exteriorBlockState.setValue(
                FACING, this.itardisLevel.GetNavigationalData().getFacing());
        exteriorBlock.SetInteriorKey(this.itardisLevel.GetLevel().dimension());

        CurrentLevel.setBlock(
                this.itardisLevel.GetNavigationalData().getDestination().GetBlockPos(),
                TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState(),
                3);
        BlockState blockState = this.itardisLevel
                .GetLevel()
                .getBlockState(this.itardisLevel
                        .GetNavigationalData()
                        .GetExteriorLocation()
                        .GetBlockPos());
        this.itardisLevel.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), blockState);

        // The pos needs to be final or effectively final
        BlockPos finalPos = pos;
        CurrentLevel.getServer()
                .execute(new TickTask(
                        1,
                        () -> this.itardisLevel.SetExteriorTile(
                                ((ExteriorTile) CurrentLevel.getBlockEntity(finalPos)))));
        CurrentLevel.setChunkForced(
                (int) (this.itardisLevel.GetNavigationalData().getDestination().GetX() / 16),
                (int) (this.itardisLevel.GetNavigationalData().getDestination().GetZ() / 16),
                false);
        this.itardisLevel.UpdateClient(DataUpdateValues.FLIGHT);
        this.itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);
        MinecraftForge.EVENT_BUS.post(new TardisEvent.Crash(this.itardisLevel, TardisEvent.State.END));
        super.run();
        return;
    }
}
