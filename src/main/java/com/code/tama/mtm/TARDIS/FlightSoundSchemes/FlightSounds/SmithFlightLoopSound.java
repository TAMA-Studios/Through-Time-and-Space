package com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds;

import com.code.tama.mtm.Client.Sounds;
import net.minecraft.sounds.SoundEvent;

public class SmithFlightLoopSound extends AbstractFlightSound {
    public int GetLength() {
        return 40;
    }

    public SoundEvent GetSound() {
        return Sounds.TARDIS_FLIGHT_LOOP.get();
    }
}