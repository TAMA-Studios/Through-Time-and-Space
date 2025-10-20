/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import static com.code.tama.tts.TTSMod.LOGGER;
import static com.mojang.math.Axis.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.ReflectionBuddy;

public class JavaJSONRenderer {

	private final List<JavaJSONRenderer> children = new ArrayList<>();

	public JavaJSONModel model;

	public ModelPart modelPart;

	public float x, y, z;
	public float xRot, yRot, zRot;

	public JavaJSONRenderer() {
		this.model = new JavaJSONModel();
		this.modelPart = new ModelPart(new ArrayList<>(), new java.util.HashMap<>());
	}
	public JavaJSONRenderer(JavaJSONModel model, ModelPart modelPart) {
		this.model = model;
		this.modelPart = modelPart;
	}

	protected static ResourceLocation generateAlphaOverlay(ResourceLocation texture) {
		try {
			Minecraft mc = Minecraft.getInstance();
			ResourceLocation newLocation = new ResourceLocation("tts",
					"textures/generated/alpha_overlay/" + texture.getPath());

			NativeImage originalImage = NativeImage.read(mc.getResourceManager().getResourceOrThrow(texture).open());
			NativeImage newImage = new NativeImage(originalImage.getWidth(), originalImage.getHeight(), false);
			newImage.copyFrom(originalImage);

			for (int y = 0; y < originalImage.getHeight(); y++) {
				for (int x = 0; x < originalImage.getWidth(); x++) {
					int pixel = originalImage.getPixelRGBA(x, y);
					int alpha = (pixel >> 24) & 0xFF;
					int red = (pixel >> 16) & 0xFF;
					int green = (pixel >> 8) & 0xFF;
					int blue = pixel & 0xFF;

					if (alpha > 0) {
						newImage.setPixelRGBA(x, y, (0x02 << 24) | (blue << 16) | (green << 8) | red);
					}
				}
			}

			TextureManager textureManager = mc.getTextureManager();
			ResourceLocation registered = textureManager.register(newLocation.getPath(), new DynamicTexture(newImage));
			return registered;
		} catch (IOException e) {
			LOGGER.error("Failed to generate alpha overlay for texture: {}", texture, e);
			return texture;
		}
	}

	protected static RenderType lightMapRenderType(ResourceLocation tex) {
		return RenderType.entityTranslucent(tex);
	}

	protected static RenderType transparentRenderType(ResourceLocation tex) {
		return RenderType.entityTranslucent(tex);
	}

	public void addChild(JavaJSONRenderer child) {
		this.children.add(child);
	}

	public void render(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red,
			float green, float blue, float alpha) {
		if (!ReflectionBuddy.ModelPartAccess.cubes.apply(modelPart).isEmpty() || !children.isEmpty()) {
			poseStack.pushPose();
			// Translate to pivot (pixels to blocks)
			float scale = 1.0F / 16.0F;
			poseStack.translate(this.x * scale, this.y * scale, this.z * scale);
			// Apply rotations (X, Y, Z order, around pivot)
			if (this.xRot != 0)
				poseStack.mulPose(XP.rotation(this.xRot));
			if (this.yRot != 0)
				poseStack.mulPose(YP.rotation(this.yRot));
			if (this.zRot != 0)
				poseStack.mulPose(ZP.rotation(this.zRot));

			// Render cubes (origin is relative to pivot, in pixels)
			modelPart.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

			// Render children (inherit parent transformations)
			for (JavaJSONRenderer child : children) {
				child.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
			}

			poseStack.popPose();
		} else {
			// TODO In debug logging (when i implement that) add something here like this:
			// LOGGER.warn("Skipping render:
			// ModelPart is empty and no children present");
		}
	}

	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setRotation(float xRot, float yRot, float zRot) {
		this.xRot = xRot;
		this.yRot = yRot;
		this.zRot = zRot;
	}
}
