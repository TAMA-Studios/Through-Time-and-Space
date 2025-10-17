/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class ElectricSparkParticle extends TextureSheetParticle {
	private final SpriteSet sprites;

	protected ElectricSparkParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd,
			SpriteSet spriteSet) {
		super(level, x, y, z, xd, yd, zd);
		this.sprites = spriteSet;

		// Motion setup
		this.xd = xd;
		this.yd = yd;
		this.zd = zd;

		// Physics properties
		this.gravity = 0.6F;
		this.lifetime = 24 + this.random.nextInt(16);
		this.quadSize = 0.05F + this.random.nextFloat() * 0.05F;

		// Electric blue-white
		// this.rCol = 0.5F + this.random.nextFloat() * 0.5F;
		// this.gCol = 0.7F + this.random.nextFloat() * 0.3F;
		// this.bCol = 1.0F;

		this.alpha = 1F;
		this.setSpriteFromAge(spriteSet);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);

		// Fade out near the end
		float lifeRatio = (float) this.age / (float) this.lifetime;
		this.alpha = 1.0F - lifeRatio;

		// Slight flicker
		if (this.random.nextFloat() < 0.1F)
			this.quadSize *= 1.1F;
	}

	public static class Provider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
				double xd, double yd, double zd) {
			return new ElectricSparkParticle(level, x, y, z, xd, yd, zd, this.sprites);
		}
	}
}
