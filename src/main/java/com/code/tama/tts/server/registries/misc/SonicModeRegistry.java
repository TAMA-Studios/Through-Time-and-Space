/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.misc;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.sonic.*;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class SonicModeRegistry {
	public static final RegistryObject<SonicBlockMode> BLOCKS = SONIC_MODE.register("blocks", SonicBlockMode::new);

	public static final RegistryObject<SonicBuilderMode> BUILDER = SONIC_MODE.register("builder",
			SonicBuilderMode::new);

	public static final RegistryObject<SonicEntityMode> ENTITY = SONIC_MODE.register("entity", SonicEntityMode::new);
	public static final RegistryObject<RedstoneSonicMode> REDSTONE = SONIC_MODE.register("redstone",
			RedstoneSonicMode::new);
	public static final DeferredRegister<SonicMode> SONIC_MODE = DeferredRegister.create(SONIC_MODE_REGISTRY_KEY,
			MODID);
	public static final ResourceKey<Registry<SonicMode>> SONIC_MODE_REGISTRY_KEY = ResourceKey
			.createRegistryKey(new ResourceLocation(MODID, "sonic_mode"));

	public static void register(IEventBus modEventBus) {
		SONIC_MODE.makeRegistry(() -> new RegistryBuilder<SonicMode>().hasTags().disableSaving().disableSync());
		SONIC_MODE.register(modEventBus);
	}
}
