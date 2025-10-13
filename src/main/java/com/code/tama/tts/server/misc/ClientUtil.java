/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

public class ClientUtil {
  public static Entity GetEntityClientIsLookingAt() {
    return Minecraft.getInstance().crosshairPickEntity;
  }
}
