/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import com.code.tama.tts.server.registries.misc.RecipeRegistry;
import java.util.List;
import lombok.Getter;

public class DataRecipeList {
    @Getter
    private static List<DataRecipe> RecipeList;

    public static void setList(List<DataRecipe> list) {
        // Remove duplicates from the list
        for (DataRecipe recipe : list) {
            list.removeIf(r -> r != recipe && r.toString().equals(recipe.toString()));
        }
        RecipeList = list;
        RecipeRegistry.RECIPES.clear();
        RecipeRegistry.RECIPES.addAll(list);
    }
}
