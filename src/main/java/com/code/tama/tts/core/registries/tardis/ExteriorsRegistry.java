/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.registries.tardis;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;

public class ExteriorsRegistry {
	public static ArrayList<ExteriorModelContainer> EXTERIORS = new ArrayList<>();
	/**
	 * Key: Collection name. Value: Group name
	 */
	public static Map<String, List<String>> COLLECTIONS = new LinkedHashMap<>();
	public static Map<String, List<ExteriorModelContainer>> GROUPS = new LinkedHashMap<>();

	public static ExteriorModelContainer Cycle(ExteriorModelContainer Variant) {
		return EXTERIORS.get(Cycle(GetOrdinal(Variant)));
	}

	public static int Cycle(int Index) {
		Index++;
		return Index >= EXTERIORS.size() ? 0 : Index;
	}

	public static ExteriorModelContainer CycleDown(ExteriorModelContainer Variant) {
		return EXTERIORS.get(CycleDown(GetOrdinal(Variant)));
	}

	public static int CycleDown(int Index) {
		Index--;
		return Math.max(Index, 0);
	}

	public static ExteriorModelContainer Get(int Variant) {
		if (Variant >= EXTERIORS.size())
			Variant = 0;
		return EXTERIORS.get(Variant);
	}

	public static ExteriorModelContainer GetByName(String Name) {
		return EXTERIORS.stream().filter(ext -> ext.getName().equals(Name)).toList().get(0);
	}

	public static int GetOrdinal(ExteriorModelContainer Variant) {
		for (int ord = 0; ord < ExteriorsRegistry.EXTERIORS.size(); ord++) {
			ExteriorModelContainer v = ExteriorsRegistry.EXTERIORS.get(ord);
			if (v.getName().equals(Variant.getName()) && v.getTexture().equals(Variant.getTexture())
					&& v.getModel().equals(Variant.getModel())) {
				return ord;
			}

		}
		return 0;
	}

	public static String CycleCollection(String collection) {
		List<String> keys = new ArrayList<>(COLLECTIONS.keySet());
		int idx = keys.indexOf(collection);
		return keys.get((idx + 1) % keys.size());
	}

	public static int CycleGroup(String group) {
		String arr[] = (String[]) ExteriorsRegistry.GROUPS.keySet().toArray(String[]::new);
		for (int ord = 0; ord < ExteriorsRegistry.GROUPS.size(); ord++) {
			String v = arr[ord];
			if (v.equals(group))
				return ord + 1 >= ExteriorsRegistry.GROUPS.size() ? 0 : ord + 1;
		}
		return 0;
	}

	public static String CycleGroup(String group, String collection) {
		List<String> groups = COLLECTIONS.get(collection);
		if (groups == null || groups.isEmpty())
			return group;
		int idx = groups.indexOf(group);
		return groups.get((idx + 1) % groups.size());
	}

	public static String CyclePrevGroup(String group, String collection) {
		List<String> groups = COLLECTIONS.get(collection);
		if (groups == null || groups.isEmpty())
			return group;
		int idx = groups.indexOf(group);
		return groups.get((idx - 1 + groups.size()) % groups.size());
	}

	public static ExteriorModelContainer CycleInGroup(ExteriorModelContainer exterior) {
		List<ExteriorModelContainer> l = GROUPS.get(exterior.getParent());
		int idx = indexInGroup(l, exterior);
		return l.get((idx + 1) % l.size());
	}

	public static ExteriorModelContainer CycleDownInGroup(ExteriorModelContainer exterior) {
		List<ExteriorModelContainer> l = GROUPS.get(exterior.getParent());
		int idx = indexInGroup(l, exterior);
		return l.get((idx - 1 + l.size()) % l.size());
	}

	private static int indexInGroup(List<ExteriorModelContainer> l, ExteriorModelContainer exterior) {
		for (int i = 0; i < l.size(); i++) {
			if (l.get(i).getName().equals(exterior.getName()))
				return i;
		}
		return 0;
	}
}
