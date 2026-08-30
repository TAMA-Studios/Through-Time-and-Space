/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import com.code.tama.tts.TTSMod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.boti.teleporting.TickScheduler;
import com.code.tama.triggerapi.events.TardisFlightEventHandler;

public class TriggerAPI {
	public static final String MOD_ID = TTSMod.MODID; // THIS MUST BE SET TO YOUR MODS MODID!

	public TriggerAPI(IEventBus bus, String modid) {
		bus.register(JavaJSON.class);
		Logger.info("Trigger engine started for %s", MOD_ID);
		TickScheduler.register();
		MinecraftForge.EVENT_BUS.register(TardisFlightEventHandler.class);
	}

	public TriggerAPI(String modID) { // String modId) {
		if (modID == null || modID.trim().isEmpty()) {
			throw new IllegalArgumentException("MODID cannot be null or empty");
		}
		Logger.info("Trigger engine started for %s", MOD_ID);
	}

	public static String getModId() {
		return MOD_ID;
	}

	/**
	 * @return The version of the mod
	 */
	public static String getModVersion() {
		return ModList.get().getModFileById(getModId()).versionString();
	}
}
