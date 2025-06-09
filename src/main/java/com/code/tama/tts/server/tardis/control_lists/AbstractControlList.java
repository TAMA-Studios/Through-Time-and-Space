/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

public abstract class AbstractControlList {
    public abstract HashMap<Vec3, Vec3> GetPositionSizeMap();
}
