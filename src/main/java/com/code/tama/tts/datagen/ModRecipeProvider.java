/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.registries.TTSItems;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ZEITON_SMELTABLES = List.of(
            TTSItems.ZEITON.get(),
            TTSBlocks.ZEITON_ORE.get(),
            TTSBlocks.DEEPSLATE_ZEITON_ORE.get(),
            TTSBlocks.NETHER_ZEITON_ORE.get(),
            TTSBlocks.END_STONE_ZEITON_ORE.get());

    protected static void oreBlasting(
            Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting");
    }

    protected static void oreCooking(
            Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pRecipeName) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(
                            Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(
                            pFinishedRecipeConsumer,
                            MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    protected static void oreSmelting(
            Consumer<FinishedRecipe> pFinishedRecipeConsumer,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTIme,
            String pGroup) {
        oreCooking(
                pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting");
    }

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, TTSItems.ZEITON.get(), 0.25f, 200, "zeiton");
        oreBlasting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, TTSItems.ZEITON.get(), 0.25f, 100, "zeiton");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TTSBlocks.ZEITON_BLOCK.get())
                .requires(TTSItems.ZEITON.get(), 9)
                .unlockedBy(getHasName(TTSItems.ZEITON.get()), has(TTSItems.ZEITON.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TTSItems.ZEITON.get(), 9)
                .requires(TTSBlocks.ZEITON_BLOCK.get())
                .unlockedBy(getHasName(TTSBlocks.ZEITON_BLOCK.get()), has(TTSBlocks.ZEITON_BLOCK.get()))
                .save(pWriter);
    }
}
