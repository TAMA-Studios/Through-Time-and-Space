/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.misc;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.config.TTSConfig;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.GrammarNazi;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class MiscUtils {
	private static Map<ResourceLocation, String> DimNameMap = null;
	public static String getDimName(ResourceLocation dimLoc) {
		if (DimNameMap == null) {
			DimNameMap = new HashMap<>();
			TTSConfig.ServerConfig.DIMENSION_NAME_OVERRIDE.get().forEach((r) -> {
				String full[] = r.split(",");
				String RL = full[0];
				String Readable = full[1];

				DimNameMap.put(UniversalCommon.parse(RL), Readable);
			});
		}

		if (!DimNameMap.containsKey(dimLoc)) {
			DimNameMap.put(dimLoc, GrammarNazi.CleanString(dimLoc.getPath()) + "["
					+ GrammarNazi.CleanString(dimLoc.getNamespace()) + "]");
		}

		return DimNameMap.get(dimLoc);
	}
}
