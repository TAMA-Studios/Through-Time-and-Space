/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import net.minecraftforge.eventbus.api.IEventBus;

import static com.code.tama.tts.TTSMod.MODID;

public class TriggerAPI {
    public static String MOD_ID = MODID;

    public TriggerAPI() {//String modId) {
        //        if (modId == null || modId.trim().isEmpty()) {
//            throw new IllegalArgumentException("MODID cannot be null or empty");
//        }
        Logger.info("Trigger engine started for %s", MOD_ID);
        ConfigHelper.register();
    }

    public TriggerAPI(IEventBus bus) {
        bus.register(JavaJSON.class);
        Logger.info("Trigger engine started for %s", MOD_ID);
        ConfigHelper.register();
    }

    public static String getModId() {
        return MOD_ID;
    }
}