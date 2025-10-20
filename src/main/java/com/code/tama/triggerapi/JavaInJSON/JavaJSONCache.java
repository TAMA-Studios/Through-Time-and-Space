/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.JavaInJSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JavaJSONCache extends SimplePreparableReloadListener<Void> {

	protected static Map<ResourceLocation, JavaJSONParsed> bakedCache = new HashMap<>();
	protected static Map<IUseJavaJSON, ResourceLocation> reloadableModels = new HashMap<>();
	protected static List<ResourceLocation> unbakedCache = new ArrayList<>();

	protected static void init() {
		// Handled in event
	}

	public static void register(IUseJavaJSON part, ResourceLocation model) {
		JavaJSONCache.reloadableModels.put(part, model);
		part.reload();
	}

	@SubscribeEvent
	public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
		event.registerReloadListener(new JavaJSONCache());
	}

	@Override
	protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
		JavaJSONCache.bakedCache.clear();

		for (ResourceLocation location : JavaJSONCache.unbakedCache) {
			JavaJSONParser.loadModel(location);
		}

		for (Map.Entry<ResourceLocation, JavaJSONParsed> entry : JavaJSONCache.bakedCache.entrySet()) {
			entry.getValue().load();
		}
		for (Map.Entry<IUseJavaJSON, ResourceLocation> entry : JavaJSONCache.reloadableModels.entrySet()) {
			entry.getKey().reload();
		}
	}

	@Override
	protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
		return null;
	}
}
