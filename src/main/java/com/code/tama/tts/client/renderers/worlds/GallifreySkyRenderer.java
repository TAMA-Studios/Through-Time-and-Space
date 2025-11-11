/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds;

import com.code.tama.tts.client.renderers.worlds.helper.AbstractLevelRenderer;
import com.code.tama.tts.server.worlds.dimension.TDimensions;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;

public class GallifreySkyRenderer extends AbstractLevelRenderer {

	@Override
	public ResourceLocation EffectsLocation() {
		return TDimensions.DimensionEffects.GALLIFREY_EFFECTS.location();
	}

	@Override
	public void RenderLevel(@NotNull Camera camera, Matrix4f matrix4f, @NotNull PoseStack poseStack, Frustum frustum,
			float partialTicks) {
		// RenderStars(poseStack, matrix4f, partialTicks);x
	}

	@Override
	public boolean ShouldRenderVoid() {
		return false;
	}
}
