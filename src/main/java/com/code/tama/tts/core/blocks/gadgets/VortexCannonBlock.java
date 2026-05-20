/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.gadgets;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.core.tileentities.VortexCannonTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class VortexCannonBlock extends Block implements EntityBlock {
	public VortexCannonBlock(BlockBehaviour.Properties props) {
		super(props);
	}

	// @Override
	// public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter
	// p_60556_, BlockPos p_60557_,
	// CollisionContext p_60558_) {
	// return Shapes.join(Block.box(0, 0, 3, 16, 2, 13), Block.box(3, 2, 6, 13, 6,
	// 10), BooleanOp.OR);
	// }

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return TTSTileEntities.VORTEX_CANNON.get().create(blockPos, blockState);
	}

	@Override
	public @NotNull InteractionResult use(BlockState p_60503_, Level level, BlockPos blockPos, Player player,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		if (p_60507_.equals(InteractionHand.OFF_HAND))
			return super.use(p_60503_, level, blockPos, player, p_60507_, p_60508_);

		if (level.getBlockEntity(blockPos) instanceof VortexCannonTile cannon) {
			if (player.getMainHandItem().getItem().equals(TTSBlocks.CORAL.get().asItem())) {
				player.getMainHandItem().shrink(1);
				cannon.FIRE(player);
			} else
				cannon.FIREAnimation();
		}
		return super.use(p_60503_, level, blockPos, player, p_60507_, p_60508_);
	}
}
