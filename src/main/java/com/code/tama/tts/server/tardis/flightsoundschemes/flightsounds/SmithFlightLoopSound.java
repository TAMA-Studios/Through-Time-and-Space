/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flightsoundschemes.flightsounds;

import com.code.tama.tts.client.TTSSounds;
import net.minecraft.sounds.SoundEvent;

public class SmithFlightLoopSound extends AbstractFlightSound {
  public int GetLength() {
    return 71;
  }

  public SoundEvent GetSound() {
    return TTSSounds.TARDIS_FLIGHT_LOOP.get();
  }
}
