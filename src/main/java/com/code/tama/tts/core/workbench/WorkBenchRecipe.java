package com.code.tama.tts.core.workbench;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkBenchRecipe {
    Item Ingredients[] = new Item[4];
    Item Result;

    /**
     * Specifies 4 ingredients, if you want only say, 2 Ingredients, leave the rest as Items.AIR
     * @param FirstIngredient 1st Ingredient
     * @param SecondIngredient 2nd Ingredient
     * @param ThirdIngredient 3rd Ingredient
     * @param FourthIngredient 4th Ingredient
     */
    public WorkBenchRecipe(Item FirstIngredient, Item SecondIngredient, Item ThirdIngredient, Item FourthIngredient) {
        this.Ingredients[0] = FirstIngredient;
        this.Ingredients[1] = SecondIngredient;
        this.Ingredients[2] = ThirdIngredient;
        this.Ingredients[3] = FourthIngredient;
    }
    public WorkBenchRecipe AddReceivingItem(Item Received){
        this.Result = Received;
        return this;
    }

    public WorkBenchRecipe Get(){
        return this;
    }

    public List<Item> GetList(){
        return Arrays.asList(this.Ingredients);
    }

    public List<Ingredient> GetIngredientList(){
        Ingredient ingredient1 = Ingredient.of(this.Ingredients[0].asItem());
        Ingredient ingredient2 = Ingredient.of(this.Ingredients[1].asItem());
        Ingredient ingredient3 = Ingredient.of(this.Ingredients[2].asItem());
        Ingredient ingredient4 = Ingredient.of(this.Ingredients[3].asItem());
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);
        ingredients.add(ingredient3);
        ingredients.add(ingredient4);
        return ingredients;
//        return
    }

    public Item GetOutput(){
        return this.Result;
    }
}