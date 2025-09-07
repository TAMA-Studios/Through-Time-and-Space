/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import com.code.tama.tts.server.registries.RecipeRegistry;
import java.util.List;
import lombok.Getter;

public class DataRecipeList {
    @Getter
    private static List<DataRecipe> RecipeList;

    public static void setList(List<DataRecipe> list) {
        RecipeList = list;
        RecipeRegistry.RECIPES.clear();
        RecipeRegistry.RECIPES.addAll(list);
    }
}
