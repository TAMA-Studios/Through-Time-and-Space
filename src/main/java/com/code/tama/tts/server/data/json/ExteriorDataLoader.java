/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.records.DataExterior;
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
public class ExteriorDataLoader implements ResourceManagerReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final List<DataExterior> dataExteriorList = new ArrayList<>(); // List to store DataExterior objects

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        dataExteriorList.clear(); // Reset the list of DataExterior objects

        // Iterate over all namespaces
        for (String namespace : resourceManager.getNamespaces()) {
            LOGGER.info("Searching resources in namespace: {}", namespace);

            // List all resources in this namespace inside 'data' folder, looking for .json
            // files
            Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                    "tts/exteriors", fileName -> fileName.toString().endsWith(".json"));

            // Log the paths being searched for resources
            LOGGER.info("Searching for resources under: data/{}/exterior/", namespace);

            // Log the resources found in this namespace
            LOGGER.info("Resources found in {}: {}", namespace, resources.keySet());

            if (resources.isEmpty()) {
                LOGGER.warn("No resources found for namespace: {}", namespace);
            }

            for (ResourceLocation location : resources.keySet()) { // Iterate over the keys
                LOGGER.info("Found resource: {}", location);

                Resource resource = resources.get(location);

                try (InputStreamReader reader = new InputStreamReader(resource.open())) {
                    JsonElement jsonElement = GsonHelper.parse(reader);

                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (isValidJson(jsonObject)) {
                            JsonObject valuesObject = jsonObject.getAsJsonObject("values");
                            String name = valuesObject.get("name").getAsString();
                            String modelname = valuesObject.get("modelname").getAsString();
                            ResourceLocation modelLocation = new ResourceLocation(modelname);

                            // Create DataExterior and add it to the list
                            DataExterior dataExterior = new DataExterior(name, modelLocation);
                            dataExteriorList.add(dataExterior);

                            LOGGER.info("Loaded DataExterior from {}: {}", location, dataExterior);
                        } else {
                            LOGGER.warn("Invalid JSON structure in {}", location);
                        }
                    }
                } catch (IOException e) {
                    LOGGER.error("Error reading or parsing JSON file: {}", location, e);
                }
            }
        }

        // Store the list of DataExterior objects in the DataExteriorArray
        DataExteriorList.setExteriorList(dataExteriorList);
    }

    private boolean isValidJson(JsonObject jsonObject) {
        if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
            JsonObject valuesObject = jsonObject.getAsJsonObject("values");

            // Validate name and texture fields
            if (valuesObject.has("name") && valuesObject.has("modelname")) {
                String name = valuesObject.get("name").getAsString();
                String modelName = valuesObject.get("modelname").getAsString();

                // Check for non-empty name
                if (name.isEmpty()) {
                    LOGGER.warn("Empty name field");
                    return false;
                }

                // Validate texture as ResourceLocation
                try {
                    ResourceLocation.parse(modelName); // Will throw exception if invalid
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("Invalid texture ResourceLocation: {}", modelName);
                    return false;
                }

                return true;
            }
        }
        return false;
    }
}
