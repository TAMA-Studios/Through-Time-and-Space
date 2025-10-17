/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class JavaJSONParsed {

	private ResourceLocation location;
	private ModelInformation modelInfo = new ModelInformation();

	public JavaJSONParsed(ResourceLocation path) {
		this.location = path;
	}

	public ModelInformation getModelInfo() {
		return modelInfo;
	}

	public JavaJSONRenderer getPart(String groupName) {
		return getModelInfo().getModel().getPart(groupName);
	}

	public JavaJSONParsed load() {
		modelInfo = JavaJSONParser.getModelInfo(location);

		if (modelInfo.getModel() != null)
			modelInfo.getModel().model = this;

		return this;
	}

	public static class ModelInformation {
		private ResourceLocation alphaMap;
		private ResourceLocation lightMap;
		private JavaJSONModel model = new JavaJSONModel();
		private ResourceLocation texture = MissingTextureAtlasSprite.getLocation();

		public ModelInformation() {
			this(null, null, null, null);
		}

		public ModelInformation(JavaJSONModel model, ResourceLocation tex, ResourceLocation lightMap,
				ResourceLocation alphaMap) {
			if (model != null)
				this.model = model;
			if (tex != null)
				this.texture = tex;
			if (lightMap != null)
				this.lightMap = lightMap;
			if (alphaMap != null)
				this.alphaMap = alphaMap;
		}

		public ResourceLocation getAlphaMap() {
			return alphaMap;
		}

		public ResourceLocation getLightMap() {
			return lightMap;
		}

		public JavaJSONModel getModel() {
			return model;
		}

		public ResourceLocation getTexture() {
			return texture;
		}
	}
}
