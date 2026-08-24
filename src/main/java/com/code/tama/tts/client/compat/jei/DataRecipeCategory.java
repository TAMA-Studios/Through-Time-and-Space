/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.compat.jei;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.jetbrains.annotations.NotNull;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class DataRecipeCategory implements IRecipeCategory<DataRecipe> {
	public static final ResourceLocation UID = UniversalCommon.modRL("data_recipe");
	public static final RecipeType<DataRecipe> TYPE = new RecipeType<>(UID, DataRecipe.class);
	public static final ResourceLocation GUI = UniversalCommon.modRL("textures/gui/fabricator.png");

	private final IDrawable background;
	private final IDrawable icon;

	public DataRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(GUI, 256, 256, 151, 151);// .createBlankDrawable(150, 80);
		this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(TTSBlocks.TEMPORAL_FABRICATOR));
	}

	@Override
	public @NotNull IDrawable getBackground() {
		return background;
	}

	@Override
	public @NotNull IDrawable getIcon() {
		return icon;
	}

	@Override
	public @NotNull RecipeType<DataRecipe> getRecipeType() {
		return TYPE;
	}

	@Override
	public @NotNull Component getTitle() {
		return Component.literal("Data Recipe");
	}

	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, DataRecipe recipe, IFocusGroup recipeSlotsView) {
		// 6 items (inputs)
		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 17, 20).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item1)));

		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 117, 20).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item2)));

		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 17, 115).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item3)));

		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 117, 115).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item4)));

		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 37, 105).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item5)));

		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 97, 105).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item6)));

		// nozzle
		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 67, 30).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.nozzle)));

		// output
		builder.addSlot(mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT, 67, 70).addIngredient(VanillaTypes.ITEM_STACK,
				new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.result)));
	}
}
