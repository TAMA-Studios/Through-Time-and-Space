/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DoorTile extends BlockEntity {
    public DoorTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TTSTileEntities.DOOR_TILE.get(), p_155229_, p_155230_);
    }
}