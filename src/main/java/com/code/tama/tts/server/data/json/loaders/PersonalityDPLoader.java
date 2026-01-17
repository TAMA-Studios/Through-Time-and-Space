/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import com.code.tama.tts.server.data.json.AbstractDPLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class PersonalityDPLoader extends AbstractDPLoader<PersonalityDPLoader.TARDISPersonality> {
	private static final Gson GSON = new Gson();

	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/personality");
	}

	@Override
	public boolean isValidJson(JsonObject json) {
		try {
			GSON.fromJson(json, TARDISPersonality.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String dataPath() {
		return "tts/tardis/personality/";
	}

	@Override
	public TARDISPersonality getHolder(JsonObject json) {
		return GSON.fromJson(json, TARDISPersonality.class);
	}

	public record TARDISPersonality(float weight, float curiosity, float stability, float obedience, float playfulness,
			float protectiveness, float energy) {
	}
}
