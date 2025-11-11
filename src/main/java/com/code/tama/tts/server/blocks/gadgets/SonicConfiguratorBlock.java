/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.gadgets;

import com.code.tama.tts.server.blocks.core.HorizontalRotatedBlock;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.SonicConfiguratorTileEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// The block Tile Entity class must implement EntityBlock
public class SonicConfiguratorBlock extends HorizontalRotatedBlock implements EntityBlock {
	public SonicConfiguratorBlock(Properties props) {
		super(props);
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_,
			CollisionContext p_60558_) {
		return Shapes.join(Block.box(0, 0, 3, 16, 2, 13), Block.box(3, 2, 6, 13, 6, 10), BooleanOp.OR);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		// Set this to your tile entity RegistryObject
		return TTSTileEntities.SONIC_CONFIGURATOR.get().create(blockPos, blockState);
	}

	@Override
	public @NotNull InteractionResult use(BlockState p_60503_, Level level, BlockPos blockPos, Player player,
			InteractionHand p_60507_, BlockHitResult p_60508_) {
		if (p_60507_.equals(InteractionHand.OFF_HAND))
			return super.use(p_60503_, level, blockPos, player, p_60507_, p_60508_);

		if (level.getBlockEntity(blockPos) != null
				&& level.getBlockEntity(blockPos) instanceof SonicConfiguratorTileEntity sonicConfiguratorTileEntity) {
			ItemStack stack = sonicConfiguratorTileEntity.getStack();

			if (stack.equals(ItemStack.EMPTY)) {
				if (player.getMainHandItem().getItem() instanceof SonicItem sonicItem) {
					if (sonicItem.IsExtended(player.getMainHandItem()))
						sonicItem.ToggleExtend(player.getMainHandItem());
					sonicConfiguratorTileEntity.setStack(player.getMainHandItem().copy());
					player.getMainHandItem().shrink(1);
				}
			} else if (stack.getItem() instanceof SonicItem sonicItem) {
				if (player.isCrouching()) {
					ItemEntity item = EntityType.ITEM.create(level);
					assert item != null;
					item.setPos(blockPos.above().getCenter());
					item.setItem(sonicConfiguratorTileEntity.getStack().copy());
					level.addFreshEntity(item);
					sonicConfiguratorTileEntity.setStack(ItemStack.EMPTY);
				} else {
					sonicItem.CycleVariant(stack);
				}
			}
		}
		return super.use(p_60503_, level, blockPos, player, p_60507_, p_60508_);
	}
}
