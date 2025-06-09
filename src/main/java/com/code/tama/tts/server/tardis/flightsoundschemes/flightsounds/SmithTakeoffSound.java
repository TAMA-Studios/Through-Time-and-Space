/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.client.TTSSounds;
import net.minecraft.sounds.SoundEvent;

public class SmithTakeoffSound extends AbstractFlightSound {
    public int GetLength() {
        return 250;
    }

    public SoundEvent GetSound() {
        return TTSSounds.TARDIS_TAKEOFF.get();
    }
}