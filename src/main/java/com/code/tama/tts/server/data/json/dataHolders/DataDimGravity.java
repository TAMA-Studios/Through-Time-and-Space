/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders;

import lombok.Builder;

import net.minecraft.resources.ResourceLocation;

@Builder
public class DataDimGravity {
	public float mavity;
	public ResourceLocation dimension;

	@Override
	public String toString() {
		return String.format("DataRecipe{dimension=%s,mavity=%s}", dimension, mavity);
	}
}
