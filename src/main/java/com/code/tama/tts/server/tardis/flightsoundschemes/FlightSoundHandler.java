/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.code.tama.tts.TTSMod;

public class FlightSoundHandler {
	public static AbstractSoundScheme GetByID(int ID) {
		AtomicReference<AbstractSoundScheme> id = new AtomicReference<>();
		AtomicInteger Iterator = new AtomicInteger();

		TTSMod.SoundSchemes.stream().toList().forEach(scheme -> {
			if (ID != Iterator.get())
				return;
			id.set(scheme);
		});
		return id.get();
	}

	public static int GetID(AbstractSoundScheme SoundScheme) {
		AtomicInteger id = new AtomicInteger();
		AtomicBoolean gotID = new AtomicBoolean();

		TTSMod.SoundSchemes.stream().toList().forEach(scheme -> { // .getEntries().forEach(scheme -> {
			if (gotID.get())
				return;

			if (!scheme.equals(SoundScheme)) {
				id.getAndIncrement();
			} else
				return;
		});
		return 0;
	}
}
