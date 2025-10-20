/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.server.misc.containers.ARSStructureContainer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import lombok.Getter;
import org.slf4j.Logger;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

@Getter
public class ARSDataLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final List<ARSStructureContainer> dataRoom = new ArrayList<>(); // List to store Data ars objects

	private boolean isValidJson(JsonObject jsonObject) {
		if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
			JsonObject valuesObject = jsonObject.getAsJsonObject("values");

			// Validate name and structure fields
			if (valuesObject.has("name") && valuesObject.has("location")) {
				String name = valuesObject.get("name").getAsString();
				String location = valuesObject.get("location").getAsString();

				// Check for non-empty name
				if (name.isEmpty()) {
					LOGGER.warn("Empty name field");
					return false;
				}

				// Validate structure as ResourceLocation
				try {
					ResourceLocation.parse(location); // Will throw exception if invalid
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid structure ResourceLocation: {}", location);
					return false;
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		dataRoom.clear(); // Reset the list of Data ars objects

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {
			// LOGGER.info("Searching resources in namespace: {}", namespace);

			// List all resources in this namespace inside 'data' folder, looking for .json
			// files
			Map<ResourceLocation, Resource> resources = resourceManager.listResources("tts/ars",
					fileName -> fileName.toString().endsWith(".json"));

			// Log the paths being searched for resources
			// LOGGER.info("Searching for nbt under {}:structures/", namespace);

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
							String name = valuesObject.get("name").getAsString();
							String location = valuesObject.get("location").getAsString();
							ResourceLocation structureLocation = new ResourceLocation(location);
							int heightOffs;
							if (!valuesObject.asMap().containsKey("yOffs"))
								heightOffs = 0;
							else
								heightOffs = valuesObject.get("yOffs").getAsInt();
							// Create Data ars rooms and add it to the list
							ARSStructureContainer Structure = new ARSStructureContainer(structureLocation,
									Component.translatable(name), heightOffs);
							if (!dataRoom.contains(Structure))
								dataRoom.add(Structure);
						} else {
							LOGGER.warn("Invalid JSON structure in {}", rl);
						}
					}
				} catch (IOException e) {
					LOGGER.error("Error reading or parsing JSON file: {}", rl, e);
				}
			}
		}

		// Store the list of Data ars room objects in the Data ars Array
		DataARSList.setList(dataRoom);
	}
}
