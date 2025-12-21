/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.gadgets;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.CompressedMultiblockTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class CompressedMultiblockBlock extends Block implements EntityBlock {
	public CompressedMultiblockBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return TTSTileEntities.COMPRESSED_MULTIBLOCK_TILE.get().create(pos, state);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos,
			@NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
		if (interactionHand.equals(InteractionHand.OFF_HAND) || level.isClientSide)
			return super.use(state, level, pos, player, interactionHand, blockHitResult);
		if (level.getBlockEntity(pos) instanceof CompressedMultiblockTile compressedMultiblock) {
			// Add recipe handling shit here.
			// Here's an example for later on, so I don't forget

			if (!compressedMultiblock.stateMap.isEmpty())
				return InteractionResult.FAIL;

			for (BlockPos blockPos : BlockPos.betweenClosed(-1, -1, -1, 1, 1, 1)) {
				BlockPos pos1 = BlockUtils.getRelativeBlockPos(blockPos, pos);
				BlockState state1 = level.getBlockState(pos1);
				compressedMultiblock.stateMap.put(blockPos.immutable(), state1);
				if (!state1.equals(state))
					level.removeBlock(pos1, false);
			}

			level.sendBlockUpdated(pos, state, state, Block.UPDATE_CLIENTS);

			ItemStack item = TTSBlocks.COMPRESSED_MULTIBLOCK.asItem().getDefaultInstance().copy();
			compressedMultiblock.saveToItem(item);
			ItemEntity entity = EntityType.ITEM.create(level);

			assert entity != null;
			entity.setPos(pos.getCenter());
			entity.setItem(item);
			// level.removeBlock(pos, false);
			this.destroy(level, pos, state);
			level.addFreshEntity(entity);
		}

		return super.use(state, level, pos, player, interactionHand, blockHitResult);
	}
}
