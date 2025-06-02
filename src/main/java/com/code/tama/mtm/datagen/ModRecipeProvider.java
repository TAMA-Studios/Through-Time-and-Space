package com.code.tama.mtm.datagen;

import com.code.tama.mtm.server.registries.MTMBlocks;
import com.code.tama.mtm.server.registries.MTMItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.code.tama.mtm.MTMMod.MODID;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ZEITON_SMELTABLES = List.of(MTMItems.ZEITON.get(),
            MTMBlocks.ZEITON_ORE.get(), MTMBlocks.DEEPSLATE_ZEITON_ORE.get(), MTMBlocks.NETHER_ZEITON_ORE.get(),
            MTMBlocks.END_STONE_ZEITON_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, MTMItems.ZEITON.get(), 0.25f, 200, "zeiton");
        oreBlasting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, MTMItems.ZEITON.get(), 0.25f, 100, "zeiton");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MTMBlocks.ZEITON_BLOCK.get())
                .requires(MTMItems.ZEITON.get(), 9)
                .unlockedBy(getHasName(MTMItems.ZEITON.get()), has(MTMItems.ZEITON.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MTMItems.ZEITON.get(), 9)
                .requires(MTMBlocks.ZEITON_BLOCK.get())
                .unlockedBy(getHasName(MTMBlocks.ZEITON_BLOCK.get()), has(MTMBlocks.ZEITON_BLOCK.get()))
                .save(pWriter);

    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}