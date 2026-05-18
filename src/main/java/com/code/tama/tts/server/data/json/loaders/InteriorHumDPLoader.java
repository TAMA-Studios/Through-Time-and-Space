/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class InteriorHumDPLoader extends AbstractDPLoaderGSON<InteriorHumDPLoader.InteriorHum> {
	public static ResourceLocation ID = UniversalCommon.modRL("tardis/interior_hum");

	@Override
	public ResourceLocation id() {
		return ID;
	}

	@Override
	public Class<InteriorHum> GetClass() {
		return InteriorHum.class;
	}

	@Override
	public String dataPath() {
		return "tts/tardis/interior_hum";
	}

	public record InteriorHum(ResourceLocation hum, int duration) {
	}
}
