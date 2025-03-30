package com.code.tama.mtm.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.mtm.client.Sounds;
import net.minecraft.sounds.SoundEvent;

public class SmithFlightLoopSound extends AbstractFlightSound {
    public int GetLength() {
        return 40;
    }

    public SoundEvent GetSound() {
        return Sounds.TARDIS_FLIGHT_LOOP.get();
    }
}