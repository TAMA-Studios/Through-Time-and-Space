/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.helpers.rendering.StencilUtils;

/**
 * Not actual BOTI but looks cool, for stuff like end portal, green screen (to
 * be added), a texture, whatever already has a WORKING stencil buffer setup *
 */
public class HalfBOTIRenderer {
	public static void render(Level tardis, ExteriorTile tile, PoseStack pose, MultiBufferSource buffer,
			float ageInTicks, int packedLight, int packedOverlay) {

		StencilUtils.DrawStencil(pose, (stack) -> StencilUtils.drawFrame(stack, 1, 3), (stack) -> {
			assert Minecraft.getInstance().level != null;
			renderEndPortalQuad(Minecraft.getInstance().level, (int) Minecraft.getInstance().level.getGameTime(),
					Minecraft.getInstance().getPartialTick(), stack);
		});
	}

	public static void renderEndPortalQuad(ClientLevel level, int ticks, float partialTick, PoseStack poseStack) {
		poseStack.pushPose();

		// Translate to desired render position
		poseStack.translate(-0.5, -0.5, 0); // centers the quad
		Matrix4f matrix = poseStack.last().pose();

		// Get buffer for End Portal effect
		MultiBufferSource.BufferSource source = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		VertexConsumer buffer = source.getBuffer(RenderType.endPortal());

		// Build a simple 1x1 quad
		buffer.vertex(matrix, 0, 0, 0).uv(0, 0).endVertex();
		buffer.vertex(matrix, 0, 1, 0).uv(0, 1).endVertex();
		buffer.vertex(matrix, 1, 1, 0).uv(1, 1).endVertex();
		buffer.vertex(matrix, 1, 0, 0).uv(1, 0).endVertex();

		// Flush the buffer
		source.endBatch(RenderType.endPortal());

		poseStack.popPose();
	}
}
