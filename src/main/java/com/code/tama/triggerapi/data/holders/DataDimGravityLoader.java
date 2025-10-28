/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.data.holders;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import lombok.Getter;
import org.slf4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

@Getter
public class DataDimGravityLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final List<DataDimGravity> dataGrav = new ArrayList<>(); // List to store Data ars objects

	private boolean isValidJson(JsonObject jsonObject) {
		if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
			JsonObject valuesObject = jsonObject.getAsJsonObject("values");

			// Validate grav and structure fields
			if (valuesObject.has("gravity") && valuesObject.has("dimension")) {
				String grav = valuesObject.get("gravity").getAsString();
				String dimension = valuesObject.get("dimension").getAsString();

				// Check for non-empty grav
				if (grav.isEmpty()) {
					LOGGER.warn("Empty gravity field");
					return false;
				}

				// Validate structure as ResourceLocation
				try {
					new ResourceLocation(dimension); // Will throw exception if invalid
				} catch (IllegalArgumentException e) {
					LOGGER.warn("Invalid structure ResourceLocation: {}", dimension);
					return false;
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		dataGrav.clear(); // Reset the list of Data grav objects

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {
			Map<ResourceLocation, Resource> resources = resourceManager.listResources("tts/dim/gravity",
					fileName -> fileName.toString().endsWith(".json"));

			if (resources.isEmpty()) {
				LOGGER.warn("No resources found for namespace: {}", namespace);
			}

			for (ResourceLocation rl : resources.keySet()) {
				Resource resource = resources.get(rl);

				try (InputStreamReader reader = new InputStreamReader(resource.open())) {
					JsonElement jsonElement = GsonHelper.parse(reader);

					if (jsonElement.isJsonObject()) {
						JsonObject jsonObject = jsonElement.getAsJsonObject();
						if (isValidJson(jsonObject)) {
							JsonObject valuesObject = jsonObject.getAsJsonObject("values");
							float grav = valuesObject.get("gravity").getAsFloat();
							String dimension = valuesObject.get("dimension").getAsString();
							ResourceLocation dimLoc = new ResourceLocation(dimension);
							DataDimGravity Structure = new DataDimGravity(grav, dimLoc);
							if (!dataGrav.contains(Structure))
								dataGrav.add(Structure);
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
		DataDimGravityList.setList(dataGrav);
	}
}
