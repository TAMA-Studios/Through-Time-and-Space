/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCap;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.server.data.json.dataHolders.DataDimGravity;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class GravityHelper {
	public static List<DataDimGravity> DIMENSIONS = new ArrayList<>();
	public static Map<ResourceLocation, Float> MAP = new HashMap<>();

	/**
	 * Returns the gravity strength for a given level. Default Minecraft gravity:
	 * 0.08F
	 */
	public static float getGravity(Level level) {
		if (GetTARDISCapSupplier(level).isPresent()) {
			return GetTARDISCap(level).GetEnvironmentalData().getGravityLevel();
		}

		return MAP.getOrDefault(level.dimension().location(), 0.08F);
	}

	public static float getGravity(@NotNull ResourceKey<Level> level) {
		return MAP.getOrDefault(level.location(), 0.08F);
	}

	public static void setMap(List<DataDimGravity> list) {
		DIMENSIONS.clear();
		DIMENSIONS = list;
		MAP.clear();
		DIMENSIONS.forEach(dim -> MAP.put(dim.dimension, dim.mavity));
	}
}
