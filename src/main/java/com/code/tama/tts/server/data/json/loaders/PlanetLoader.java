/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json.loaders;

import java.util.*;

import lombok.Getter;

import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.data.AbstractDPLoader;
import com.code.tama.triggerapi.data.AbstractDPLoaderGSON;
import com.code.tama.triggerapi.data.DatapackRegistry;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class PlanetLoader extends AbstractDPLoaderGSON<PlanetLoader.Planet> {
	public static List<Planet> planets = new ArrayList<>();
	public static Map<String, Planet> strList = new HashMap<>();

	@Override
	public void rebuildCache() {
		planets = PlanetLoader.list();

		strList.clear();
		for (PlanetLoader.Planet p : planets) {
			strList.put(p.getName(), p);
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
		return "tts/planets";
	}

	@Getter
	public static class Planet {
		private String name;
		private String id;
		private int size;
		private String texture;
		private int rotation_speed;
		private Orbit orbit;
		private int x, y, z;
	}

	public static class Orbit {
		private String parent;
		private double distance;
		private long period;
		private double phase;
		private double inclination;
		private double eccentricity;

		public String parent() {
			return parent;
		}
		public double distance() {
			return distance;
		}
		public long period() {
			return period;
		}
		public double phase() {
			return phase;
		}
		public double inclination() {
			return inclination;
		}
		public double eccentricity() {
			return eccentricity;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Planet> list() {
		return ((AbstractDPLoader.AbstractDPList<PlanetLoader.Planet>) DatapackRegistry.getLoader(PlanetLoader.ID).list)
				.getList();
	}
}
