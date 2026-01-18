/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class PersonalityDPLoader extends AbstractDPLoaderGSON<PersonalityDPLoader.TARDISPersonality> {
	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/personality");
	}

	@Override
	public Class<TARDISPersonality> GetClass() {
		return TARDISPersonality.class;
	}

	@Override
	public String dataPath() {
		return "tts/tardis/personality";
	}

	public record TARDISPersonality(float weight, float curiosity, float stability, float obedience, float playfulness,
			float protectiveness, float energy) {
	}
}
