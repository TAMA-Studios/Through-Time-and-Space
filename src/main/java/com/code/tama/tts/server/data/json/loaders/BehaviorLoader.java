/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import com.code.tama.tts.server.data.json.AbstractDPLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class BehaviorLoader extends AbstractDPLoader<BehaviorLoader.TARDIBehavior> {
	private static final Gson GSON = new Gson();

	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/behavior");
	}

	@Override
	public boolean isValidJson(JsonObject json) {
		try {
			GSON.fromJson(json, TARDIBehavior.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String dataPath() {
		return "tts/tardis/behavior/";
	}

	@Override
	public TARDIBehavior getHolder(JsonObject json) {
		return GSON.fromJson(json, TARDIBehavior.class);
	}

	public record TARDIBehavior(float weight, String onLand, String onTakeoff, String onPlayerEnter,
			String onPlayerExit) {
	}
}
