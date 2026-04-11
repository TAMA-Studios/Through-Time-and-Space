/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;

import com.code.tama.triggerapi.boti.FakePortalLevelRegistry;
import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {

	@Unique private static final Logger LOGGER = LogManager.getLogger("TTS$SeamlessTele#ClientPacketListener");
	@Unique private boolean tts$pushedSuppression = false;

	// Shadow the level getter so we can read which dimension this listener thinks
	// it's in
	@Shadow
	public abstract ClientLevel getLevel();

	@Inject(method = "handleRespawn", at = @At("HEAD"), cancellable = true)
	private void tts$onHandleRespawnHead(ClientboundRespawnPacket packet, CallbackInfo ci) {
		boolean pending = ClientSeamlessTeleportState.isSeamlessPending();
		boolean expecting = ClientSeamlessTeleportState.isExpectingSeamlessRespawn();

		LOGGER.info("[SMLS] handleRespawn HEAD — pending={}, expecting={}, thread={}", pending, expecting,
				Thread.currentThread().getName());

		if (pending) {
			ClientSeamlessTeleportState.pushSuppression();
			tts$pushedSuppression = true;
			ClientSeamlessTeleportState.clearPending();
			return;
		}

		if (expecting) {
			ClientSeamlessTeleportState.holdRespawn(packet);
			ci.cancel();
			return;
		}

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

	// Portal chunk injection

	@Inject(method = "handleLevelChunkWithLight", at = @At("HEAD"), cancellable = true)
	private void tts$onHandleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket packet, CallbackInfo ci) {
		// Check if any portal is waiting for chunks in this dimension at this chunk pos
		FakePortalLevelRegistry.FakePortalEntry entry = FakePortalLevelRegistry.findRecipient(packet.getX(),
				packet.getZ());

		if (entry == null)
			return; // not a portal chunk, let vanilla handle it normally

		LOGGER.debug("[PORTAL] Redirecting chunk ({},{}) into fakeLevel for portal @ {}", packet.getX(), packet.getZ(),
				entry.portalPos());

		Minecraft mc = Minecraft.getInstance();
		ClientLevel realLevel = mc.level;

		// Swap mc.level so vanilla's own chunk handler writes into fakeLevel
		mc.level = entry.fakeLevel();
		try {
			// Re-invoke the real handler body with fakeLevel active.
			// Cast to self and call — mixin will not recurse because
			// mc.level is now fakeLevel, so findRecipient won't match again
			// (fakeLevel's dimension == targetDimension, not current player dim).
			((ClientPacketListener) (Object) this).handleLevelChunkWithLight(packet);
		} finally {
			mc.level = realLevel;
		}

		// Cancel the original call — we already handled it above
		ci.cancel();

		// Notify the fake renderer that new chunk data arrived
		entry.fakeRenderer().allChanged();
	}
}