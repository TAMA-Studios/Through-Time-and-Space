/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientSeamlessTeleportState {

	private static boolean pending = false;

	public static void setPending() {
		pending = true;
	}

	public static boolean isSeamlessPending() {
		return pending;
	}

	public static void clearPending() {
		pending = false;
	}
}