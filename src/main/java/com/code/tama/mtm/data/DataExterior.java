package com.code.tama.mtm.data;

import net.minecraft.resources.ResourceLocation;

public record DataExterior(String name, ResourceLocation texture) {
    @Override
    public String toString() {
        return "DataExterior{" +
                "name='" + name + '\'' +
                ", texture=" + texture +
                '}';
    }
}
