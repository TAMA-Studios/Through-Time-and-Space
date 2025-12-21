/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.loaders;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DataFlightEvent;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.CrashFailureAction;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.FlightEventFailureAction;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.TakeArtronAction;
import com.code.tama.tts.server.data.json.lists.DataFlightEventList;
import com.google.gson.JsonArray;
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

import com.code.tama.triggerapi.universal.UniversalCommon;

@Getter
public class DataFlightEventLoader implements ResourceManagerReloadListener {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final List<DataFlightEvent> dataFlightEvent = new ArrayList<>(); // List to store objects

	private boolean isValidJson(JsonObject jsonObject) {
		if (jsonObject.has("values")) {
			JsonObject valuesObject = jsonObject.getAsJsonObject("values");

			// Validate grav and structure fields
			if (valuesObject.has("controls") && valuesObject.has("fail_action")) {
				JsonArray controls = valuesObject.get("controls").getAsJsonArray();
				String time = valuesObject.get("time").getAsString();

				// Check for non-empty controls
				if (controls.isEmpty()) {
					LOGGER.warn("Empty controls field");
					return false;
				}

				try {
					controls.asList().forEach(e -> {
						new ResourceLocation(e.getAsString());
					});
				} catch (Exception exception) {
					exception.printStackTrace();
				}

				return true;
			}
		}
		return false;
	}

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		dataFlightEvent.clear();

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {
			Map<ResourceLocation, Resource> resources = resourceManager.listResources("tts/flight_events",
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
							JsonArray controls = valuesObject.get("controls").getAsJsonArray();
							int time = valuesObject.get("time").getAsInt();
							String name = valuesObject.get("name").getAsString();

							FlightEventFailureAction action;

							JsonObject jsonAction = valuesObject.get("fail_action").getAsJsonObject();

							switch (jsonAction.get("name").getAsString()) {
								case "artron" : {
									action = new TakeArtronAction(jsonAction.get("fuel_to_take").getAsInt());
									break;
								}
								default : {
									action = new CrashFailureAction();
									break;
								}
							}

							List<ResourceLocation> locList = new ArrayList<>();

							controls.asList().forEach(obj -> locList.add(UniversalCommon.parse(obj.getAsString())));

							DataFlightEvent FlightEvent = new DataFlightEvent(locList, name, time, action);

							if (!dataFlightEvent.contains(FlightEvent))
								dataFlightEvent.add(FlightEvent);
						} else {
							LOGGER.warn("Invalid JSON structure in {}", rl);
						}
					}
				} catch (IOException e) {
					LOGGER.error("Error reading or parsing JSON file: {}", rl, e);
				}
			}
		}

		DataFlightEventList.setList(dataFlightEvent);
	}
}
