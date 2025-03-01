package com.code.tama.mtm.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class ClientUtil {
    public static Entity GetEntityClientIsLookingAt() {
        return Minecraft.getInstance().crosshairPickEntity;
    }
}
