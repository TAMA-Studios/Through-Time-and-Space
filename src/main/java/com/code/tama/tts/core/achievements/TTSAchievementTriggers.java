/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.achievements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class TTSAchievementTriggers {

	private TTSAchievementTriggers() {
	}

	public static final TTSAchievementTrigger TARDIS_EVENT = new TTSAchievementTrigger("tardis_event");

	public static void register(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			CriteriaTriggers.register(TARDIS_EVENT);
		});
	}
}