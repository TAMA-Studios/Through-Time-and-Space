/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID)
public class DatapackRegistry {
	private static final Map<ResourceLocation, AbstractDPLoader<?>> loaders = new HashMap<>();

	public static void addLoader(AbstractDPLoader<?> loader) {
		loaders.put(loader.id(), loader);
	}

	@SubscribeEvent
	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		loaders.forEach((i, l) -> {
			event.addListener(l);
		});
	}

	public static AbstractDPLoader<?> getLoader(ResourceLocation location) {
		if (loaders.containsKey(location))
			loaders.get(location);

		return null;
	}
}