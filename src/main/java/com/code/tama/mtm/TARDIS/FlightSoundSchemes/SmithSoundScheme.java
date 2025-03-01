package com.code.tama.mtm.TARDIS.FlightSoundSchemes;

import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.AbstractFlightSound;
import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.SmithFlightLoopSound;
import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.SmithLandingSound;
import com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds.SmithTakeoffSound;

public class SmithSoundScheme extends AbstractSoundScheme {
    private final SmithTakeoffSound takeoffSound;
    private final SmithFlightLoopSound loopSound;
    private final SmithLandingSound landSound;

    public SmithSoundScheme() {
        this.takeoffSound = new SmithTakeoffSound();
        this.loopSound = new SmithFlightLoopSound();
        this.landSound = new SmithLandingSound();
    }

    public AbstractFlightSound GetTakeoff() {
        return this.takeoffSound;
    }
    public AbstractFlightSound GetLanding() {
        return this.landSound;
    }
    public AbstractFlightSound GetFlightLoop() {
        return this.loopSound;
    }
}
