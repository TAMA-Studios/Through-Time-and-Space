/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.mixin.client.RenderStateShardAccessor;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class TRenderTypes {

	public static final ResourceLocation ARC_TEXTURE = UniversalCommon.modRL("textures/particle/arc.png");

	public static final RenderType ARC = RenderType.create(TTSMod.MODID + ":dynamorphic_arc",
			DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,
			RenderType.CompositeState.builder()
					.setShaderState(RenderStateShardAccessor.getPositionColorTexLightmapShader())
					.setTextureState(new RenderStateShard.TextureStateShard(ARC_TEXTURE, false, false))
					.setTransparencyState(RenderStateShardAccessor.getAdditiveTransparency())
					.setWriteMaskState(RenderStateShardAccessor.getColorWrite())
					.setCullState(RenderStateShardAccessor.getNoCull())
					.setLightmapState(RenderStateShardAccessor.getLightmap())
					.setDepthTestState(RenderStateShardAccessor.getLequalDepthTest()).createCompositeState(false));

	private TRenderTypes() {
	}
}