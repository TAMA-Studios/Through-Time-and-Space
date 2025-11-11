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

public class SonicModeRegistry {
	public static final ResourceKey<Registry<SonicMode>> SONIC_MODE_REGISTRY_KEY = ResourceKey
			.createRegistryKey(new ResourceLocation(MODID, "sonic_mode"));

	public static final DeferredRegister<SonicMode> SONIC_MODE = DeferredRegister.create(SONIC_MODE_REGISTRY_KEY,
			MODID);

	public static final RegistryObject<SonicBlockMode> BLOCKS = SONIC_MODE.register("blocks", SonicBlockMode::new);
	public static final RegistryObject<SonicEntityMode> ENTITY = SONIC_MODE.register("entity", SonicEntityMode::new);
	public static final RegistryObject<RedstoneSonicMode> REDSTONE = SONIC_MODE.register("redstone",
			RedstoneSonicMode::new);
	public static final RegistryObject<SonicBuilderMode> BUILDER = SONIC_MODE.register("builder",
			SonicBuilderMode::new);

	public static void register(IEventBus modEventBus) {
		SONIC_MODE.makeRegistry(() -> new RegistryBuilder<SonicMode>().hasTags().disableSaving().disableSync());
		SONIC_MODE.register(modEventBus);
	}

	@SuppressWarnings("unchecked")
	public static RegistryObject<SonicMode> getFromOrdinal(int ordinal) {
		if (ordinal >= SONIC_MODE.getEntries().size())
			return (RegistryObject<SonicMode>) SONIC_MODE.getEntries().toArray()[0];
		return (RegistryObject<SonicMode>) SonicModeRegistry.SONIC_MODE.getEntries().toArray()[ordinal];
	}

	@SuppressWarnings("unchecked")
	public static int ordinal(SonicMode mode) {
		for (int i = 0; i < SONIC_MODE.getEntries().size(); i++) {
			RegistryObject<SonicMode> next = (RegistryObject<SonicMode>) SONIC_MODE.getEntries().toArray()[i];
			if (next.get().equals(mode))
				return i;
		}
		return 0;
	}
}
