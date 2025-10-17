/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemHelper {
	/** Counts how many of a specific item a player has in their inventory. */
	public static int countItem(Player player, ItemStack itemToCount) {
		int count = 0;
		for (ItemStack stack : player.getInventory().items) {
			if (ItemStack.isSameItem(stack, itemToCount)) {
				count += stack.getCount();
			}
		}
		return count;
	}

	/** Gives an item to a player or drops it if inventory is full. */
	public static void giveItemToPlayer(Player player, ItemStack stack) {
		if (!player.addItem(stack)) {
			player.drop(stack, false);
		}
	}

	/** Spawns an item entity in the world at the specified coordinates. */
	public static void spawnItem(Level world, ItemStack stack, double x, double y, double z) {
		if (!world.isClientSide && !stack.isEmpty()) {
			ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack.copy());
			itemEntity.setDefaultPickUpDelay(); // Sets a 10-tick pickup delay
			world.addFreshEntity(itemEntity);
		}
	}
}
