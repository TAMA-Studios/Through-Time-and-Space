/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import com.code.tama.triggerapi.data.AbstractDPLoader;
import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.data.DatapackRegistry;
import com.code.tama.triggerapi.universal.UniversalCommon;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

public class PlanetLoader extends AbstractDPLoaderGSON<PlanetLoader.Planet> {
	public static List<Planet> planets = new ArrayList<>();
	public static Map<String, Planet> strList = new HashMap<>();

	@Override
	public void rebuildCache() {
		planets = PlanetLoader.list();

		strList.clear();
		for (PlanetLoader.Planet p : planets) {
			strList.put(p.name(), p);
		}
	}

	public static ResourceLocation ID = UniversalCommon.modRL("tardis/planets");

	public Class<Planet> GetClass() {
		return Planet.class;
	}

	public ResourceLocation id() {
		return ID;
	}

	@Override
	public String dataPath() {
		return "tts/tardis/planets";
	}

	public record Planet(
			String name,
			String id,
			int size,
			String texture,
			int rotation_speed,
			Orbit orbit
	) {
	}

	public record Orbit(
			String parent,
			double distance,
			long period,
			double phase,
			double inclination,
			double eccentricity
	) {
	}

	public static List<Planet> list() {
		return ((AbstractDPLoader.AbstractDPList<PlanetLoader.Planet>) DatapackRegistry.getLoader(PlanetLoader.ID).list).getList();
	}
}
