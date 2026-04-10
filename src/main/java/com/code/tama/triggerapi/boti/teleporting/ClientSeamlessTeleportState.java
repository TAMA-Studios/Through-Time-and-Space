/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;

/**
 * Client-side state for seamless cross-dimension teleports.
 *
 * Flow: 1. PREPARE packet (uuid) arrives → openStagingBuffer +
 * setExpectingSeamlessRespawn 2. ClientboundRespawnPacket fires → if expecting,
 * hold it; else if pending, suppress normally 3. COMMIT packet () arrives →
 * setPending, clearExpecting, replayHeldRespawnIfAny 4. handleRespawn runs for
 * real → pushSuppression so setScreen suppresses ReceivingLevelScreen 5.
 * handleRespawn returns → popSuppression
 */
@OnlyIn(Dist.CLIENT)
public final class ClientSeamlessTeleportState {

	private static final Logger LOGGER = LogManager.getLogger("TTS$ClientSeamlessTeleportState");

	// -------------------------------------------------------------------------
	// Pending flag — set by COMMIT packet, cleared when handleRespawn sees it
	// -------------------------------------------------------------------------

	private static volatile boolean pending = false;

	public static void setPending() {
		pending = true;
	}
	public static boolean isSeamlessPending() {
		return pending;
	}
	public static void clearPending() {
		pending = false;
	}

	// -------------------------------------------------------------------------
	// Expecting flag — set by PREPARE packet, tells the mixin to HOLD the next
	// respawn packet rather than letting it through unsuppressed
	// -------------------------------------------------------------------------

	private static volatile boolean expectingSeamlessRespawn = false;

	public static void setExpectingSeamlessRespawn() {
		expectingSeamlessRespawn = true;
	}
	public static void clearExpectingSeamlessRespawn() {
		expectingSeamlessRespawn = false;
	}
	public static boolean isExpectingSeamlessRespawn() {
		return expectingSeamlessRespawn;
	}

	// -------------------------------------------------------------------------
	// Held respawn — populated when the respawn packet arrives before the COMMIT
	// -------------------------------------------------------------------------

	@Nullable private static volatile ClientboundRespawnPacket heldRespawn = null;

	public static void holdRespawn(ClientboundRespawnPacket packet) {
		LOGGER.info("[SMLS] Holding respawn packet until COMMIT arrives");
		heldRespawn = packet;
	}

	/**
	 * Called by the COMMIT handler on the main client thread. If a respawn packet
	 * was held, arm suppression and replay it now.
	 */
	public static void replayHeldRespawnIfAny() {
		ClientboundRespawnPacket held = heldRespawn;
		if (held == null)
			return;
		heldRespawn = null;

		Minecraft mc = Minecraft.getInstance();
		if (mc.getConnection() == null) {
			LOGGER.warn("[SMLS] replayHeldRespawnIfAny — no connection, dropping held packet");
			return;
		}

		LOGGER.info("[SMLS] Replaying held respawn packet now that COMMIT arrived");
		// Do NOT push suppression here — the HEAD inject sees pending=true and
		// pushes exactly once. Pushing here too would double-push with one pop,
		// permanently leaking suppressDepth by 1 on every held-path teleport.
		mc.getConnection().handleRespawn(held);
		// popSuppression is called by the RETURN inject in MixinClientPacketListener
	}

	// -------------------------------------------------------------------------
	// Suppression counter — incremented when handleRespawn is ours, decremented
	// when it returns. MixinMinecraft checks this to suppress ReceivingLevelScreen.
	// -------------------------------------------------------------------------

	private static int suppressDepth = 0;

	public static void pushSuppression() {
		suppressDepth++;
		LOGGER.info("[SMLS] pushSuppression → depth={}", suppressDepth);
	}

	public static void popSuppression() {
		suppressDepth = Math.max(0, suppressDepth - 1);
		LOGGER.info("[SMLS] popSuppression → depth={}", suppressDepth);
	}

	public static boolean isSuppressingLoadingScreen() {
		return suppressDepth > 0;
	}

	// -------------------------------------------------------------------------
	// Staging buffer — geometry pre-gathered before the teleport
	// -------------------------------------------------------------------------

	@Nullable private static volatile UUID currentTeleportId = null;

	private static final Object STAGE_LOCK = new Object();

	@Nullable private static List<BotiBlockContainer> stagedContainers = null;

	private static final AtomicInteger stagedTotal = new AtomicInteger(0);
	private static final AtomicInteger stagedReceived = new AtomicInteger(0);

	/**
	 * Opens (or resets) the staging buffer for a new teleport UUID. Called when
	 * SeamlessPreparePacket(uuid) arrives.
	 */
	public static void openStagingBuffer(UUID teleportId) {
		currentTeleportId = teleportId;
		synchronized (STAGE_LOCK) {
			stagedContainers = null;
		}
		stagedTotal.set(0);
		stagedReceived.set(0);
	}

	/**
	 * Adds a geometry batch. Drops silently if UUID doesn't match.
	 */
	public static void stageContainers(UUID teleportId, List<BotiBlockContainer> batch, int index, int total) {
		if (!teleportId.equals(currentTeleportId))
			return;
		synchronized (STAGE_LOCK) {
			if (stagedContainers == null)
				stagedContainers = new ArrayList<>();
			stagedContainers.addAll(batch);
		}
		stagedTotal.set(total);
		stagedReceived.incrementAndGet();
	}

	public static boolean isStagingComplete() {
		int total = stagedTotal.get();
		return total > 0 && stagedReceived.get() >= total;
	}

	@Nullable public static List<BotiBlockContainer> consumeStagedContainers() {
		synchronized (STAGE_LOCK) {
			List<BotiBlockContainer> result = stagedContainers;
			stagedContainers = null;
			return result;
		}
	}
}