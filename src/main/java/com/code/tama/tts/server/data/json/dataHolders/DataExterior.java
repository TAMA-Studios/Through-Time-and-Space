/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record DataExterior(String name, ResourceLocation ModelName) {
    @Override
    public @NotNull String toString() {
        return "DataExterior{" + "name='" + name + '\'' + ", modelname=" + ModelName + '}';
    }
}
