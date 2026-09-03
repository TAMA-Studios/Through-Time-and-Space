/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

/**
 * Register in your renderer registration:
 * event.registerBlockEntityRenderer(YOUR_BE_TYPE.get(),
 * AnimatedGeoBlockEntityRenderer::new); <br />
 * Remember: your Block's getRenderShape() should return RenderShape.INVISIBLE
 * so the static baked model doesn't also draw a duplicate/wrong-pose copy.
 */
public class AnimatedGeoBlockEntityRenderer implements BlockEntityRenderer<AnimatedGeoBlockEntity> {

	private final GeoModel model;
	private final ResourceLocation texture;

	public AnimatedGeoBlockEntityRenderer(BlockEntityRendererProvider.Context ctx, GeoModel model,
			ResourceLocation texture) {
		this.model = model;
		this.texture = texture;
	}

	@Override
	public void render(AnimatedGeoBlockEntity be, float partialTick, PoseStack poseStack, MultiBufferSource buffer,
			int packedLight, int packedOverlay) {
		float nowTicks = be.getLevel() != null ? GeoAnimTicker.getTicks() : 0;
		be.player.apply(model, nowTicks, partialTick);

		poseStack.pushPose();
		poseStack.translate(0.5, 0, 0.5); // center on the block; adjust to taste

		var consumer = buffer.getBuffer(RenderType.entityCutout(texture));
		GeoRenderer.render(model, poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

		poseStack.popPose();
	}
}
