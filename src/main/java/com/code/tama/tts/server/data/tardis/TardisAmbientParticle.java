/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.tardis;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;

public record TardisAmbientParticle(ParticleOptions particle, float probability) {
	public static final Codec<TardisAmbientParticle> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(ParticleTypes.CODEC.fieldOf("particle").forGetter(TardisAmbientParticle::particle),
					Codec.FLOAT.fieldOf("probability").forGetter(TardisAmbientParticle::probability))
			.apply(instance, TardisAmbientParticle::new));
}