/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public interface IUseJavaJSON {
    default JavaJSONParsed getJavaJSON() {
        return JavaJSON.getParsedJavaJSON(this);
    }

    default Model getModel() {
        return JavaJSON.getModel(this);
    }

    default RenderType getRenderType() {
        return this.getModel().renderType(this.getTexture());
    }

    default RenderType getEmmisiveRenderType() {
        return this.getModel().renderType(this.getLightMap());
    }

    default ResourceLocation getTexture() {
        return JavaJSON.getTexture(this);
    }

    default ResourceLocation getLightMap() {
        return JavaJSON.getLightMap(this);
    }

    default ResourceLocation getAlphaMap() {
        return JavaJSON.getAlphaMap(this);
    }

    default void registerJavaJSON(ResourceLocation modelPath) {
        JavaJSONCache.register(this, modelPath);
    }

    default void reload() {}
}
