/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.tts.TTSMod;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTSParticles {
	public static final RegistryObject<SimpleParticleType> ELECTRIC_SPARK = PARTICLES.register("electric_spark",
			() -> new SimpleParticleType(true));

	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(ForgeRegistries.PARTICLE_TYPES, TTSMod.MODID);

	public static void register(IEventBus bus) {
		PARTICLES.register(bus);
	}
}
