/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public abstract class AbstractDPLoaderGSON<T> extends AbstractDPLoader<T> {

	public abstract Class<T> GetClass();
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(ResourceLocation.class,
					(JsonDeserializer<ResourceLocation>) (json, type, ctx) -> UniversalCommon.parse(json.getAsString()))
			.create();

	@Override
	public boolean isValidJson(JsonObject json) {
		try {
			GSON.fromJson(json, GetClass());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public T getHolder(JsonObject json) {
		return GSON.fromJson(json, GetClass());
	}
}
