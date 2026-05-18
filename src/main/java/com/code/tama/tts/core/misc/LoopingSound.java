/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class LoopingSound extends AbstractTickableSoundInstance {

	private boolean stopped = false;

	public void Stop() {
		this.stopped = true;
		this.stop();
	}

	public LoopingSound(SoundEvent event) {
		super(event, SoundSource.MUSIC, SoundInstance.createUnseededRandom());

		this.looping = true;
		this.delay = 0;
		this.volume = 1.0F;
		this.relative = true;
	}

	public void setVolume(float vol) {
		this.volume = vol;
	}

	@Override
	public void tick() {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player == null || mc.level == null || this.stopped) {
			this.stop();
		}
	}
}