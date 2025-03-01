package com.code.tama.mtm.TARDIS.FlightSoundSchemes;

import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.AbstractFlightSound;

public abstract class AbstractSoundScheme {
    public abstract AbstractFlightSound GetTakeoff();
    public abstract AbstractFlightSound GetLanding();
    public abstract AbstractFlightSound GetFlightLoop();
}
