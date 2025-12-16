/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import com.code.tama.tts.server.data.json.dataHolders.DataDimOxygen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCap;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class OxygenHelper {
	public static List<DataDimOxygen> DIMENSIONS = new ArrayList<>();
	public static Map<ResourceLocation, Float> MAP = new HashMap<>();

	/**
	 * Returns the oxygen strength for a given level. Default Minecraft gravity: 0.08F
	 */
	public static float getO2(Level level) {
		if (GetTARDISCapSupplier(level).isPresent()) {
			return GetTARDISCap(level).GetEnvironmentalData().getOxygenLevel();
		}

		return MAP.getOrDefault(level.dimension().location(), 1.0F);
	}

	public static float getO2(@NotNull ResourceKey<Level> level) {
		return MAP.getOrDefault(level.location(), 0.08F);
	}

	public static void setMap(List<DataDimOxygen> list) {
		if (!DIMENSIONS.isEmpty())
			DIMENSIONS.clear();
		DIMENSIONS = list;
		MAP.clear();
		DIMENSIONS.forEach(dim -> MAP.put(dim.dimension, dim.oxygen));
	}
}
