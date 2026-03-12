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

	@Unique private static final Logger throughTimeAndSpace$LOGGER = LogManager
			.getLogger("TTS$SeamlessTele#ClientPacketListener");

	@Unique private boolean tts$suppressLoadingScreen = false;

	@Inject(method = "handleRespawn", at = @At("HEAD"))
	private void tts$onHandleRespawnHead(ClientboundRespawnPacket packet, CallbackInfo ci) {
		boolean pending = ClientSeamlessTeleportState.isSeamlessPending();
		throughTimeAndSpace$LOGGER.info("[SMLS] handleRespawn HEAD — isSeamlessPending={}", pending);
		if (pending) {
			tts$suppressLoadingScreen = true;
			ClientSeamlessTeleportState.clearPending();
			ClientSeamlessTeleportState.setSuppressingLoadingScreen(true);
		}
	}

	@Inject(method = "handleRespawn", at = @At("RETURN"))
	private void tts$onHandleRespawnReturn(ClientboundRespawnPacket packet, CallbackInfo ci) {
		throughTimeAndSpace$LOGGER.info("[SMLS] handleRespawn RETURN — suppressed={}", tts$suppressLoadingScreen);
		tts$suppressLoadingScreen = false;
		ClientSeamlessTeleportState.setSuppressingLoadingScreen(false);
	}
}