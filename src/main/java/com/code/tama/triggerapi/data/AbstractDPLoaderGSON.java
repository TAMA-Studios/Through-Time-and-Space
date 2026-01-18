/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class AbstractDPLoaderGSON<T> extends AbstractDPLoader<T> {

	public abstract Class<T> GetClass();
	private static final Gson GSON = new Gson();

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
	public String dataPath() {
		return "tts/tardis/mood";
	}

	@Override
	public T getHolder(JsonObject json) {
		return GSON.fromJson(json, GetClass());
	}
}
