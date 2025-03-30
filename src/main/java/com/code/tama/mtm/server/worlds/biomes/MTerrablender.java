package com.code.tama.mtm.server.worlds.biomes;

import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

import static com.code.tama.mtm.MTMMod.MODID;

public class MTerrablender {
    public static void registerBiomes() {
        Regions.register(new GallifreyRegion(new ResourceLocation(MODID, "gallifrey"), 5));
    }
}