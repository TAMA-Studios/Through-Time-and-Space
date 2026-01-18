/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class BehaviorLoader extends AbstractDPLoaderGSON<BehaviorLoader.TARDIBehavior> {
	public Class<TARDIBehavior> GetClass() {
		return TARDIBehavior.class;
	}

	public ResourceLocation id() {
		return UniversalCommon.modRL("tardis/behavior");
	}

	@Override
	public String dataPath() {
		return "tts/tardis/behavior";
	}

	public record TARDIBehavior(float weight, String onLand, String onTakeoff, String onPlayerEnter,
			String onPlayerExit) {
	}
}
