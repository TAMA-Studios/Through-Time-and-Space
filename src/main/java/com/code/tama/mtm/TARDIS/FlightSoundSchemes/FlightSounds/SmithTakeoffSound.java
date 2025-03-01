package com.code.tama.mtm.TARDIS.FlightSoundSchemes.FlightSounds;

import com.code.tama.mtm.Client.Sounds;
import net.minecraft.sounds.SoundEvent;

public class SmithTakeoffSound extends AbstractFlightSound {
    public int GetLength() {
        return 250;
    }

    public SoundEvent GetSound() {
        return Sounds.TARDIS_TAKEOFF.get();
    }
}