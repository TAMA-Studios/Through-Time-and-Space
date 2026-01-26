/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class FlightSoundThread {
	private static final Map<BlockPos, FlightSoundThread> activeSounds = new HashMap<>();
	private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(4);

	private volatile boolean shouldStop = false;
	private long lastGameTime = 0;
	private int ticks = 0;

	private final Level level;
	private final BlockPos blockPos;
	private final AbstractFlightSound sound;

	public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
		this.level = level;
		this.blockPos = blockPos;
		this.sound = sound;
	}

	public boolean start() {
		// Prevent duplicate sounds at the same location
		if (activeSounds.containsKey(blockPos)) {
			return false;
		}

		activeSounds.put(blockPos, this);
		executor.scheduleAtFixedRate(this::tick, 0, 50, TimeUnit.MILLISECONDS);
		return true;
	}

	public void stop() {
		shouldStop = true;
	}

	private void tick() {
		try {
			if (shouldStop || sound.IsFinished()) {
				cleanup();
				return;
			}

			if (ticks == 0) {
				level.playSound(null, blockPos, sound.GetSound(), SoundSource.BLOCKS);
			}

			long currentGameTime = level.getGameTime();
			if (currentGameTime != lastGameTime) {
				ticks++;
				lastGameTime = currentGameTime;

				if (ticks >= sound.GetLength()) {
					sound.SetFinished(true);
					cleanup();
				}
			}
		} catch (Exception e) {
			cleanup();
			e.printStackTrace();
		}
	}

	private void cleanup() {
		shouldStop = true;
		activeSounds.remove(blockPos);
	}

	public boolean IsRunning() {
		return !shouldStop && !sound.IsFinished();
	}

	public static void shutdownAll() {
		executor.shutdown();
		try {
			if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}