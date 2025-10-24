/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.dataHolders.DataRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class RecipeDataLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final List<DataRecipe> dataRecipes = new ArrayList<>(); // List to store Data recipe objects

	@SuppressWarnings("deprecation")
	private boolean isValidJson(JsonObject jsonObject) {
		if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
			JsonObject valuesObject = jsonObject.getAsJsonObject("values");

			// Validate name and texture fields
			if (valuesObject.has("nozzle") && valuesObject.has("result") && valuesObject.has("1")
					&& valuesObject.has("time")) {
				String nozzle = valuesObject.get("nozzle").getAsString();
				String result = valuesObject.get("result").getAsString();

				// Validate nozzle as ResourceLocation
				try {
					BuiltInRegistries.ITEM.get(new ResourceLocation(nozzle)); // Will throw exception if invalid
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid nozzle ResourceLocation: {}", result);
					return false;
				}

				// Validate result as ResourceLocation
				try {
					BuiltInRegistries.ITEM.get(new ResourceLocation(result)); // Will throw exception if invalid
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid result ResourceLocation: {}", result);
					return false;
				}

				try {
					for (int i = 1; i <= 6; i++) {
						String itemKey = String.valueOf(i);
						if (valuesObject.has(itemKey)) {
							String itemValue = valuesObject.get(itemKey).getAsString();
							BuiltInRegistries.ITEM.get(new ResourceLocation(itemValue));
						}
					}
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid item ResourceLocation: {}", result);
					return false;
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		dataRecipes.clear(); // Reset the list of Data recipe objects

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {

			// List all resources in this namespace inside 'data' folder, looking for .json
			// files
			Map<ResourceLocation, Resource> resources = resourceManager.listResources("tts/recipes",
					fileName -> fileName.toString().endsWith(".json"));

			// Log the paths being searched for resources
			// LOGGER.info("Searching for nbt under {}:tts/recipes/", namespace);

			// Log the resources found in this namespace
			// LOGGER.info("Resources found in {}: {}", namespace, resources.keySet());

			if (resources.isEmpty()) {
				LOGGER.warn("No resources found for namespace: {}", namespace);
			}

			for (ResourceLocation rl : resources.keySet()) { // Iterate over the keys
				// LOGGER.info("Found resource: {}", rl);

				Resource resource = resources.get(rl);

				try (InputStreamReader reader = new InputStreamReader(resource.open())) {
					JsonElement jsonElement = GsonHelper.parse(reader);

					if (jsonElement.isJsonObject()) {
						JsonObject jsonObject = jsonElement.getAsJsonObject();
						if (isValidJson(jsonObject)) {
							JsonObject valuesObject = jsonObject.getAsJsonObject("values");
							ResourceLocation itemOne = getResourceOrAir(valuesObject, "1");
							ResourceLocation itemTwo = getResourceOrAir(valuesObject, "2");
							ResourceLocation itemThree = getResourceOrAir(valuesObject, "3");
							ResourceLocation itemFour = getResourceOrAir(valuesObject, "4");
							ResourceLocation itemFive = getResourceOrAir(valuesObject, "5");
							ResourceLocation itemSix = getResourceOrAir(valuesObject, "6");

							ResourceLocation nozzle = new ResourceLocation(valuesObject.get("nozzle").getAsString());
							ResourceLocation result = new ResourceLocation(valuesObject.get("result").getAsString());
							int time = valuesObject.get("time").getAsInt();

							// Create Data recipe rooms and add it to the list
							DataRecipe recipe = DataRecipe.builder().item1(itemOne).item2(itemTwo).item3(itemThree)
									.item4(itemFour).item5(itemFive).item6(itemSix).nozzle(nozzle).result(result)
									.TimeInTicks(time).build();

							if (!dataRecipes.contains(recipe))
								dataRecipes.add(recipe);
							// LOGGER.info("Loaded recipe Structure {}", recipe);
						} else {
							LOGGER.warn("Invalid JSON structure in {}", rl);
						}
					}
				} catch (IOException e) {
					LOGGER.error("Error reading or parsing JSON file: {}", rl, e);
				}
			}
		}

		// Store the list of Data recipe room objects in the Data recipe Array
		DataRecipeList.setList(dataRecipes);
	}

	private ResourceLocation getResourceOrAir(JsonObject obj, String key) {
		return new ResourceLocation(
				obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : "air"
		);
	}
}
