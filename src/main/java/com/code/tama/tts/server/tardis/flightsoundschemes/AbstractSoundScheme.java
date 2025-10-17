/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes;

import com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;

public abstract class AbstractSoundScheme {
	public abstract AbstractFlightSound GetFlightLoop();

	public abstract AbstractFlightSound GetLanding();

	public abstract String GetName();

	public abstract AbstractFlightSound GetTakeoff();
}
