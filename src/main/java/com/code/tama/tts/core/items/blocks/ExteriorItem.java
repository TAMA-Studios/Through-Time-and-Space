/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.items.blocks;

import java.util.function.Consumer;

import com.code.tama.tts.client.renderers.items.ExteriorItemRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class ExteriorItem extends BlockItem {
	public ExteriorItem(Block block, Item.Properties properties) {
		super(block, properties);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new ExteriorItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
						Minecraft.getInstance().getEntityModels());
			}
		});
	}


	@Override
	protected boolean updateCustomBlockEntityTag(
			BlockPos pos,
			Level level,
			Player player,
			ItemStack stack,
			BlockState state) {

		CompoundTag tag = stack.getTag();
		BlockEntity blockEntity = level.getBlockEntity(pos);

		if (tag != null && tag.contains("BlockEntityTag")) {
			blockEntity.load(tag.getCompound("BlockEntityTag"));
			return true;
		}

		return false;
	}

	@Override
	public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
		return armorType == EquipmentSlot.HEAD;
	}
}
