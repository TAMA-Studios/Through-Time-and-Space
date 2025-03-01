package com.code.tama.TriggerAPI;

import static com.code.tama.mtm.mtm.MODID;

public class TriggerAPI {
    public static String MOD_ID = MODID;

    public TriggerAPI() {//String modId) {
        //        if (modId == null || modId.trim().isEmpty()) {
//            throw new IllegalArgumentException("MODID cannot be null or empty");
//        }
        Logger.info("Trigger engine started for %s", MOD_ID);
        ConfigHelper.register();
    }

    public static String getModId() {
        return MOD_ID;
    }
}