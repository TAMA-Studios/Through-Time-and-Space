// (C) TAMA Studios 2025
package com.code.tama.triggerapi.helpers;

import net.minecraft.world.level.Level;

public class GravityHelper {

    /**
     * Returns the gravity strength for a given level.
     * Default Minecraft gravity = 0.08F
     * TODO: Datapack gravity values, take the dimension RL and a float mavity
     */
    public static float getGravity(Level level) {
        if (level.dimension().location().getPath().contains("moon")) { // moon
            return 0.02F;
        }
        if (level.dimension().location().getPath().contains("heavyworld")) {
            return 0.15F;
        }

        return 0.08F; // vanilla gravity
    }
}
