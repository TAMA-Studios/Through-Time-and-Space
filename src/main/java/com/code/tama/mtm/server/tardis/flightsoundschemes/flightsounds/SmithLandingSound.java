package com.code.tama.mtm.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.mtm.client.MTMSounds;
import net.minecraft.sounds.SoundEvent;

public class SmithLandingSound extends AbstractFlightSound {
    public int GetLength() {
        return 250;
    }

    public SoundEvent GetSound() {
        return MTMSounds.TARDIS_LANDING.get();
    }
}