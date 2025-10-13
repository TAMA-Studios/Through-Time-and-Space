/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.compat.jei;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DataRecipeCategory implements IRecipeCategory<DataRecipe> {
  public static final ResourceLocation UID = new ResourceLocation("tts", "data_recipe");
  public static final RecipeType<DataRecipe> TYPE = new RecipeType<>(UID, DataRecipe.class);

  private final IDrawable background;
  private final IDrawable icon;

  public DataRecipeCategory(IGuiHelper guiHelper) {
    // You can use a custom texture for the background if you want
    this.background = guiHelper.createBlankDrawable(150, 80);
    this.icon =
        guiHelper.createDrawableIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(
                net.minecraft.world.item.Items
                    .BOOK // replace with your recipe "book"/representative item
                ));
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
  public @NotNull IDrawable getBackground() {
    return background;
  }

  @Override
  public @NotNull IDrawable getIcon() {
    return icon;
  }

  @Override
  public void setRecipe(
      IRecipeLayoutBuilder builder, DataRecipe recipe, IFocusGroup recipeSlotsView) {
    // 6 items (inputs)
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 10, 10)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item1)));
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 30, 10)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item2)));
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 50, 10)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item3)));
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 10, 30)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item4)));
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 30, 30)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item5)));
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 50, 30)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.item6)));

    // nozzle
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.INPUT, 80, 20)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.nozzle)));

    // output
    builder
        .addSlot(mezz.jei.api.recipe.RecipeIngredientRole.OUTPUT, 120, 20)
        .addIngredient(
            VanillaTypes.ITEM_STACK,
            new ItemStack(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(recipe.result)));
  }
}
