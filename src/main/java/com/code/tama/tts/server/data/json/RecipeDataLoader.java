/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.records.DataRecipe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;
import org.slf4j.Logger;

@Getter
public class RecipeDataLoader implements ResourceManagerReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final List<DataRecipe> dataRecipes = new ArrayList<>(); // List to store Data recipe objects

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        dataRecipes.clear(); // Reset the list of Data recipe objects

        // Log namespaces
        LOGGER.info("Loaded namespaces: {}", resourceManager.getNamespaces());

        // Iterate over all namespaces
        for (String namespace : resourceManager.getNamespaces()) {
            LOGGER.info("Searching resources in namespace: {}", namespace);

            // List all resources in this namespace inside 'data' folder, looking for .json
            // files
            Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                    "tts/recipes", fileName -> fileName.toString().endsWith(".json"));

            // Log the paths being searched for resources
            LOGGER.info("Searching for nbt under {}:structures/", namespace);

            // Log the resources found in this namespace
            LOGGER.info("Resources found in {}: {}", namespace, resources.keySet());

            if (resources.isEmpty()) {
                LOGGER.warn("No resources found for namespace: {}", namespace);
            }

            for (ResourceLocation rl : resources.keySet()) { // Iterate over the keys
                LOGGER.info("Found resource: {}", rl);

                Resource resource = resources.get(rl);

                try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                    JsonElement jsonElement = GsonHelper.parse(reader);

                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (isValidJson(jsonObject)) {
                            JsonObject valuesObject = jsonObject.getAsJsonObject("values");
                            ResourceLocation itemOne =
                                    new ResourceLocation(valuesObject.get("1").getAsString());
                            ResourceLocation itemTwo =
                                    new ResourceLocation(valuesObject.get("2").getAsString());
                            ResourceLocation itemThree =
                                    new ResourceLocation(valuesObject.get("3").getAsString());
                            ResourceLocation itemFour =
                                    new ResourceLocation(valuesObject.get("4").getAsString());
                            ResourceLocation itemFive =
                                    new ResourceLocation(valuesObject.get("5").getAsString());
                            ResourceLocation itemSix =
                                    new ResourceLocation(valuesObject.get("6").getAsString());
                            ResourceLocation nozzle = new ResourceLocation(
                                    valuesObject.get("nozzle").getAsString());
                            ResourceLocation result = new ResourceLocation(
                                    valuesObject.get("result").getAsString());
                            int time = valuesObject.get("time").getAsInt();

                            // Create Data recipe rooms and add it to the list
                            DataRecipe recipe = DataRecipe.builder()
                                    .item1(itemOne)
                                    .item2(itemTwo)
                                    .item3(itemThree)
                                    .item4(itemFour)
                                    .item5(itemFive)
                                    .item6(itemSix)
                                    .nozzle(nozzle)
                                    .result(result)
                                    .TimeInTicks(time)
                                    .build();
                            dataRecipes.add(recipe);

                            LOGGER.info("Loaded recipe Structure {}", recipe);
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

    private boolean isValidJson(JsonObject jsonObject) {
        if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
            JsonObject valuesObject = jsonObject.getAsJsonObject("values");

            // Validate name and texture fields
            if (valuesObject.has("name") && valuesObject.has("location")) {
                String name = valuesObject.get("name").getAsString();
                String location = valuesObject.get("location").getAsString();

                // Check for non-empty name
                if (name.isEmpty()) {
                    LOGGER.warn("Empty name field");
                    return false;
                }

                // Validate texture as ResourceLocation
                try {
                    ResourceLocation.parse(location); // Will throw exception if invalid
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("Invalid texture ResourceLocation: {}", location);
                    return false;
                }

                return true;
            }
        }
        return false;
    }
}
