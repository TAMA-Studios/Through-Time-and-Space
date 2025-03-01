package com.code.tama.mtm.TileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DoorTile extends BlockEntity {
    public DoorTile(BlockPos p_155229_, BlockState p_155230_) {
        super(MTileEntities.DOOR_TILE.get(), p_155229_, p_155230_);
    }
}