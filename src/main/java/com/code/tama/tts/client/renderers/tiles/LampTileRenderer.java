/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.client.TardisLampRenderTypes;
import com.code.tama.tts.core.tileentities.LampTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;

import com.code.tama.triggerapi.animation.AnimationTicker;

public class LampTileRenderer implements BlockEntityRenderer<LampTile> {

	private static final float CORE_RADIUS = 0.35f;
	private static final float VOLUME_RADIUS = 1.6f;
	private static final float BEAM_HEIGHT = 3.0f;
	private static final float BEAM_BASE_RADIUS = 0.5f;
	private static final float BEAM_TOP_RADIUS = 1.2f;
	private static final int BEAM_SIDES = 8;

	private static final int R = 90, G = 170, B = 255;

	private static final int FLARE_SEGMENTS = 16;

	public LampTileRenderer(BlockEntityRendererProvider.Context ctx) {
	}

	@Override
	public void render(LampTile be, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource,
			int packedLight, int packedOverlay) {

		// if (!be.isLit()) return;

		renderLamp(partialTick, poseStack, bufferSource);
	}

	public static void renderLamp(float partialTick, PoseStack poseStack, MultiBufferSource bufferSource) {
		long gameTime = AnimationTicker.getTicks();
		float pulse = 0.85f + 0.15f * Mth.sin((gameTime + partialTick) * 0.1f);

		poseStack.pushPose();
		poseStack.translate(0.5, 0.5, 0.5);

		VertexConsumer buffer = bufferSource.getBuffer(TardisLampRenderTypes.LAMP_GLOW_ADDITIVE);

		Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
		renderBillboardFlare(poseStack, buffer, camera, CORE_RADIUS * pulse, 255, 255, 255, 235);
		renderBillboardFlare(poseStack, buffer, camera, VOLUME_RADIUS * pulse, R, G, B, 70);
		// renderBeamPrism(poseStack, buffer, pulse);

		poseStack.popPose();
	}

	private static void renderBillboardFlare(PoseStack poseStack, VertexConsumer buffer, Camera camera, float radius,
			int r, int g, int b, int centerAlpha) {
		Matrix4f mat = poseStack.last().pose();

		Vector3f left = camera.getLeftVector();
		Vector3f up = camera.getUpVector();

		float[] offX = new float[FLARE_SEGMENTS + 1];
		float[] offY = new float[FLARE_SEGMENTS + 1];
		float[] offZ = new float[FLARE_SEGMENTS + 1];
		for (int i = 0; i <= FLARE_SEGMENTS; i++) {
			double angle = (i / (double) FLARE_SEGMENTS) * Math.PI * 2;
			float cx = (float) Math.cos(angle) * radius;
			float cy = (float) Math.sin(angle) * radius;
			offX[i] = left.x() * cx + up.x() * cy;
			offY[i] = left.y() * cx + up.y() * cy;
			offZ[i] = left.z() * cx + up.z() * cy;
		}

		for (int i = 0; i < FLARE_SEGMENTS; i++) {
			buffer.vertex(mat, 0, 0, 0).color(r, g, b, centerAlpha).endVertex();
			buffer.vertex(mat, offX[i], offY[i], offZ[i]).color(r, g, b, 0).endVertex();
			buffer.vertex(mat, offX[i + 1], offY[i + 1], offZ[i + 1]).color(r, g, b, 0).endVertex();
		}
	}

	private static void renderBeamPrism(PoseStack poseStack, VertexConsumer buffer, float pulse) {
		Matrix4f mat = poseStack.last().pose();
		int alphaBase = (int) (60 * pulse);

		float[] baseX = new float[BEAM_SIDES + 1];
		float[] baseZ = new float[BEAM_SIDES + 1];
		float[] topX = new float[BEAM_SIDES + 1];
		float[] topZ = new float[BEAM_SIDES + 1];
		for (int i = 0; i <= BEAM_SIDES; i++) {
			double angle = (i / (double) BEAM_SIDES) * Math.PI * 2;
			baseX[i] = (float) (Math.cos(angle) * BEAM_BASE_RADIUS);
			baseZ[i] = (float) (Math.sin(angle) * BEAM_BASE_RADIUS);
			topX[i] = (float) (Math.cos(angle) * BEAM_TOP_RADIUS);
			topZ[i] = (float) (Math.sin(angle) * BEAM_TOP_RADIUS);
		}

		for (int i = 0; i < BEAM_SIDES; i++) {
			vertex(buffer, mat, baseX[i], 0, baseZ[i], R, G, B, alphaBase);
			vertex(buffer, mat, baseX[i + 1], 0, baseZ[i + 1], R, G, B, alphaBase);
			vertex(buffer, mat, topX[i + 1], BEAM_HEIGHT, topZ[i + 1], R, G, B, 0);

			vertex(buffer, mat, baseX[i], 0, baseZ[i], R, G, B, alphaBase);
			vertex(buffer, mat, topX[i + 1], BEAM_HEIGHT, topZ[i + 1], R, G, B, 0);
			vertex(buffer, mat, topX[i], BEAM_HEIGHT, topZ[i], R, G, B, 0);
		}
	}

	private static void vertex(VertexConsumer buffer, Matrix4f mat, float x, float y, float z, int r, int g, int b,
			int a) {
		buffer.vertex(mat, x, y, z).color(r, g, b, a).endVertex();
	}
}