/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.blocks.tardis;

import static com.code.tama.tts.core.blocks.tardis.ExteriorBlock.DOORS;
import static com.code.tama.tts.core.blocks.tardis.ExteriorBlock.FACING;

import com.code.tama.tts.core.tileentities.multiblock.ImAMultiblock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

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
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos,
			@NotNull CollisionContext context) {
		return state.getValue(DOORS)
				? ExteriorBlock.SHAPE_OPEN.GetShapeFromRotation(state.getValue(FACING)).move(0,
						BlockUtils.getReverseHeightModifier(getter.getBlockState(pos.below())) + -1, 0)
				: ExteriorBlock.SHAPE_CLOSED.GetShapeFromRotation(state.getValue(FACING)).move(0,
						BlockUtils.getReverseHeightModifier(getter.getBlockState(pos.below())) + -1, 0);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof ImAMultiblock master)
				master.removeThis(level, pos);
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult whoTfKnows) {
		if (level.getBlockState(pos.below()).getBlock() instanceof ExteriorBlock block)
			block.use(state, level, pos.below(), player, hand, whoTfKnows);

		return super.use(state, level, pos, player, hand, whoTfKnows);
	}
}
