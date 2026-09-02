/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.registries.tardis;

import java.util.ArrayList;

import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;

public class ExteriorsRegistry {
	public static ArrayList<ExteriorModelContainer> EXTERIORS = new ArrayList<>();

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
}
