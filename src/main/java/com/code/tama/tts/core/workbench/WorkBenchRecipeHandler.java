/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class WorkBenchRecipeHandler {
    public ArrayList<WorkBenchRecipe> RecipeList = new ArrayList<>();

    public void AddRecipe(
            Item FirstIngredient,
            Item SecondIngredient,
            Item ThirdIngredient,
            Item FourthIngredient,
            Item RecievedItem) {
        this.RecipeList.add(new WorkBenchRecipe(FirstIngredient, SecondIngredient, ThirdIngredient, FourthIngredient)
                .AddReceivingItem(RecievedItem));
    }

    /**
     * Check if the four ingredients combined form a valid recipe
     *
     * @return wether it is valid
     */
    public boolean IsValidRecipe(
            Item FirstIngredient, Item SecondIngredient, Item ThirdIngredient, Item FourthIngredient) {
        for (int i = 0; i < this.RecipeList.size(); i++) {
            //            if(Arrays.equals(this.RecipeList.get(i).Ingredients, new Item[]{FirstIngredient,
            // SecondIngredient, ThirdIngredient, FourthIngredient}));
            if (Arrays.asList(this.RecipeList.get(i).Ingredients).contains(FirstIngredient)
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(SecondIngredient)
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(ThirdIngredient)
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(FourthIngredient)) return true;
        }
        return false;
    }

    /**
     * Check if the four ingredients combined form a valid recipe
     *
     * @return wether it is valid
     */
    public boolean IsValidRecipeFromArrayList(ArrayList<Item> Ingredients) {
        while (Ingredients.size() < 4) {
            Ingredients.add(Items.AIR);
        }
        for (int i = 0; i < this.RecipeList.size(); i++) {
            //            if(this.RecipeList.get(i).Ingredients new Item[]{Ingredients.get(0), Ingredients.get(1),
            // Ingredients.get(2), Ingredients.get(3)}))
            if (Arrays.asList(this.RecipeList.get(i).Ingredients).contains(Ingredients.get(0))
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(Ingredients.get(1))
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(Ingredients.get(2))
                    && Arrays.asList(this.RecipeList.get(i).Ingredients).contains(Ingredients.get(3))) return true;
        }
        return false;

        //        return this.RecipeList.contains(new WorkBenchRecipe(Ingredients.get(0), Ingredients.get(1),
        // Ingredients.get(2), Ingredients.get(3)));
    }

    /**
     * Takes 4 items and if the recipe is valid returns the result of the recipe
     *
     * @return The result of the recipe or dirt if it doesn't exist
     */
    public Item GetRecipeResult(
            Item FirstIngredient, Item SecondIngredient, Item ThirdIngredient, Item FourthIngredient) {
        if (this.IsValidRecipe(FirstIngredient, SecondIngredient, ThirdIngredient, FourthIngredient)) {
            for (int i = 0; i < this.RecipeList.size(); i++) {
                if (Arrays.equals(
                        this.RecipeList.get(i).Ingredients,
                        new Item[] {FirstIngredient, SecondIngredient, ThirdIngredient, FourthIngredient})) {
                    return this.RecipeList.get(i).Result;
                }
            }
        }
        return Items.DIRT;
    }

    /**
     * Takes 4 items and if the recipe is valid returns the result of the recipe
     *
     * @return The result of the recipe or dirt if it doesn't exist
     */
    public Item GetRecipeResultFromArrayList(ArrayList<Item> Ingredient) {
        Item FirstIngredient = Ingredient.get(0),
                SecondIngredient = Ingredient.get(1),
                ThirdIngredient = Ingredient.get(2),
                FourthIngredient = Ingredient.get(3);
        if (this.IsValidRecipe(FirstIngredient, SecondIngredient, ThirdIngredient, FourthIngredient)) {
            for (int i = 0; i < this.RecipeList.size(); i++) {
                if (Arrays.equals(
                        this.RecipeList.get(i).Ingredients,
                        new Item[] {FirstIngredient, SecondIngredient, ThirdIngredient, FourthIngredient})) {
                    return this.RecipeList.get(i).Result;
                }
            }
        }
        return Items.DIRT;
    }
    //    public WorkBenchRecipe MatchRecipeFromItemArrayList(ArrayList<Item> List){
    //        return new (List.get(0), List.get(1), List.get(2), List.get(3));
    //    }

    /**
     * Check if this handler has all the recipes as provided handler and add any missing recipes
     * This is mainly so external mods can add more recipes
     * @param Handler Handler with recipes to add to this
     */
    public void Sync(WorkBenchRecipeHandler Handler) {
        for (int i = 0; i < Handler.RecipeList.size(); i++) {
            if (!this.RecipeList.contains(Handler.RecipeList.get(i))) this.RecipeList.add(Handler.RecipeList.get(i));
        }
    }
}
