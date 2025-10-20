/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;

/** Basic stencil + black quad test */
public class TardisBotiRenderer {

	/** draws a solid black quad using vertex colours */
	private static void drawBlackQuad(PoseStack poseStack) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		Matrix4f mat = poseStack.last().pose();
		builder.vertex(mat, 0, 0, 0).color(1f, 1f, 1f, 1f).endVertex();
		builder.vertex(mat, 0, 1, 0).color(1f, 1f, 1f, 1f).endVertex();
		builder.vertex(mat, 1, 1, 0).color(1f, 1f, 1f, 1f).endVertex();
		builder.vertex(mat, 1, 0, 0).color(1f, 1f, 1f, 1f).endVertex();
		Tesselator.getInstance().end();
	}

	/** draws the stencil mask rectangle */
	public static void drawFrame(PoseStack poseStack, float width, float height) {
		RenderSystem.setShader(GameRenderer::getPositionShader);
		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		poseStack.translate(-width / 2, -height / 2, 0);
		Matrix4f matrix = poseStack.last().pose();
		builder.vertex(matrix, 0, 0, 0).endVertex();
		builder.vertex(matrix, 0, height, 0).endVertex();
		builder.vertex(matrix, width, height, 0).endVertex();
		builder.vertex(matrix, width, 0, 0).endVertex();
		Tesselator.getInstance().end();
	}

	public static void render(Level tardis, ExteriorTile tile, PoseStack pose, MultiBufferSource buffer,
			float ageInTicks, int packedLight, int packedOverlay) {

		RenderSystem.assertOnRenderThread();
		Minecraft mc = Minecraft.getInstance();

		// Make sure the main target has a stencil attachment
		mc.getMainRenderTarget().enableStencil();

		// === set up stencil mask ===
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

		pose.pushPose();
		pose.translate(0, 0.3, -0.5);
		pose.scale(1, 0.8f, 1);

		// Only write stencil, not color/depth
		RenderSystem.colorMask(false, false, false, false);
		RenderSystem.depthMask(false);

		drawFrame(pose, 1, 3); // your “door frame”

		// back to normal writes
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.depthMask(true);
		pose.popPose();

		// now only draw where stencil==1
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

		pose.pushPose();
		pose.translate(0, 0, 0);
		pose.scale(4, 4, 0);

		drawBlackQuad(pose);

		pose.popPose();

		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}
}
