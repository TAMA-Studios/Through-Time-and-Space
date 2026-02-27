/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;

public class SeamlessTeleportState {
	private static final Map<UUID, SeamlessTeleportContext> PENDING = new HashMap<>();

	public static void setPending(ServerPlayer player, SeamlessTeleportContext ctx) {
		PENDING.put(player.getUUID(), ctx);
	}

	public static @Nullable SeamlessTeleportContext consumePending(ServerPlayer player) {
		return PENDING.remove(player.getUUID());
	}

	public static boolean isPending(ServerPlayer player) {
		return PENDING.containsKey(player.getUUID());
	}
}
