/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders;

import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;

public record DataExterior(String name, ResourceLocation ModelName, ResourceLocation texture, ResourceLocation light,
		float maxRot) {
	@Override
	public @NotNull String toString() {
		return "DataExterior{" + "name='" + name + '\'' + ", modelname=" + ModelName + '\'' + "texture=" + texture
				+ '\'' + "maxRot=" + maxRot + '\'' + "lightmap=" + light + '}';
	}
}
