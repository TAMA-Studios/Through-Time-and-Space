/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import static com.code.tama.tts.TTSMod.MODID;

import net.minecraftforge.eventbus.api.IEventBus;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;

public class TriggerAPI {
	public static String MOD_ID = MODID;

	public TriggerAPI(IEventBus bus, String modid) {
		MOD_ID = modid;
		bus.register(JavaJSON.class);
		Logger.info("Trigger engine started for %s", MOD_ID);
	}

	public TriggerAPI(String modID) { // String modId) {
		if (modID == null || modID.trim().isEmpty()) {
			throw new IllegalArgumentException("MODID cannot be null or empty");
		}
		MOD_ID = modID;
		Logger.info("Trigger engine started for %s", MOD_ID);
	}

	public static String getModId() {
		return MOD_ID;
	}
}
