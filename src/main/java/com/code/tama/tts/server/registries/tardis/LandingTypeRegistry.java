/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import com.code.tama.tts.server.misc.containers.LandingType;
import com.code.tama.tts.server.misc.containers.LandingTypeUP;

public class LandingTypeRegistry {
    public static final LandingType UP;

    static {
        UP = new LandingTypeUP();
    }
}
