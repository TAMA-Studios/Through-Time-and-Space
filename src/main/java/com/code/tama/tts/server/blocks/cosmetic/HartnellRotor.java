/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.cosmetic;

import com.code.tama.tts.server.tileentities.HartnellRotorTile;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Slf4j
public class HartnellRotor extends Block implements EntityBlock {
	public HartnellRotor(BlockBehaviour.Properties blockBehaviour) {
		super(blockBehaviour);
		this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.POWERED);
	}

	private boolean getNeighborSignal(SignalGetter signalGetter, BlockPos pos) {
		for (Direction direction : Direction.values()) {
			if (signalGetter.hasSignal(pos.relative(direction), direction)) {
				return true;
			}
		}

		if (signalGetter.hasSignal(pos, Direction.DOWN)) {
			return true;
		} else {
			BlockPos blockpos = pos.above();

			for (Direction direction1 : Direction.values()) {
				if (direction1 != Direction.DOWN && signalGetter.hasSignal(blockpos.relative(direction1), direction1)) {
					return true;
				}
			}

			return false;
		}
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_,
			@NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
		return HartnellRotorTile::tick;
	}

	@SuppressWarnings("deprecation")
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block,
			@NotNull BlockPos pos1, boolean piston) {
		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos,
					state.setValue(BlockStateProperties.POWERED, this.getNeighborSignal(level, pos)));
		}
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HartnellRotorTile(pos, state);
	}

	@SuppressWarnings("deprecation")
	public void onPlace(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState state1,
			boolean piston) {
		if (!state1.is(state.getBlock())) {
			if (!level.isClientSide && level.getBlockEntity(pos) == null) {
				level.setBlockAndUpdate(pos,
						state.setValue(BlockStateProperties.POWERED, this.getNeighborSignal(level, pos)));
			}
		}
	}

	public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity entity,
			@NotNull ItemStack stack) {
		if (!level.isClientSide) {
			level.setBlockAndUpdate(pos,
					state.setValue(BlockStateProperties.POWERED, this.getNeighborSignal(level, pos)));
		}
	}
}
