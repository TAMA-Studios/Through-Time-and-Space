/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderBuffers;

@Mixin(LevelRenderer.class)
public interface ILevelRendererAccessor { // Mixins are being crap and not letting me use @Shadow without
	// crashing non-devmode players so I need an accessor
	@Accessor
	RenderBuffers getRenderBuffers();

	@Invoker("renderSnowAndRain")
	void renderSnowAndRain(LightTexture p_109704_, float p_109705_, double p_109706_, double p_109707_,
			double p_109708_);
}
