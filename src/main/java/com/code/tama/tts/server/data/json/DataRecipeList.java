/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import com.code.tama.tts.server.registries.misc.RecipeRegistry;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataRecipeList {
	@Getter
	private static List<DataRecipe> RecipeList;

	public static void setList(List<DataRecipe> list) {
		RecipeList = removeDuplicates(list);
		RecipeRegistry.RECIPES.clear();
		RecipeRegistry.RECIPES.addAll(RecipeList);
	}

	public static List<DataRecipe> removeDuplicates(List<DataRecipe> list) {
		Set<String> seen = new HashSet<>();
		return list.stream()
				.filter(r -> seen.add(r.toString()))
				.collect(Collectors.toList());
	}
}
