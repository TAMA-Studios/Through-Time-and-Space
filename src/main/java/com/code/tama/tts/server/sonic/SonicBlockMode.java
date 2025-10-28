/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.sonic;

import com.code.tama.tts.server.misc.progressable.IWeldable;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.code.tama.triggerapi.helpers.world.RayTraceUtils;

public class SonicBlockMode extends SonicMode {
	public Item getIcon() {
		return Blocks.GRASS_BLOCK.asItem();
	}

	public String getName() {
		return "block_mode";
	}

	public void onUse(UseOnContext context) {
		Player player = context.getPlayer();
		BlockHitResult hitResult = RayTraceUtils.getLookingAtBlock(25);
		if (hitResult == null)
			return;
		BlockPos usedPos = hitResult.getBlockPos(); // context.getClickedPos();
		assert player != null;
		BlockState state = player.level().getBlockState(usedPos);
		Level level = player.level();

		if (state.hasBlockEntity() && level.getBlockEntity(usedPos) instanceof IWeldable weldable) {

			weldable.setWeld(weldable.getWeld() + 1);
		}

		// if (state.getBlock().equals(TTSBlocks.EXTERIOR_BLOCK.get())) {
		// if (level.getBlockEntity(usedPos) instanceof ExteriorTile exteriorTile) {
		// if (exteriorTile.GetInterior() != null)
		// ServerLifecycleHooks.getCurrentServer().getLevel(exteriorTile.GetInterior())
		// .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::Dematerialize);
		// }
		// }

		if (state.getBlock() instanceof SandBlock) {
			level.setBlockAndUpdate(usedPos, Blocks.GLASS.defaultBlockState());
			return;
		}

		if (state.getBlock() instanceof GlassBlock) {
			level.removeBlock(usedPos, false);
			level.playSound(player, usedPos, SoundEvents.GLASS_BREAK, SoundSource.BLOCKS, 1.0F,
					level.getRandom().nextFloat() * 0.1F + 0.9F);
			return;
		}
		//
		if (state.getBlock().equals(Blocks.BRICKS)) {
			level.removeBlock(usedPos, false);
			ItemEntity item = EntityType.ITEM.create(level);
			assert item != null;
			item.setItem(Items.BRICK.getDefaultInstance());
			level.addFreshEntity(item);
			item.setPos(usedPos.getCenter());
			return;
		}

		if (state.getBlock().equals(Blocks.BRICK_SLAB)) {
			ItemEntity item = EntityType.ITEM.create(level);
			assert item != null;
			item.setItem(Items.BRICK.getDefaultInstance());
			level.addFreshEntity(item);
			item.setPos(usedPos.getCenter());
			return;
		}

		if (state.getBlock().equals(Blocks.BRICK_WALL)) {
			ItemEntity item = EntityType.ITEM.create(level);
			assert item != null;
			item.setItem(Items.BRICK.getDefaultInstance());
			level.addFreshEntity(item);
			item.setPos(usedPos.getCenter());
			return;
		}

		if (state.getBlock().equals(Blocks.BRICK_STAIRS)) {
			ItemEntity item = EntityType.ITEM.create(level);
			assert item != null;
			item.setItem(Items.BRICK.getDefaultInstance());
			level.addFreshEntity(item);
			item.setPos(usedPos.getCenter());
			return;
		}

		//
		// if (State.getBlock() instanceof PistonBaseBlock pistonBaseBlock) {
		// pistonBaseBlock.triggerEvent(State, Level, usedPos, 1, 2);
		// }
		//
		// return InteractionResult.PASS;

		assert context.getPlayer() != null;
		state.use(context.getLevel(), context.getPlayer(), context.getHand(), hitResult);
	}
}
