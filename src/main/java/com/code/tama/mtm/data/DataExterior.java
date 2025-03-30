package com.code.tama.mtm.data;

import net.minecraft.resources.ResourceLocation;

public class DataExterior {
    private final String name;
    private final ResourceLocation texture;

    public DataExterior(String name, ResourceLocation texture) {
        this.name = name;
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public String toString() {
        return "DataExterior{" +
                "name='" + name + '\'' +
                ", texture=" + texture +
                '}';
    }
}
