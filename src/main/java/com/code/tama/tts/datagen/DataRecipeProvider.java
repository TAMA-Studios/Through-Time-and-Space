/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

import static com.code.tama.tts.TTSMod.MODID;

public class DataRecipeProvider extends RecipeProvider implements IConditionBuilder {

	public DataRecipeProvider(PackOutput pOutput) {
		super(pOutput);
	}

	protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
		oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
				pExperience, pCookingTime, pGroup, "_from_blasting");
	}

	protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
			RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup,
			String pRecipeName) {
		for (ItemLike itemlike : pIngredients) {
			SimpleCookingRecipeBuilder
					.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer)
					.group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer,
							MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
		}
	}

	protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients,
			RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
		oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
				pExperience, pCookingTIme, pGroup, "_from_smelting");
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
//		List<ItemLike> ZEITON_SMELTABLES = List.of(TTSItems.ZEITON, TTSBlocks.ZEITON_ORE,
//				TTSBlocks.DEEPSLATE_ZEITON_ORE, TTSBlocks.NETHER_ZEITON_ORE,
//				TTSBlocks.END_STONE_ZEITON_ORE);
//
//		oreSmelting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, TTSItems.ZEITON, 0.25f, 200, "zeiton");
//		oreBlasting(pWriter, ZEITON_SMELTABLES, RecipeCategory.MISC, TTSItems.ZEITON, 0.25f, 100, "zeiton");
//
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TTSBlocks.ZEITON_BLOCK)
//				.requires(TTSItems.ZEITON, 9)
//				.unlockedBy(getHasName(TTSItems.ZEITON), has(TTSItems.ZEITON)).save(pWriter);
//
//		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TTSItems.ZEITON, 9)
//				.requires(TTSBlocks.ZEITON_BLOCK)
//				.unlockedBy(getHasName(TTSBlocks.ZEITON_BLOCK), has(TTSBlocks.ZEITON_BLOCK)).save(pWriter);
	}
}
