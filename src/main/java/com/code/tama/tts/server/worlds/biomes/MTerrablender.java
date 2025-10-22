/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.biomes;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.compat.CompatClass;
import com.code.tama.tts.server.worlds.biomes.surface.MSurfaceRules;
import terrablender.api.SurfaceRuleManager;

import net.minecraftforge.fml.ModList;

public class MTerrablender extends CompatClass {
	@Override
	public void runCompat() {
		// Regions.register(new GallifreyRegion(new ResourceLocation(MODID,
		// "gallifrey"), 5));
	}

	@Override
	public void runCommonSetup() {
		TTSMod.LOGGER.info("Surface Rules Added");
		if (ModList.get().isLoaded("terrablender"))
			SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID,
					MSurfaceRules.makeRules());
	}
}
