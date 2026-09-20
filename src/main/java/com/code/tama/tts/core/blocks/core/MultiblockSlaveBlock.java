/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.core;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MultiblockSlaveBlock extends Block implements EntityBlock {
	public MultiblockSlaveBlock(Properties props) {
		super(props);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return TTSTileEntities.MULTIBLOCK_SLAVE_TILE.get().create(blockPos, blockState);
	}
}
