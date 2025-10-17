/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.forge.TTSTileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleTileEntity extends BlockEntity {
	public ExampleTileEntity(BlockPos pos, BlockState state) {
		super(TTSTileEntities.EXAMPLE_TILE.get(), pos, state);
	}
}
