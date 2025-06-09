/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.records;

import net.minecraft.resources.ResourceLocation;

public record DataExterior(String name, ResourceLocation ModelName) {
    @Override
    public String toString() {
        return "DataExterior{" +
                "name='" + name + '\'' +
                ", modelname=" + ModelName +
                '}';
    }
}
