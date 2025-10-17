/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.misc;

import java.util.ArrayList;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

@SuppressWarnings("unused")
public class RecipeRegistry {
	public static ArrayList<DataRecipe> RECIPES = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static Item GetRecipeResult(Item item1, Item item2, Item item3, Item item4, Item item5, Item item6,
			Item nozzle) {
		for (DataRecipe recipe : RECIPES) {
			if (recipe.item1.equals(BuiltInRegistries.ITEM.getKey(item1))
					&& recipe.item2.equals(BuiltInRegistries.ITEM.getKey(item2))
					&& recipe.item3.equals(BuiltInRegistries.ITEM.getKey(item3))
					&& recipe.item4.equals(BuiltInRegistries.ITEM.getKey(item4))
					&& recipe.item5.equals(BuiltInRegistries.ITEM.getKey(item5))
					&& recipe.item6.equals(BuiltInRegistries.ITEM.getKey(item6))
					&& recipe.nozzle.equals(BuiltInRegistries.ITEM.getKey(nozzle))) {
				// Valid recipe found
				return BuiltInRegistries.ITEM.get(recipe.result);
			}
		}
		// No valid recipe found
		return Items.AIR;
	}

	@SuppressWarnings("deprecation")
	public static boolean IsValidRecipe(Item item1, Item item2, Item item3, Item item4, Item item5, Item item6,
			Item nozzle) {
		for (DataRecipe recipe : RECIPES) {
			if (recipe.item1.equals(item1.builtInRegistryHolder().key().registry())
					&& recipe.item2.equals(item2.builtInRegistryHolder().key().registry())
					&& recipe.item3.equals(item3.builtInRegistryHolder().key().registry())
					&& recipe.item4.equals(item4.builtInRegistryHolder().key().registry())
					&& recipe.item5.equals(item5.builtInRegistryHolder().key().registry())
					&& recipe.item6.equals(item6.builtInRegistryHolder().key().registry())
					&& recipe.nozzle.equals(nozzle.builtInRegistryHolder().key().registry())) {
				// Valid recipe found
				return true;
			}
		}
		// No valid recipe found
		return false;
	}
}
