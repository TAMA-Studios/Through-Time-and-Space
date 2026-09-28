/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client;

import com.code.tama.tts.mixin.client.RenderStateShardAccessor;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public final class TardisLampRenderTypes {

	private TardisLampRenderTypes() {
	}

	private static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
			"tardis_additive_transparency", () -> {
				RenderSystem.enableBlend();
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
						GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			}, () -> {
				RenderSystem.disableBlend();
				RenderSystem.defaultBlendFunc(); // restore, mirroring vanilla's own teardown
			});

	public static final RenderType LAMP_GLOW_ADDITIVE = RenderType.create("tardis_lamp_glow_additive",
			DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLES, 256, false, true,
			RenderType.CompositeState.builder().setShaderState(RenderStateShardAccessor.getPositionColorShader())
					.setTransparencyState(ADDITIVE_TRANSPARENCY)
					.setWriteMaskState(RenderStateShardAccessor.getColorWrite())
					.setCullState(RenderStateShardAccessor.getNoCull())
					.setLightmapState(RenderStateShardAccessor.getNoLightmap())
					.setDepthTestState(RenderStateShardAccessor.getLequalDepthTest()).createCompositeState(false));
}