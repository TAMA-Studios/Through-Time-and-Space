/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.server.threads.FlightSoundThread;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public abstract class AbstractFlightSound {
	boolean Finished;
	private boolean Started;
	private FlightSoundThread flightSoundThread;
	private long startedTime = 0;

	public abstract int GetLength();

	public abstract SoundEvent GetSound();

	public boolean IsFinished() {
		return this.Finished;
	}

	public boolean IsStarted() {
		return this.Started;
	}

	public void Play(Level level, BlockPos blockPos) {
		this.Started = true;
		this.flightSoundThread = null;

		Thread(level, blockPos);
	}

	public void PlayIfFinished(Level level, BlockPos blockPos) {
		// if(this.Finished) {
		// this.Started = false;
		// this.Finished = false;
		// }
		// if (this.Started) return;
		// this.Started = true;
		// Thread(level, blockPos);

		if (this.startedTime == 0) {
			assert Minecraft.getInstance().level != null;
			this.startedTime = level.getGameTime();
		}
		assert level != null;
		if (level.getGameTime() - this.startedTime >= this.GetLength()) {
			this.Finished = false;
			this.startedTime = 0;
			// level.playSound(null, blockPos, this.GetSound(), SoundSource.BLOCKS);
			this.flightSoundThread = null;
			Thread(level, blockPos);
			// Minecraft.getInstance().player.playSound(this.GetSound());
		}
	}

	public void SetFinished(boolean IsFinished) {
		this.Finished = IsFinished;
		this.Started = !IsFinished;
	}

	public void Thread(Level level, BlockPos pos) {
		if (this.flightSoundThread == null)
			this.flightSoundThread = new FlightSoundThread(level, pos, this);
		if (this.flightSoundThread.isAlive())
			this.flightSoundThread.run();
		else
			this.flightSoundThread.start();
	}
}
