/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.rendering; /* (C) TAMA Studios 2025 */

import java.util.function.Consumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;

public class StencilUtils {
	public static void DrawStencil(PoseStack pose, Consumer<PoseStack> drawFrame, Consumer<PoseStack> drawScene) {
		RenderSystem.assertOnRenderThread();
		Minecraft mc = Minecraft.getInstance();

		mc.getMainRenderTarget().enableStencil();

		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

		pose.pushPose();

		// Disable writing to color and depth buffers so only stencil will update
		RenderSystem.colorMask(false, false, false, false);
		RenderSystem.depthMask(false);

		drawFrame.accept(pose);

		// Re-enable color + depth writes
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.depthMask(true);

		pose.popPose();

		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

		pose.pushPose();
		pose.translate(0, 0, 0);

		assert mc.level != null;
		drawScene.accept(pose);

		pose.popPose();

		GL11.glDisable(GL11.GL_STENCIL_TEST);
	}

	public static void DrawStencilNumerous(PoseStack pose, Consumer<PoseStack> drawFrame, Consumer<PoseStack> drawScene,
			int value) {
		RenderSystem.assertOnRenderThread();

		// Re-enable stencil test and writing for this rift's mask pass
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glStencilFunc(GL11.GL_ALWAYS, value, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

		pose.pushPose();
		RenderSystem.colorMask(false, false, false, false);
		RenderSystem.depthMask(false);
		drawFrame.accept(pose);
		RenderSystem.colorMask(true, true, true, true);
		RenderSystem.depthMask(true);
		pose.popPose();

		// Scene pass — only draw where stencil == value
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, value, 0xFF);

		pose.pushPose();
		drawScene.accept(pose);
		pose.popPose();

		// Leave stencil disabled for whatever renders after
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF); // restore mask so next rift's clear/write works
	}

	public static void drawColoredCube(PoseStack stack, float size, Vec3 color) {
		for (int i = 0; i < 4; i++) {
			stack.pushPose();
			stack.mulPose(Axis.YP.rotationDegrees(i * 90));
			stack.translate(0, 0, size / 2);
			drawColoredFrame(stack, size, size, color);
			stack.popPose();
		}

		stack.pushPose();
		stack.mulPose(Axis.XP.rotationDegrees(90));
		stack.translate(0, 0, size / 2);
		drawColoredFrame(stack, size, size, color);
		stack.popPose();

		stack.pushPose();
		stack.mulPose(Axis.XN.rotationDegrees(90));
		stack.translate(0, 0, size / 2);
		drawColoredFrame(stack, size, size, color);
		stack.popPose();
		// BufferBuilder buffer = Tesselator.getInstance().getBuilder();
		// RenderSystem.setShaderColor((float) color.x, (float) color.y, (float)
		// color.z, 1);
		// buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		// Matrix4f matrix = new Matrix4f(); // poseStack.last().pose();
		// float BaseSize = 20.0F;
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).endVertex();
		//
		// // Top
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		//
		// // East
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).endVertex();
		//
		// // West
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size)
		//
		// .endVertex();
		//
		// // SOUTH
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size)
		//
		// .endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).endVertex();
		//
		// // Down
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).endVertex();
		// buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).endVertex();
		// buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize -
		// size).endVertex();
		//
		// buffer.end();
		// RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	public static void drawColoredFrame(PoseStack poseStack, float width, float height, Vec3 color) {
		RenderSystem.setShader(GameRenderer::getPositionShader);

		poseStack.pushPose();
		poseStack.translate(-width / 2, -height / 2, 0);
		var matrix = poseStack.last().pose();

		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		RenderSystem.setShaderColor((float) color.x, (float) color.y, (float) color.z, 1);

		builder.vertex(matrix, 0, 0, 0).color(1, 1, 1, 1).endVertex();
		builder.vertex(matrix, 0, height, 0).color(1, 1, 1, 1).endVertex();
		builder.vertex(matrix, width, height, 0).color(1, 1, 1, 1).endVertex();
		builder.vertex(matrix, width, 0, 0).color(1, 1, 1, 1).endVertex();

		Tesselator.getInstance().end();

		poseStack.popPose();

		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	public static void drawFrame(PoseStack poseStack, float width, float height) {
		poseStack.pushPose();

		RenderSystem.setShader(GameRenderer::getPositionShader);
		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		poseStack.translate(-width / 2, -height / 2, 0);
		var matrix = poseStack.last().pose();

		builder.vertex(matrix, 0, 0, 0).endVertex();
		builder.vertex(matrix, 0, height, 0).endVertex();
		builder.vertex(matrix, width, height, 0).endVertex();
		builder.vertex(matrix, width, 0, 0).endVertex();

		Tesselator.getInstance().end();
		poseStack.popPose();
	}

	public static void endStencil() {
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
	}

	public static void setupStencil(Consumer<PoseStack> drawPortal, PoseStack stack) {
		GL11.glEnable(GL11.GL_STENCIL_TEST);

		// Clear once per frame
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

		// Write 1s to stencil where portal is drawn
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
		GL11.glStencilMask(0xFF);

		RenderSystem.depthMask(false);
		drawPortal.accept(stack);
		RenderSystem.depthMask(true);

		// Only draw where stencil == 1 from now on
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
	}
}
