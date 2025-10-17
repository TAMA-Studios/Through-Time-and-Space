/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class JavaJSON {
	public static ResourceLocation getAlphaMap(IUseJavaJSON part) {
		if (getParsedJavaJSON(part) == null)
			return null;
		return getParsedJavaJSON(part).getModelInfo().getAlphaMap();
	}

	public static ResourceLocation getLightMap(IUseJavaJSON part) {
		if (getParsedJavaJSON(part) == null)
			return null;
		return getParsedJavaJSON(part).getModelInfo().getLightMap();
	}

	public static Model getModel(IUseJavaJSON part) {
		if (getParsedJavaJSON(part) == null)
			return null;
		return getParsedJavaJSON(part).getModelInfo().getModel();
	}

	public static JavaJSONParsed getParsedJavaJSON(IUseJavaJSON part) {
		return JavaJSONParser.loadModel(JavaJSONCache.reloadableModels.get(part));
	}

	public static ResourceLocation getTexture(IUseJavaJSON part) {
		if (getParsedJavaJSON(part) == null)
			return null;
		return getParsedJavaJSON(part).getModelInfo().getTexture();
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(JavaJSONCache::init);
	}
}
