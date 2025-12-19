/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.loaders;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.server.data.json.dataHolders.DataDimOxygen;
import com.code.tama.tts.server.data.json.lists.DataDimOxygenList;
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
public class DataDimOxygenLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final List<DataDimOxygen> dataOxygen = new ArrayList<>(); // List to store objects

	private boolean isValidJson(JsonObject jsonObject) {
		if (jsonObject.has("values") && jsonObject.get("values").isJsonObject()) {
			JsonObject valuesObject = jsonObject.getAsJsonObject("values");

			// Validate grav and structure fields
			if (valuesObject.has("oxygen") && valuesObject.has("dimension")) {
				String grav = valuesObject.get("oxygen").getAsString();
				String dimension = valuesObject.get("dimension").getAsString();

				// Check for non-empty grav
				if (grav.isEmpty()) {
					LOGGER.warn("Empty oxygen field");
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
		dataOxygen.clear(); // Reset the list of Data grav objects

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {
			Map<ResourceLocation, Resource> resources = resourceManager.listResources("tts/dim/oxygen",
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
							float oxygen = valuesObject.get("gravity").getAsFloat();
							String dimension = valuesObject.get("dimension").getAsString();
							ResourceLocation dimLoc = new ResourceLocation(dimension);
							DataDimOxygen Oxygen = DataDimOxygen.builder().oxygen(oxygen).dimension(dimLoc).build();
							if (!dataOxygen.contains(Oxygen))
								dataOxygen.add(Oxygen);
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
		DataDimOxygenList.setList(dataOxygen);
	}
}
