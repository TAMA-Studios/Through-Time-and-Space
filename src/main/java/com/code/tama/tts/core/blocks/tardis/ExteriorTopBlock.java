/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.blocks.tardis;

import static com.code.tama.tts.core.blocks.tardis.ExteriorBlock.DOORS;
import static com.code.tama.tts.core.blocks.tardis.ExteriorBlock.FACING;

import com.code.tama.tts.core.tileentities.ExteriorTile;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class ExteriorTopBlock extends Block {
	public ExteriorTopBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
		super.createBlockStateDefinition(StateDefinition);
		StateDefinition.add(FACING);
		StateDefinition.add(DOORS);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
		if (level.isClientSide)
			return;
		if (level.getBlockEntity(pos.below()) instanceof ExteriorTile e)
			e.removeThis(level, pos.below());
		super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult whoTfKnows) {
		if (level.getBlockState(pos.below()).getBlock() instanceof ExteriorBlock block)
			block.use(state, level, pos.below(), player, hand, whoTfKnows);

		return super.use(state, level, pos, player, hand, whoTfKnows);
	}
}
