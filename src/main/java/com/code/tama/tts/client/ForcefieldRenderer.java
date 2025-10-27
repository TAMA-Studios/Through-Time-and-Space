/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;

public class ForcefieldRenderer {

	private static final float RADIUS = 1.0f; // radius of the sphere
	private static final int LAT_SEGMENTS = 6; // vertical segments
	private static final int LONG_SEGMENTS = 6; // horizontal segments

	public static void render(PoseStack poseStack, MultiBufferSource buffer) {
		poseStack.pushPose();

		RenderSystem.enableDepthTest();
		RenderSystem.disableCull();

		BufferBuilder builder = Tesselator.getInstance().getBuilder();

		// center it on the block
		poseStack.translate(0.5, 0.5, 0.5);

		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		// VertexConsumer consumer = buffer.getBuffer(RenderType.debugQuads());

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		PoseStack.Pose matrix = poseStack.last();

		for (int lat = 0; lat < LAT_SEGMENTS; lat++) {
			double theta1 = Math.PI * lat / LAT_SEGMENTS;
			double theta2 = Math.PI * (lat + 1) / LAT_SEGMENTS;

			for (int lon = 0; lon < LONG_SEGMENTS; lon++) {
				double phi1 = 2 * Math.PI * lon / LONG_SEGMENTS;
				double phi2 = 2 * Math.PI * (lon + 1) / LONG_SEGMENTS;

				// four vertices of this quad
				float x1 = (float) (RADIUS * Math.sin(theta1) * Math.cos(phi1));
				float y1 = (float) (RADIUS * Math.cos(theta1));
				float z1 = (float) (RADIUS * Math.sin(theta1) * Math.sin(phi1));

				float x2 = (float) (RADIUS * Math.sin(theta2) * Math.cos(phi1));
				float y2 = (float) (RADIUS * Math.cos(theta2));
				float z2 = (float) (RADIUS * Math.sin(theta2) * Math.sin(phi1));

				float x3 = (float) (RADIUS * Math.sin(theta2) * Math.cos(phi2));
				float y3 = (float) (RADIUS * Math.cos(theta2));
				float z3 = (float) (RADIUS * Math.sin(theta2) * Math.sin(phi2));

				float x4 = (float) (RADIUS * Math.sin(theta1) * Math.cos(phi2));
				float y4 = (float) (RADIUS * Math.cos(theta1));
				float z4 = (float) (RADIUS * Math.sin(theta1) * Math.sin(phi2));

				// draw the quad
				drawQuad(matrix, builder, x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4,
						FastColor.ARGB32.color(255, 0, 0, 255));
			}
		}

		Tesselator.getInstance().end();

		RenderSystem.disableDepthTest();
		RenderSystem.enableCull();

		poseStack.popPose();
	}

	private static void drawQuad(PoseStack.Pose matrix, BufferBuilder builder, float x1, float y1, float z1, float x2,
			float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, int color) {
		float r = ((color >> 16) & 0xFF) / 255f;
		float g = ((color >> 8) & 0xFF) / 255f;
		float b = (color & 0xFF) / 255f;
		float a = ((color >> 24) & 0xFF) / 255f;

		float[][] verts = {{x1, y1, z1}, {x2, y2, z2}, {x3, y3, z3}, {x4, y4, z4}};
		for (float[] v : verts) {
			float len = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
			float nx = v[0] / len;
			float ny = v[1] / len;
			float nz = v[2] / len;

			builder.vertex(matrix.pose(), v[0], v[1], v[2]).color(r, g, b, a).normal(matrix.normal(), nx, ny, nz)
					.endVertex();
		}
	}

}
