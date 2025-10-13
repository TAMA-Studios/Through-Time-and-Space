/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.compat.jei;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import com.code.tama.tts.server.registries.RecipeRegistry;
import java.util.List;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class TTSJeiPlugin implements IModPlugin {
  private static final ResourceLocation UID = new ResourceLocation("tts", "jei_plugin");

  @Override
  public @NotNull ResourceLocation getPluginUid() {
    return UID;
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(
        new DataRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    List<DataRecipe> recipes = new java.util.ArrayList<>(List.of());
    recipes.addAll(RecipeRegistry.RECIPES);
    registration.addRecipes(DataRecipeCategory.TYPE, recipes);
  }
}
