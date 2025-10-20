/* (C) TAMA Studios 2025 */
package com.code.tama.tts.compat;

import java.util.ArrayList;

import com.code.tama.tts.server.worlds.biomes.MTerrablender;
import lombok.Getter;
import lombok.Setter;

import net.minecraftforge.fml.ModList;

public class ModCompat {
	private static int ID = 0;
	private static final ArrayList<CompatThingy> compatList = new ArrayList<>();

	private static void RegisterCompat(String modid, CompatClass clazz) {
		compatList.add(ID++, new CompatThingy(modid, clazz));
	}

	public static void RegisterCompats() {
		RegisterCompat("terrablender", new MTerrablender());
	}

	public static void Run() {
		RegisterCompats();

		for (CompatThingy thingy : compatList) {
			if (ModList.get().isLoaded(thingy.modid)) {
				thingy.aClass.runCompat();
			}
		}
	}

	@Getter
	@Setter
	public static class CompatThingy {
		public CompatClass aClass;
		public String modid;

		public CompatThingy(String modid, CompatClass compatClass) {
			this.aClass = compatClass;
			this.modid = modid;
		}
	}
}
