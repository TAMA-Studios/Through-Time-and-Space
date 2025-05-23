package com.code.tama.mtm.server.tileentities;

import com.code.tama.mtm.server.registries.MTMTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleTileEntity extends BlockEntity {
    public ExampleTileEntity(BlockPos pos, BlockState state) {
        super(MTMTileEntities.DOOR_TILE.get(), pos, state);
    }
}
