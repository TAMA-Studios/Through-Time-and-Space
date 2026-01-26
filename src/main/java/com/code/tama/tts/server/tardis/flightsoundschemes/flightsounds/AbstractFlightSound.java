/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.server.threads.FlightSoundThread;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public abstract class AbstractFlightSound {
	private boolean started = false;
	private boolean finished = false;
	private FlightSoundThread soundThread;
	private long startedTime = 0;

	public abstract int GetLength();

	public abstract SoundEvent GetSound();

	public boolean IsFinished() {
		return this.finished;
	}

	public boolean IsStarted() {
		return this.started;
	}

	/**
	 * Start playing the sound immediately. If already playing, does nothing.
	 */
	public void Play(Level level, BlockPos blockPos) {
		if (this.started) {
			return;
		}

		this.started = true;
		this.finished = false;
		this.startedTime = level.getGameTime();
		startPlayback(level, blockPos);
	}

	/**
	 * Play the sound once, and automatically restart it when finished. Useful for
	 * looping sounds like flight loops.
	 */
	public void PlayLooped(Level level, BlockPos blockPos) {
		long currentTime = level.getGameTime();

		// Initialize start time on first call
		if (this.startedTime == 0) {
			this.startedTime = currentTime;
			this.started = true;
			startPlayback(level, blockPos);
			return;
		}

		// Check if sound has finished its duration
		if (currentTime - this.startedTime >= this.GetLength()) {
			// Reset and restart
			this.startedTime = currentTime;
			this.finished = false;
			startPlayback(level, blockPos);
		}
	}

	/**
	 * Stop the currently playing sound.
	 */
	public void Stop() {
		if (this.soundThread != null) {
			this.soundThread.stop();
			this.soundThread = null;
		}
		this.started = false;
		this.finished = true;
		this.startedTime = 0;
	}

	/**
	 * Internal: Start the actual playback thread.
	 */
	private void startPlayback(Level level, BlockPos blockPos) {
		// Stop any existing thread
		if (this.soundThread != null) {
			this.soundThread.stop();
		}

		// Create and start new thread
		this.soundThread = new FlightSoundThread(level, blockPos, this);
		boolean started = this.soundThread.start();

		if (!started) {
			// Another sound is already playing at this location
			this.soundThread = null;
			this.started = false;
		}
	}

	/**
	 * Called by FlightSoundThread when the sound finishes.
	 */
	public void SetFinished(boolean isFinished) {
		this.finished = isFinished;
		if (isFinished) {
			this.startedTime = 0;
			this.soundThread = null;
		}
	}

	/**
	 * Get the current playback state.
	 */
	public boolean IsPlaying() {
		return this.started && !this.finished && this.soundThread != null && this.soundThread.IsRunning();
	}
}