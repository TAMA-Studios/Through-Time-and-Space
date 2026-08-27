/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client;

import java.util.function.Function;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EmmisiveRenderType extends RenderType {
	// Dummy constructor required because RenderType is abstract
	public EmmisiveRenderType(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize,
			boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
		super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
	}

	private static final Function<ResourceLocation, RenderType> EMISSIVE_ENTITY_FUNC = Util.memoize((textureLoc) -> {
		CompositeState compositeState = CompositeState.builder()
				.setTextureState(new TextureStateShard(textureLoc, false, false))
				.setShaderState(new ShaderStateShard(ClientSetup::getEmissiveEntityShader))
				.setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP) // Crucial for lighting/gradients
				.setOverlayState(OVERLAY) // Crucial for entity hit-flash / overlay
				.createCompositeState(true);

		return create("tts:emissive_entity", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false,
				compositeState);
	});

	public static RenderType getEmissiveEntity(ResourceLocation texture) {
		return EMISSIVE_ENTITY_FUNC.apply(texture);
	}
}