package com.code.tama.tts.server.registries;

import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Register a callback for when an entry is added to any {@link TTSRegistrate} instance
 */
public class TTSRegistrateRegistrationCallback {
	public static <R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, ResourceLocation id, NonNullConsumer<? super T> callback) {
		TTSRegistrateRegistrationCallbackImpl.<R, T>register(registry, id, callback);
	}

	/**
	 * Provide a {@link TTSRegistrate} instance to be used by the API.
	 * Instances created by {@link TTSRegistrate#create(String)} will automatically be registered.
	 * It is illegal to call this method more than once for the same mod ID.
	 */
	public static void provideRegistrate(TTSRegistrate registrate) {
		TTSRegistrateRegistrationCallbackImpl.provideRegistrate(registrate);
	}

	private TTSRegistrateRegistrationCallback() {
		throw new AssertionError("This class should not be instantiated");
	}
}