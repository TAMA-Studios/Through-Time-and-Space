/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.items;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

/**
 * Abstract base for items that use an Energy system.
 */
public abstract class EnergyItem extends Item {

	public EnergyItem(Properties properties, int maxEnergy) {
		super(properties.durability(maxEnergy).setNoRepair());
	}

	public int getMaxEnergy(ItemStack stack) {
		return stack.getMaxDamage();
	}

	public int getEnergy(ItemStack stack) {
		return stack.getMaxDamage() - stack.getDamageValue();
	}

	public void setEnergy(ItemStack stack, int amount) {
		int clampedEnergy = Math.max(0, Math.min(amount, getMaxEnergy(stack)));
		stack.setDamageValue(stack.getMaxDamage() - clampedEnergy);
	}

	public boolean consumeEnergy(ItemStack stack, int amount) {
		int current = getEnergy(stack);
		if (current >= amount) {
			setEnergy(stack, current - amount);
			return true;
		}
		return false;
	}

	public boolean hasEnergy(ItemStack stack) {
		return getEnergy(stack) > 0;
	}

	@Override
	public boolean isEnchantable(@NotNull ItemStack stack) {
		return false;
		// Prevents Mending from being applied.
		// For obvious reasons.
		// Don't want the core mechanics easily bypassed by an anvil.
		// Jeryn if you're reading this you know exactly what I'm referring to.
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip,
			@NotNull TooltipFlag flag) {
		tooltip.add(Component.literal("Energy: " + getEnergy(stack) + " / " + getMaxEnergy(stack))
				.withStyle(ChatFormatting.AQUA));
		super.appendHoverText(stack, level, tooltip, flag);
	}
}