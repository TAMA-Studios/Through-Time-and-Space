/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlayerUtils {
	public static void addExperienceLevels(Player player, int levels) {
		if (player != null) {
			player.experienceLevel += levels;
		}
	}

	public static boolean consumeItem(Player player, Item item, int amount) {
		if (!hasItemAmount(player, item, amount))
			return false;
		int remaining = amount;
		for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
			ItemStack stack = player.getInventory().getItem(i);
			if (stack.getItem() == item) {
				int toRemove = Math.min(stack.getCount(), remaining);
				stack.shrink(toRemove);
				remaining -= toRemove;
				if (remaining <= 0)
					break;
			}
		}
		return true;
	}

	public static boolean hasItem(Player player, Item item) {
		if (player == null)
			return false;
		for (ItemStack stack : player.getInventory().items) {
			if (stack.getItem() == item)
				return true;
		}
		return player.getOffhandItem().getItem() == item;
	}

	public static boolean hasItemAmount(Player player, Item item, int minCount) {
		if (player == null)
			return false;
		int count = 0;
		for (ItemStack stack : player.getInventory().items) {
			if (stack.getItem() == item)
				count += stack.getCount();
		}
		if (player.getOffhandItem().getItem() == item)
			count += player.getOffhandItem().getCount();
		return count >= minCount;
	}

	public static boolean isSneakingWithItem(Player player, ItemStack item) {
		return player.isShiftKeyDown() && ItemStack.isSameItem(player.getMainHandItem(), item);
	}

	public static void sendMessage(Player player, String message) {
		if (player != null && !player.level().isClientSide) {
			player.sendSystemMessage(Component.literal(message));
		}
	}
}
