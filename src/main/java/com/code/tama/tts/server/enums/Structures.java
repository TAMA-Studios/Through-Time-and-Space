/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.enums;

import net.minecraft.resources.ResourceLocation;

import static com.code.tama.tts.TTSMod.MODID;

public enum Structures {
    CleanInterior(new ResourceLocation(MODID, "clean")),
    CitadelInterior(new ResourceLocation(MODID, "citadel"));

    final ResourceLocation path;

    Structures(ResourceLocation path) {
        this.path = path;
    }

    public ResourceLocation GetRL() {
        return this.path;
    }
}
