/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.misc.LandingType;
import com.code.tama.tts.server.misc.LandingTypeUP;

public class LandingTypeRegistry {
    public static final LandingType UP;

    static {
        UP = new LandingTypeUP();
    }
}
