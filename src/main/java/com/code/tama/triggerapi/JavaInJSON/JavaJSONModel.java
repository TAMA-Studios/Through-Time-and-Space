/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import static com.mojang.math.Axis.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class JavaJSONModel extends Model {

	private static final Logger LOGGER = LogManager.getLogger();

	public Map<String, List<JavaJSONFile.FontData>> fontData = new HashMap<>();
	public JavaJSONParsed model;
	public float modelScale;
	public Map<String, JavaJSONRenderer> partsList = new HashMap<>();
	public List<JavaJSONRenderer> renderList = new ArrayList<>();
	public List<JavaJSONFile.FontData> rootfontData = new ArrayList<>();

	public JavaJSONModel() {
		this(16, 16, 1, null);
	}

	public JavaJSONModel(int texWidth, int texHeight, float scale) {
		this(texWidth, texHeight, scale, null);
	}

	public JavaJSONModel(int texWidth, int texHeight, float scale, List<JavaJSONFile.FontData> fontData) {
		super(JavaJSONRenderer::transparentRenderType);
		this.modelScale = scale;
		this.rootfontData = fontData != null ? fontData : new ArrayList<>();
	}

	public JavaJSONRenderer getPart(String groupName) {
		JavaJSONRenderer part = partsList.getOrDefault(groupName, JavaJSONParser.NULL_PART);
		return part;
	}

	public void renderFont(PoseStack poseStack, String _parent, JavaJSONFile.FontData fontData, float red, float green,
			float blue, float alpha, int packedLight) {
		Minecraft mc = Minecraft.getInstance();
		Font font = mc.font;
		JavaJSONRenderer parent = model.getPart(_parent);

		poseStack.pushPose();

		poseStack.translate(0.5, 0.0, 0.5);
		poseStack.translate(-parent.x / 16.0, 1.5 - parent.y / 16.0, parent.z / 16.0);

		poseStack.mulPose(ZN.rotationDegrees(parent.zRot));
		poseStack.mulPose(YN.rotationDegrees(-parent.yRot));
		poseStack.mulPose(XN.rotationDegrees(-parent.xRot));

		poseStack.translate(fontData.origin[0] / 16.0, fontData.origin[1] / 16.0, fontData.origin[2] / 16.0);

		poseStack.mulPose(ZN.rotationDegrees((float) Math.toRadians(fontData.rotation[2] + 180)));
		poseStack.mulPose(YN.rotationDegrees((float) Math.toRadians(-fontData.rotation[1])));
		poseStack.mulPose(XN.rotationDegrees((float) Math.toRadians(-fontData.rotation[0])));

		float scale = fontData.scale * modelScale / 100.0f;
		poseStack.scale(scale, scale, scale);

		float adjustmentX = fontData.centered[0] ? -font.width(fontData.value) / 2.0f : 0;
		float adjustmentY = fontData.centered[1] ? (1.0f / 32.0f) * (fontData.scale * modelScale) : 0;

		if (fontData.glow) {
			font.drawInBatch(fontData.value, adjustmentX, adjustmentY, new Color(red, green, blue, alpha).getRGB(),
					false, poseStack.last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(),
					Font.DisplayMode.NORMAL, 0, packedLight);
		} else {
			int color = new Color(red, green, blue, alpha).getRGB();
			int realRed = (int) (((color >> 16) & 0xFF) * 0.7);
			int realGreen = (int) (((color >> 8) & 0xFF) * 0.7);
			int realBlue = (int) ((color & 0xFF) * 0.7);
			int realColor = (0xFF << 24) | (realRed << 16) | (realGreen << 8) | realBlue;

			font.drawInBatch(fontData.value, adjustmentX, adjustmentY, realColor, false, poseStack.last().pose(),
					Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, packedLight);
		}

		poseStack.popPose();
	}

	public void renderLayer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		poseStack.pushPose();
		// Apply scale only, let group pivots handle positioning
		poseStack.scale(modelScale, modelScale, modelScale);

		for (JavaJSONRenderer renderer : renderList) {
			renderer.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		}

		poseStack.popPose();
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		MultiBufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
		RenderType renderType;

		if (model != null && alpha > 0) {
			// Alpha Overlay & Map
			if (alpha < 1) {
				boolean alphaMapExists = model.getModelInfo().getAlphaMap() != null;
				renderType = JavaJSONRenderer.transparentRenderType(
						alphaMapExists ? model.getModelInfo().getAlphaMap() : model.getModelInfo().getTexture());
				renderLayer(poseStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue,
						alpha);

				if (alphaMapExists) {
					renderType = JavaJSONRenderer.transparentRenderType(model.getModelInfo().getAlphaMap());
					renderLayer(poseStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green,
							blue, alpha);
				}
			}

			// Normal Textures
			renderType = JavaJSONRenderer.transparentRenderType(model.getModelInfo().getTexture());
			renderLayer(poseStack, bufferSource.getBuffer(renderType), packedLight, packedOverlay, red, green, blue,
					alpha);

			// Light Map
			if (model.getModelInfo().getLightMap() != null) {
				renderType = JavaJSONRenderer.lightMapRenderType(model.getModelInfo().getLightMap());
				renderLayer(poseStack, bufferSource.getBuffer(renderType), 0xf000f0, // Max light
						packedOverlay, red, green, blue, alpha);
			}

			// Font Data
			if (!rootfontData.isEmpty()) {
				for (JavaJSONFile.FontData fontData : rootfontData) {
					renderFont(poseStack, null, fontData, fontData.getColor().getRed() / 255.0f * red,
							fontData.getColor().getGreen() / 255.0f * green,
							fontData.getColor().getBlue() / 255.0f * blue, alpha, packedLight);
				}
			}

			if (!fontData.isEmpty()) {
				for (Map.Entry<String, List<JavaJSONFile.FontData>> entry : fontData.entrySet()) {
					for (JavaJSONFile.FontData fontData : entry.getValue()) {
						renderFont(poseStack, entry.getKey(), fontData, fontData.getColor().getRed() / 255.0f * red,
								fontData.getColor().getGreen() / 255.0f * green,
								fontData.getColor().getBlue() / 255.0f * blue, alpha, packedLight);
					}
				}
			}
		} else {
			LOGGER.warn("Skipping render: model is null or alpha <= 0");
		}
	}
}
