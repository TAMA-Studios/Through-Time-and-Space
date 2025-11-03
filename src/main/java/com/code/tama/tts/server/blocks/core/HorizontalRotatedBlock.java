package com.code.tama.tts.server.blocks.core;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class HorizontalRotatedBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public HorizontalRotatedBlock(BlockBehaviour.Properties p_55926_) {
		super(p_55926_);
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	@Override
	public @NotNull BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
		return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
	}

	public @NotNull BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
		return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
	}

	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> p_55933_) {
		super.createBlockStateDefinition(p_55933_);
		p_55933_.add(FACING);
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_55928_) {
		return this.defaultBlockState().setValue(FACING, p_55928_.getClickedFace().getOpposite());
	}
}