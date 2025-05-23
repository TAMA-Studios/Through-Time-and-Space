package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.server.blocks.ExteriorBlock;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.misc.BlockHelper;
import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import com.code.tama.mtm.server.registries.MTMBlocks;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

import static com.code.tama.mtm.server.blocks.ExteriorBlock.FACING;

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

        BlockState exteriorBlockState = MTMBlocks.EXTERIOR_BLOCK.get().defaultBlockState().setValue(FACING, this.itardisLevel.GetFacing());

        ExteriorBlock exteriorBlock = (ExteriorBlock) exteriorBlockState.getBlock();
        exteriorBlockState.setValue(FACING, this.itardisLevel.GetFacing());
        exteriorBlock.SetInteriorKey(this.itardisLevel.GetLevel().dimension());

        CurrentLevel.setBlock(this.itardisLevel.GetDestination().GetBlockPos(), MTMBlocks.EXTERIOR_BLOCK.get().defaultBlockState(), 3);
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
