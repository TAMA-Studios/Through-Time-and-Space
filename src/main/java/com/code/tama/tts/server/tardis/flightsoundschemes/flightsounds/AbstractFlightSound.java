/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.core.misc.LoopingSound;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public abstract class AbstractFlightSound {
	private boolean started = false;
	private boolean finished = false;
	private LoopingSound loopey;

	public abstract int GetLength();
	public abstract SoundEvent GetSound();

	public boolean IsFinished() {
		return this.finished;
	}
	public boolean IsStarted() {
		return this.started;
	}
	public boolean IsPlaying() {
		return this.started && !this.finished && this.loopey != null;
	}

	/**
	 * Start playing the sound immediately. If already playing, does nothing.
	 */
	public void Play(Level level, BlockPos blockPos) {
		if (this.started) { // removed || true
			return;
		}
		this.started = true;
		this.finished = false;

		Minecraft.getInstance().tell(() -> {
			level.playSound(null, blockPos, this.GetSound(), SoundSource.BLOCKS, 1f, 1f);
		});
	}

	/**
	 * Play the sound looped. Safe to call every tick — only starts once.
	 */
	public void PlayLooped(Level level, BlockPos blockPos) {
		Minecraft.getInstance().tell(() -> {
			if (loopey == null && !started) {
				loopey = new LoopingSound(this.GetSound());
				Minecraft.getInstance().getSoundManager().play(loopey);
				started = true;
				finished = false;
			}
		});
	}

	/**
	 * Stop the currently playing sound.
	 */
	public void Stop() {
		if (this.loopey != null) {
			this.loopey.Stop();
			this.loopey = null;
		}
		this.started = false;
		this.finished = true;
	}

	/**
	 * Called externally when the sound finishes naturally (non-looping).
	 */
	public void SetFinished(boolean isFinished) {
		this.finished = isFinished;
		if (isFinished) {
			this.loopey = null;
			this.started = false;
		}
	}
}