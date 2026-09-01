/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

/**
 * Drives a single {@link AbstractFlightSound} to completion (or, if
 * {@code loop} is set, indefinitely until stopped), broadcasting it as a
 * real server-authoritative sound via {@link Level#playSound}. This is the
 * ONLY place flight-stage sound should be triggered from on the server -
 * never call {@link AbstractFlightSound#Play} / {@code PlayLooped} directly
 * from server-executed code, since those go through the client-only
 * {@code Minecraft.getInstance()} singleton and will NPE on a dedicated
 * server.
 */
public class FlightSoundThread {

	/**
	 * Identifies a sound "slot" uniquely across every TARDIS interior dimension.
	 * The previous version keyed purely off BlockPos, which meant two different
	 * TARDISes using the same reference position (e.g. both at BlockPos.ZERO)
	 * would incorrectly block each other's flight sounds.
	 */
	private record SoundKey(ResourceKey<Level> dimension, BlockPos pos) {
	}

	private static final Map<SoundKey, FlightSoundThread> activeSounds = new ConcurrentHashMap<>();
	private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(4);

	private volatile boolean shouldStop = false;
	private long lastGameTime = -1;
	private int ticks = 0;
	private ScheduledFuture<?> future;

	private final Level level;
	private final BlockPos blockPos;
	private final AbstractFlightSound sound;
	private final boolean loop;
	private final SoundKey key;

	public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound) {
		this(level, blockPos, sound, false);
	}

	/**
	 * @param loop
	 *            If true, the sound replays indefinitely (for the flight loop)
	 *            instead of finishing after {@link AbstractFlightSound#GetLength()}
	 *            ticks. A looping sound only reports {@code IsFinished() == true}
	 *            once explicitly stopped via {@link #stop()} or
	 *            {@link #stop(Level, BlockPos)}.
	 */
	public FlightSoundThread(Level level, BlockPos blockPos, AbstractFlightSound sound, boolean loop) {
		this.level = level;
		this.blockPos = blockPos;
		this.sound = sound;
		this.loop = loop;
		this.key = new SoundKey(level.dimension(), blockPos);
	}

	public boolean start() {
		// Prevent duplicate sounds occupying the same slot in the same dimension
		if (activeSounds.putIfAbsent(key, this) != null) {
			return false;
		}

		this.future = executor.scheduleAtFixedRate(this::tick, 0, 50, TimeUnit.MILLISECONDS);
		return true;
	}

	public void stop() {
		shouldStop = true;
	}

	/**
	 * Immediately stops and frees whatever flight sound currently occupies the
	 * given dimension/position slot, if any. Safe to call even if nothing is
	 * playing there. This is synchronous (unlike the instance {@link #stop()}),
	 * so the slot is guaranteed free by the time this returns - important when a
	 * new sound needs to claim the same slot right away (e.g. the flight loop
	 * needs to be gone before the landing sound starts).
	 */
	public static void stop(Level level, BlockPos pos) {
		FlightSoundThread existing = activeSounds.remove(new SoundKey(level.dimension(), pos));
		if (existing != null) {
			existing.shouldStop = true;
			existing.sound.SetFinished(true);
			if (existing.future != null)
				existing.future.cancel(false);
		}
	}

	private void tick() {
		// level.playSound / level.getGameTime must run on the main server thread -
		// calling into Level from this executor's own thread pool isn't safe and
		// was a likely contributor to glitchy/overlapping playback.
		if (level.getServer() != null) {
			level.getServer().execute(this::tickOnServerThread);
		}
	}

	private void tickOnServerThread() {
		try {
			if (shouldStop) {
				sound.SetFinished(true);
				cleanup();
				return;
			}

			int length = sound.GetLength();
			if (length <= 0) {
				// A sound reporting a zero/negative length can never be measured
				// against - rather than instantly "finishing" (which is what caused
				// takeoff/landing to be inaudible and the loop to restart every
				// poll), play it once, don't mark it finished, and warn loudly so
				// the actual GetLength() implementation gets fixed. The stage this
				// belongs to will fall through via PhysicalStateManager's own
				// timeout rather than hang the game indefinitely.
				if (ticks == 0) {
					System.err.println("[TTS] " + sound.getClass().getSimpleName()
							+ ".GetLength() returned " + length + " - this must be a positive tick count.");
					level.playSound(null, blockPos, sound.GetSound(), SoundSource.BLOCKS);
					ticks = 1;
				}
				return;
			}

			if (ticks == 0) {
				level.playSound(null, blockPos, sound.GetSound(), SoundSource.BLOCKS);
			}

			long currentGameTime = level.getGameTime();
			if (lastGameTime < 0) {
				// First real tick - just record the baseline, don't count it yet.
				lastGameTime = currentGameTime;
				return;
			}

			if (currentGameTime != lastGameTime) {
				ticks++;
				lastGameTime = currentGameTime;

				if (ticks >= length) {
					if (loop) {
						// Loop back around and replay rather than finishing
						ticks = 0;
					} else {
						sound.SetFinished(true);
						cleanup();
					}
				}
			}
		} catch (Exception e) {
			sound.SetFinished(true);
			cleanup();
			e.printStackTrace();
		}
	}

	private void cleanup() {
		shouldStop = true;
		activeSounds.remove(key);
		if (future != null)
			future.cancel(false);
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