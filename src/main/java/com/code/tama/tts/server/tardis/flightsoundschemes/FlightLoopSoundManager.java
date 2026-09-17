/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.tardis.flightsoundschemes;

import com.code.tama.tts.core.misc.LoopingSound;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Owns the client's currently-playing flight loop {@link LoopingSound}, if any.
 */
@OnlyIn(Dist.CLIENT)
public class FlightLoopSoundManager {

	private static LoopingSound current;

	/**
	 * Starts the flight loop, if it isn't already playing.
	 */
	public static void start(SoundEvent sound) {
		if (current != null)
			return;

		current = new LoopingSound(sound);
		Minecraft.getInstance().getSoundManager().play(current);
	}

	/** Stops the flight loop, if one is currently playing. */
	public static void stop() {
		if (current == null)
			return;

		current.Stop();
		current = null;
	}

	/** True if a flight loop is currently registered as playing. */
	public static boolean isPlaying() {
		return current != null;
	}
}