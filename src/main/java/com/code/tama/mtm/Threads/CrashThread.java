package com.code.tama.mtm.Threads;

import com.code.tama.mtm.Blocks.ExteriorBlock;
import com.code.tama.mtm.Blocks.MBlocks;
import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import com.code.tama.mtm.TileEntities.ExteriorTile;
import com.code.tama.mtm.misc.BlockHelper;
import com.code.tama.mtm.misc.SpaceTimeCoordinate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import static com.code.tama.mtm.Blocks.ExteriorBlock.FACING;

public class CrashThread extends Thread {
    ITARDISLevel itardisLevel;

    public CrashThread(ITARDISLevel itardisLevel) {
        this.itardisLevel = itardisLevel;
    }

    @Override
    public void run() {
        if (this.itardisLevel.GetLevel().isClientSide) return;

        ServerLevel CurrentLevel = this.itardisLevel.GetLevel().getServer().getLevel(this.itardisLevel.GetCurrentLevel());
        CurrentLevel.setChunkForced((int) (this.itardisLevel.GetDestination().GetX() / 16), (int) (this.itardisLevel.GetDestination().GetZ() / 16), true);
        BlockPos pos = BlockHelper.snapToGround(this.itardisLevel.GetLevel(), this.itardisLevel.GetDestination().GetBlockPos());

        this.itardisLevel.GetFlightTerminationPolicy().GetProtocol().OnLand(this.itardisLevel, pos, CurrentLevel);
        pos = this.itardisLevel.GetFlightTerminationPolicy().GetProtocol().GetLandPos();

        SpaceTimeCoordinate coords = new SpaceTimeCoordinate(pos);
        this.itardisLevel.SetExteriorLocation(coords);
        this.itardisLevel.SetDestination(coords);
        this.itardisLevel.SetFacing(this.itardisLevel.GetDestinationFacing());

        BlockState exteriorBlockState = MBlocks.EXTERIOR_BLOCK.get().defaultBlockState().setValue(FACING, this.itardisLevel.GetFacing());

        ExteriorBlock exteriorBlock = (ExteriorBlock) exteriorBlockState.getBlock();
        exteriorBlockState.setValue(FACING, this.itardisLevel.GetFacing());
        exteriorBlock.SetInteriorKey(this.itardisLevel.GetLevel().dimension());

        CurrentLevel.setBlock(this.itardisLevel.GetDestination().GetBlockPos(), MBlocks.EXTERIOR_BLOCK.get().defaultBlockState(), 3);
        BlockState blockState = this.itardisLevel.GetLevel().getBlockState(this.itardisLevel.GetExteriorLocation().GetBlockPos());
        this.itardisLevel.GetLevel().setBlockAndUpdate(coords.GetBlockPos(), blockState);

        // The pos needs to be final or effectively final
        BlockPos finalPos = pos;
        CurrentLevel.getServer().execute(new TickTask(1, () -> {
            this.itardisLevel.SetExteriorTile(((ExteriorTile) CurrentLevel.getBlockEntity(finalPos)));
        }));
        CurrentLevel.setChunkForced((int) (this.itardisLevel.GetDestination().GetX() / 16), (int) (this.itardisLevel.GetDestination().GetZ() / 16), false);
        this.itardisLevel.UpdateClient();
        super.run();
    }
}
