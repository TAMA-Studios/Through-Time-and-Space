/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json;

import static com.code.tama.tts.TTSMod.LOGGER;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

public abstract class AbstractDPLoader<T> implements ResourceManagerReloadListener {
	public AbstractDPList<T> list = new AbstractDPList<T>();
	private final List<T> tempList = new ArrayList<>(); // List to store Data objects temporarily

	public abstract ResourceLocation id();
	public abstract boolean isValidJson(JsonObject jsonObject);
	public abstract String dataPath();

	@Override
	public void onResourceManagerReload(ResourceManager resourceManager) {
		tempList.clear();

		// Iterate over all namespaces
		for (String namespace : resourceManager.getNamespaces()) {
			Map<ResourceLocation, Resource> resources = resourceManager.listResources(dataPath(),
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
							T t = getHolder(valuesObject);
							if (!tempList.contains(t))
								tempList.add(t);
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
		list.setList(tempList);
	}

	public abstract T getHolder(JsonObject json);

	public static class AbstractDPList<T> {
		@Getter
		private List<T> list;

		public void setList(List<T> list) {
			list = removeDuplicates(list);
		}

		public List<T> removeDuplicates(List<T> list) {
			Set<String> seen = new HashSet<>();
			return list.stream().filter(r -> seen.add(r.toString())).collect(Collectors.toList());
		}
	}
}