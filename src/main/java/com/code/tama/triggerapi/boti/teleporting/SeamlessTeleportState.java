/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import java.util.*;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;

public class SeamlessTeleportState {

	/** Players whose changeDimension call should be intercepted by the mixin. */
	private static final Map<UUID, SeamlessTeleportContext> PENDING = new HashMap<>();

	/** Players for whom prepare() has finished a full geometry gather. */
	private static final Set<UUID> PREPARED = new HashSet<>();

	public static void setPending(ServerPlayer player, SeamlessTeleportContext ctx) {
		PENDING.put(player.getUUID(), ctx);
	}

	public static @Nullable SeamlessTeleportContext consumePending(ServerPlayer player) {
		return PENDING.remove(player.getUUID());
	}

	public static boolean isPending(ServerPlayer player) {
		return PENDING.containsKey(player.getUUID());
	}

	public static void markPrepared(ServerPlayer player) {
		PREPARED.add(player.getUUID());
	}

	public static boolean isPrepared(ServerPlayer player) {
		return PREPARED.contains(player.getUUID());
	}

	public static void clearPrepared(ServerPlayer player) {
		PREPARED.remove(player.getUUID());
	}
}