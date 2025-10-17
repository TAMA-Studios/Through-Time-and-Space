/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;
import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.SmithFlightLoopSound;
import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.SmithLandingSound;
import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.SmithTakeoffSound;

public class SmithSoundScheme extends AbstractSoundScheme {
	private final SmithLandingSound landSound;
	private final SmithFlightLoopSound loopSound;
	private final SmithTakeoffSound takeoffSound;

	public SmithSoundScheme() {
		this.takeoffSound = new SmithTakeoffSound();
		this.loopSound = new SmithFlightLoopSound();
		this.landSound = new SmithLandingSound();
	}

	public AbstractFlightSound GetFlightLoop() {
		return this.loopSound;
	}

	public AbstractFlightSound GetLanding() {
		return this.landSound;
	}

	@Override
	public String GetName() {
		return "smith";
	}

	public AbstractFlightSound GetTakeoff() {
		return this.takeoffSound;
	}
}
