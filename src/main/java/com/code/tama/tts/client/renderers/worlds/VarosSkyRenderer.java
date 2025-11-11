/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds;

import com.code.tama.tts.server.worlds.dimension.TDimensions;

import net.minecraft.resources.ResourceLocation;

public class VarosSkyRenderer extends BasicSkyRenderer {
	public VarosSkyRenderer() {
		super(new int[]{255, 21, 21});
	}

	@Override
	public ResourceLocation EffectsLocation() {
		return TDimensions.DimensionEffects.VAROS_EFFECTS;
	}

	@Override
	public boolean ShouldRenderVoid() {
		return false;
	}
}
