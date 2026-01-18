/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class MoodDPLoader extends AbstractDPLoaderGSON<MoodDPLoader.TARDISMood> {
	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/mood");
	}

	@Override
	public Class<TARDISMood> GetClass() {
		return TARDISMood.class;
	}

	@Override
	public String dataPath() {
		return "tts/tardis/mood";
	}

	public record TARDISMood(float base_weight) {
	}
}
