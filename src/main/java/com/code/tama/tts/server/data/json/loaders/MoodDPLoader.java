/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import com.code.tama.tts.server.data.json.AbstractDPLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class MoodDPLoader extends AbstractDPLoader<MoodDPLoader.TARDISMood> {
	private static final Gson GSON = new Gson();

	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/mood");
	}

	@Override
	public boolean isValidJson(JsonObject json) {
		try {
			GSON.fromJson(json, TARDISMood.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String dataPath() {
		return "tts/tardis/mood/";
	}

	@Override
	public TARDISMood getHolder(JsonObject json) {
		return GSON.fromJson(json, TARDISMood.class);
	}

	public record TARDISMood(float base_weight) {
	}
}
