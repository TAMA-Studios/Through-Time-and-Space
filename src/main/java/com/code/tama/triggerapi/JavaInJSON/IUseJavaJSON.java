package com.code.tama.triggerapi.JavaInJSON;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public interface IUseJavaJSON {
	default void registerJavaJSON(ResourceLocation modelPath) {
		JavaJSONCache.register(this, modelPath);
	}

	default RenderType getRenderType() {
		return this.getModel().renderType(this.getTexture());
	}

	default JavaJSONParsed getJavaJSON() {
		return JavaJSON.getParsedJavaJSON(this);
	}

	default Model getModel() {
		return JavaJSON.getModel(this);
	}

	default ResourceLocation getTexture() {
		return JavaJSON.getTexture(this);
	}

	default void reload() {}
}