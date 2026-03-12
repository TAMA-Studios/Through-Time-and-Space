/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.tileentities.boti;

import javax.annotation.Nullable;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BotiWindowBlock extends Block implements EntityBlock {
	public BotiWindowBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos,
			boolean isMoving) {
		super.neighborChanged(state, level, pos, block, fromPos, isMoving);
		if (level.getBlockEntity(pos) instanceof BotiWindowTile tile) {
			tile.invalidateCluster();
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (level.getBlockEntity(pos) instanceof BotiWindowTile tile) {
			tile.invalidateCluster(); // notify neighbors before removal
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
			@NotNull BlockEntityType<T> type) {
		return type == TTSTileEntities.BOTI_WINDOW.get() ? BotiWindowTile::tick : null;
	}

	@Nullable @Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return TTSTileEntities.BOTI_WINDOW.get().create(pos, state);
	}
}
