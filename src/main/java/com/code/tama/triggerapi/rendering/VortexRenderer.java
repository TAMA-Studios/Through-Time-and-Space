/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.rendering;

import java.util.EnumMap;
import java.util.Map;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VortexRenderer {

	// Constants / caches

	private static final int SEGMENT_COUNT = 32;
	private static final float PANEL_COUNT = 6f;
	private static final float PANEL_UV = 1f / PANEL_COUNT;
	private static final float SQRT3_OVER_2 = (float) Math.sqrt(3) / 2f;
	private static final int FULL_BRIGHT = 0xF000F0;

	private final Minecraft mc = Minecraft.getInstance();
	private final Tesselator tesselator = Tesselator.getInstance();

	// Textures (cached existence)

	public final Map<LayerType, ResourceLocation> textureLayers = new EnumMap<>(LayerType.class);

	// Parameters (tweak these)

	private final float wobbleSpeed = 0.5f;
	private final float wobbleSeparation = 32f;
	private final float wobbleAmplitude = 2f;
	private final float diameter = 32f;
	private final float scrollSpeed = 4f;

	private float time = 0f;

	public VortexRenderer(ResourceLocation baseTexture) {
		// keep same naming convention as original for derived textures
		textureLayers.put(LayerType.BASE, baseTexture);
		textureLayers.put(LayerType.SECOND,
				baseTexture.withPath(baseTexture.getPath().replace(".png", "") + "_two.png"));
		textureLayers.put(LayerType.THIRD,
				baseTexture.withPath(baseTexture.getPath().replace(".png", "") + "_three.png"));

		// Pre-check resources once; store null if missing to avoid expensive per-frame
		// checks
		textureLayers.replaceAll((layer, loc) -> {
			if (loc == null)
				return null;
			try {
				return mc.getResourceManager().getResource(loc).isEmpty() ? null : loc;
			} catch (Exception e) {
				return null;
			}
		});
	}

	/** Renders the vortex **/
	public void renderVortex(PoseStack stack) {
		renderLayer(stack, LayerType.BASE, 1f);
		renderLayer(stack, LayerType.SECOND, 1.5f);
		renderLayer(stack, LayerType.THIRD, 2f);
	}

	/** Renders the second/third layer. **/
	private void renderVortexLayer(PoseStack stack, LayerType layer) {
		if (textureLayers.get(layer) != null)
			renderLayer(stack, layer, layer.equals(LayerType.SECOND) ? 1.5f : 2f);
	}

	/// Internal layer rendering (cached state)
	private void renderLayer(PoseStack stack, LayerType layerType, float scaleFactor) {
		ResourceLocation texture = textureLayers.get(layerType);
		if (texture == null)
			return;

		// update time once per layer
		time += mc.getDeltaFrameTime() / 360f;

		stack.pushPose();

		// scale the entire layer
		stack.scale(diameter / scaleFactor, diameter / scaleFactor, diameter);

		// Setup shader + texture + blending
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		BufferBuilder buffer = tesselator.getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		// compute texture scroll (uses world gameTime when available)
		long gameTime = mc.level != null ? mc.level.getGameTime() : 0L;
		float textureDistance = (gameTime / 200.0f) * -scrollSpeed;

		// capture current matrices once per frame
		Matrix4f pose = stack.last().pose();
		Matrix3f normal = stack.last().normal();

		LevelRenderContext ctx = new LevelRenderContext(pose, normal, textureDistance);

		for (int i = 0; i < SEGMENT_COUNT; ++i) {
			renderSection(buffer, i, ctx);
		}

		tesselator.end();

		RenderSystem.disableBlend();
		stack.popPose();
	}

	// Build one hex-panel segment (6 quads per segment)
	private void renderSection(VertexConsumer builder, int zOffset, LevelRenderContext ctx) {
		float panel = PANEL_UV;
		float startScale = (float) Math.sin(zOffset * Math.PI / SEGMENT_COUNT);
		float endScale = (float) Math.sin((zOffset + 1) * Math.PI / SEGMENT_COUNT);

		float distortion = computeDistortionFactor(time, zOffset);
		float distortionPlusOne = computeDistortionFactor(time, zOffset + 1);

		// v offsets
		int vOffsetIndex = (zOffset * panel + ctx.textureDistance > 1.0f) ? zOffset - 6 : zOffset;
		float vPanelOffset = (vOffsetIndex * panel) + ctx.textureDistance;
		float panelDistanceOffset = panel + ctx.textureDistance;
		float vPanelOffsetNext = vOffsetIndex * panel + panelDistanceOffset;

		// iterate over 6 u-panels; each produces a single quad (4 verts)
		for (int uOffset = 0; uOffset < 6; uOffset++) {
			float uPanelOffset = uOffset * panel;
			float uPanelOffsetPlus = uPanelOffset + panel;

			// Default V direction (scrolls forward)
			float v1 = vPanelOffset;
			float v2 = vPanelOffsetNext;

			// Flip V for upside-down panels (2 and 5)
			boolean flipUV = (uOffset == 2 || uOffset == 5);

			if (flipUV) {
				var tmp = v1;
				v1 = v2;
				v2 = tmp;

				tmp = uPanelOffset;
				uPanelOffset = uPanelOffsetPlus;
				uPanelOffsetPlus = tmp;
			}

			switch (uOffset) {
				case 0 -> addQuad(builder, ctx, 0f, -startScale + distortion, -zOffset, 0f,
						-endScale + distortionPlusOne, -zOffset - 1, -endScale * SQRT3_OVER_2,
						endScale / -2f + distortionPlusOne, -zOffset - 1, -startScale * SQRT3_OVER_2,
						startScale / -2f + distortion, -zOffset, uPanelOffset, uPanelOffsetPlus, v1, v2);
				case 1 -> addQuad(builder, ctx, -startScale * SQRT3_OVER_2, startScale / -2f + distortion, -zOffset,
						-endScale * SQRT3_OVER_2, endScale / -2f + distortionPlusOne, -zOffset - 1,
						-endScale * SQRT3_OVER_2, endScale / 2f + distortionPlusOne, -zOffset - 1,
						-startScale * SQRT3_OVER_2, startScale / 2f + distortion, -zOffset, uPanelOffset,
						uPanelOffsetPlus, v1, v2);
				case 2 -> addQuad(builder, ctx, 0f, endScale + distortionPlusOne, -zOffset - 1, 0f,
						startScale + distortion, -zOffset, -startScale * SQRT3_OVER_2, startScale / 2f + distortion,
						-zOffset, -endScale * SQRT3_OVER_2, endScale / 2f + distortionPlusOne, -zOffset - 1,
						uPanelOffset, uPanelOffsetPlus, v1, v2);
				case 3 -> addQuad(builder, ctx, 0f, startScale + distortion, -zOffset, 0f, endScale + distortionPlusOne,
						-zOffset - 1, endScale * SQRT3_OVER_2, endScale / 2f + distortionPlusOne, -zOffset - 1,
						startScale * SQRT3_OVER_2, startScale / 2f + distortion, -zOffset, uPanelOffset,
						uPanelOffsetPlus, v1, v2);
				case 4 -> addQuad(builder, ctx, startScale * SQRT3_OVER_2, startScale / 2f + distortion, -zOffset,
						endScale * SQRT3_OVER_2, endScale / 2f + distortionPlusOne, -zOffset - 1,
						endScale * SQRT3_OVER_2, endScale / -2f + distortionPlusOne, -zOffset - 1,
						startScale * SQRT3_OVER_2, startScale / -2f + distortion, -zOffset, uPanelOffset,
						uPanelOffsetPlus, v1, v2);
				case 5 -> addQuad(builder, ctx, 0f, -endScale + distortionPlusOne, -zOffset - 1, 0f,
						-startScale + distortion, -zOffset, startScale * SQRT3_OVER_2, startScale / -2f + distortion,
						-zOffset, endScale * SQRT3_OVER_2, endScale / -2f + distortionPlusOne, -zOffset - 1,
						uPanelOffset, uPanelOffsetPlus, v1, v2);
			}
		}
	}

	// Vertex helpers (quad = 4 verts)

	private void addQuad(VertexConsumer builder, LevelRenderContext ctx, float x1, float y1, float z1, float x2,
			float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, float u1, float u2,
			float v1, float v2) {

		addVertex(builder, ctx, x1, y1, z1, u1, v1);
		addVertex(builder, ctx, x2, y2, z2, u1, v2);
		addVertex(builder, ctx, x3, y3, z3, u2, v2);
		addVertex(builder, ctx, x4, y4, z4, u2, v1);
	}

	private void addVertex(VertexConsumer builder, LevelRenderContext ctx, float x, float y, float z, float u,
			float v) {
		builder.vertex(ctx.pose, x, y, z).color(1f, 1f, 1f, 1f).uv(u, v).uv2(FULL_BRIGHT).normal(ctx.normal, 0f, 0f, 0f)
				.endVertex();
	}

	// Distortion math (cached params above)

	private float computeDistortionFactor(float time, int t) {
		return (float) (Math.sin(time * wobbleSpeed * 2.0 * Math.PI + (13 - t) * wobbleSeparation) * wobbleAmplitude)
				/ 8f;
	}

	public enum LayerType {
		BASE, SECOND, THIRD
	}

	private record LevelRenderContext(Matrix4f pose, Matrix3f normal, float textureDistance) {
	}
}
