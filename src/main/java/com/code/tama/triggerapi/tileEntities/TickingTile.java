/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.tileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TickingTile extends BlockEntity {
	public TickingTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	public static <T extends BlockEntity> void tick(Level ignoredLevel, BlockPos ignoredblockPos,
			BlockState ignoredState, T tile) {
		((TickingTile) tile).tick();
	}

	public abstract void tick();
}
