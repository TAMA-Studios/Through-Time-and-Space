/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.RenderStateShard;

@Mixin(RenderStateShard.class)
public interface RenderStateShardAccessor {
	@Accessor("TRANSLUCENT_TRANSPARENCY")
	static RenderStateShard.TransparencyStateShard getTranslucentTransparency() {
		throw new AssertionError();
	}

	@Accessor("ADDITIVE_TRANSPARENCY")
	static RenderStateShard.TransparencyStateShard getAdditiveTransparency() {
		throw new AssertionError();
	}

	@Accessor("NO_TRANSPARENCY")
	static RenderStateShard.TransparencyStateShard getNoTransparency() {
		throw new AssertionError();
	}

	@Accessor("COLOR_WRITE")
	static RenderStateShard.WriteMaskStateShard getColorWrite() {
		throw new AssertionError();
	}

	@Accessor("NO_CULL")
	static RenderStateShard.CullStateShard getNoCull() {
		throw new AssertionError();
	}

	@Accessor("LEQUAL_DEPTH_TEST")
	static RenderStateShard.DepthTestStateShard getLequalDepthTest() {
		throw new AssertionError();
	}

	@Accessor("LIGHTMAP")
	static RenderStateShard.LightmapStateShard getLightmap() {
		throw new AssertionError();
	}

	@Accessor("NO_LIGHTMAP")
	static RenderStateShard.LightmapStateShard getNoLightmap() {
		throw new AssertionError();
	}

	@Accessor("POSITION_COLOR_TEX_LIGHTMAP_SHADER")
	static RenderStateShard.ShaderStateShard getPositionColorTexLightmapShader() {
		throw new AssertionError();
	}

}
