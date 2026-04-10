/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {

	@Unique private static final Logger LOGGER = LogManager.getLogger("TTS$SeamlessTele#ClientPacketListener");

	// Whether THIS invocation of handleRespawn pushed suppression.
	// Instance field so each re-entrant call (Netty thread vs Render thread)
	// tracks its own push independently.
	@Unique private boolean tts$pushedSuppression = false;

	@Inject(method = "handleRespawn", at = @At("HEAD"), cancellable = true)
	private void tts$onHandleRespawnHead(ClientboundRespawnPacket packet, CallbackInfo ci) {
		boolean pending = ClientSeamlessTeleportState.isSeamlessPending();
		boolean expecting = ClientSeamlessTeleportState.isExpectingSeamlessRespawn();

		LOGGER.info("[SMLS] handleRespawn HEAD — pending={}, expecting={}, thread={}", pending, expecting,
				Thread.currentThread().getName());

		if (pending) {
			// COMMIT arrived before the respawn packet — normal fast path.
			// Only push suppression once; the Render thread is the one that
			// actually executes vanilla handleRespawn body and calls setScreen.
			ClientSeamlessTeleportState.pushSuppression();
			tts$pushedSuppression = true;
			ClientSeamlessTeleportState.clearPending();
			return;
		}

		if (expecting) {
			// Respawn arrived before COMMIT — hold it and cancel this call.
			// replayHeldRespawnIfAny() will re-invoke handleRespawn after
			// pushing suppression itself.
			ClientSeamlessTeleportState.holdRespawn(packet);
			ci.cancel();
			return;
		}

		// Not a seamless teleport — let vanilla run.
		tts$pushedSuppression = false;
	}

	@Inject(method = "handleRespawn", at = @At("RETURN"))
	private void tts$onHandleRespawnReturn(ClientboundRespawnPacket packet, CallbackInfo ci) {
		LOGGER.info("[SMLS] handleRespawn RETURN — pushedSuppression={}, thread={}", tts$pushedSuppression,
				Thread.currentThread().getName());
		if (tts$pushedSuppression) {
			ClientSeamlessTeleportState.popSuppression();
			tts$pushedSuppression = false;
		}
	}
}