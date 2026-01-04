/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ManualItem extends Item {
	public ManualItem(Item.Properties properties) {
		super(properties.stacksTo(1));
	}

	@NotNull @Override
	public InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		if (level.isClientSide && hand == InteractionHand.MAIN_HAND)
			Minecraft.getInstance().setScreen(new ManualScreen());

		return super.use(level, player, hand);
	}

	protected final MutableComponent descriptionTooltip = Component.translatable("tooltip.tts.manual.info");

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);

		tooltip.add(Component.translatable("tooltip.tts.ctrl"));
		if (Screen.hasControlDown()) {
			tooltip.clear();
			tooltip.add(0, this.getName(stack));
			tooltip.add(descriptionTooltip);
		}
	}
}