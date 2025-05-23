package com.code.tama.mtm.server.data.json.records;

import net.minecraft.resources.ResourceLocation;

public record DataExterior(String name, ResourceLocation Texture, ResourceLocation ModelName) {
    @Override
    public String toString() {
        return "DataExterior{" +
                "name='" + name + '\'' +
                "texture='" + Texture + '\'' +
                ", modelname=" + ModelName +
                '}';
    }
}
