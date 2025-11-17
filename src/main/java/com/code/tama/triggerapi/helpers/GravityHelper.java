/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import com.code.tama.triggerapi.data.holders.DataDimGravity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCap;

public class GravityHelper {
	public static List<DataDimGravity> DIMENSIONS = new ArrayList<>();
	public static Map<ResourceLocation, Float> MAP = new HashMap<>();

	/**
	 * Returns the gravity strength for a given level. Default Minecraft gravity =
	 * 0.08F TODO: Datapack gravity values, take the dimension RL and a float mavity
	 */
	public static float getGravity(Level level) {
		if (GetTARDISCap(level) != null) {
			return GetTARDISCap(level).GetData().getGravityLevel();
		}

		return MAP.getOrDefault(level.dimension().location(), 0.08F);
	}

	public static float getGravity(ResourceKey<Level> level) {
		return MAP.getOrDefault(level.location(), 0.08F);
	}

	public static void setMap(List<DataDimGravity> list) {
		if (!DIMENSIONS.isEmpty())
			DIMENSIONS.clear();
		DIMENSIONS = list;
		MAP.clear();
		DIMENSIONS.forEach(dim -> MAP.put(dim.dimension, dim.mavity));
	}
}
