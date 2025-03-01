package com.code.tama.mtm.Enums;

import net.minecraft.resources.ResourceLocation;

import static com.code.tama.mtm.mtm.MODID;

public enum Structures {
    CleanInterior(new ResourceLocation(MODID, "clean"));

    final ResourceLocation path;
    Structures(ResourceLocation path) {
        this.path = path;
    }

    public ResourceLocation GetRL() {
        return this.path;
    }
}
