package com.code.tama.mtm.server.tardis.flightsoundschemes;

import com.code.tama.mtm.server.tardis.flightsoundschemes.flightsounds.AbstractFlightSound;

public abstract class AbstractSoundScheme {
    public abstract AbstractFlightSound GetTakeoff();
    public abstract AbstractFlightSound GetLanding();
    public abstract AbstractFlightSound GetFlightLoop();
}
