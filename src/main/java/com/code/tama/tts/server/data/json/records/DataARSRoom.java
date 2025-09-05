/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.records;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record DataARSRoom(String name, ResourceLocation structure) {
    @Override
    public @NotNull String toString() {
        return "DataARSRoom{" + "name='" + name + '\'' + ", location=" + structure + '}';
    }
}
