/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.data.holders.DataDimGravity;

public class GravityHelper {
	public static List<DataDimGravity> DIMENSIONS = List.of();
	public static Map<ResourceLocation, Float> MAP = new HashMap<>();

	/**
	 * Returns the gravity strength for a given level. Default Minecraft gravity =
	 * 0.08F TODO: Datapack gravity values, take the dimension RL and a float mavity
	 */
	public static float getGravity(Level level) {
		// if (level.dimension().location().getPath().contains("moon")) { // moon
		// return -0.02F;
		// }
		// if (level.dimension().location().getPath().contains("heavyworld")) {
		// return 0.15F;
		// }

		// vanilla gravity is supposedly 0.08 idfk tho

		return MAP.getOrDefault(level.dimension().location(), 0.0F);
	}

	public static void setMap(List<DataDimGravity> list) {
		if (!DIMENSIONS.isEmpty())
			DIMENSIONS.clear();
		DIMENSIONS = list;
		MAP.clear();
		DIMENSIONS.forEach(dim -> MAP.put(dim.dimension, dim.mavity));
	}
}
