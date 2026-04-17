/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import com.code.tama.triggerapi.boti.FakePortalLevelRegistry;
import com.code.tama.tts.mixin.client.IClientPacketListenerAccessor;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacketData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;

import java.util.List;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientPacketListener {
	@Unique private static final ThreadLocal<Boolean> tts$inFakeChunkLoad = ThreadLocal.withInitial(() -> false);

	@Unique private static final Logger LOGGER = LogManager.getLogger("TTS$SeamlessTele#ClientPacketListener");

	// Whether THIS invocation of handleRespawn pushed suppression.
	// Instance field so each re-entrant call (Netty thread vs Render thread)
	// tracks its own push independently.
	@Unique private boolean tts$pushedSuppression = false;

	// =========================================================================
	// Existing seamless-teleport hooks — UNCHANGED
	// =========================================================================

	@Inject(method = "handleRespawn", at = @At("HEAD"), cancellable = true)
	private void tts$onHandleRespawnHead(ClientboundRespawnPacket packet, CallbackInfo ci) {
		boolean pending = ClientSeamlessTeleportState.isSeamlessPending();
		boolean expecting = ClientSeamlessTeleportState.isExpectingSeamlessRespawn();

		LOGGER.info("[SMLS] handleRespawn HEAD — pending={}, expecting={}, thread={}", pending, expecting,
				Thread.currentThread().getName());

		if (pending) {
			// COMMIT arrived before the respawn packet — normal fast path.
			ClientSeamlessTeleportState.pushSuppression();
			tts$pushedSuppression = true;
			ClientSeamlessTeleportState.clearPending();
			return;
		}

		if (expecting) {
			// Respawn arrived before COMMIT — hold and cancel.
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

	// =========================================================================
	// Portal fakeLevel chunk injection
	// =========================================================================

	/**
	 * Intercepts vanilla chunk packets that the server sent for a portal's target
	 * dimension and routes them into the portal's {@code fakeLevel} instead of
	 * (not in addition to) the real {@link ClientLevel}.
	 *
	 * <p>Flow:
	 * <ol>
	 *   <li>Server sends {@link com.code.tama.triggerapi.boti.packets.S2C.ArmPortalChunksPacketS2C}
	 *       -> {@link FakePortalLevelRegistry#armForIncomingChunks} is called.</li>
	 *   <li>Server sends N × {@link ClientboundLevelChunkWithLightPacket}.</li>
	 *   <li>This inject fires for each one, finds the armed portal, swaps
	 *       {@code mc.level} to {@code fakeLevel}, lets vanilla's own handler body
	 *       run (which writes chunk data into whatever {@code mc.level} is at the
	 *       time), then restores {@code mc.level} and cancels the outer call so the
	 *       chunk is not also written into the real level.</li>
	 * </ol>
	 *
	 * <p>If no armed portal matches the chunk coords, the method returns immediately
	 * and vanilla handles the packet normally — this path is zero-overhead for
	 * regular gameplay chunks.
	 */
	@Inject(method = "handleLevelChunkWithLight", at = @At("HEAD"), cancellable = true)
	private void tts$onHandleLevelChunkWithLight(ClientboundLevelChunkWithLightPacket packet, CallbackInfo ci) {
		if (tts$inFakeChunkLoad.get()) return;

		FakePortalLevelRegistry.FakePortalEntry entry =
				FakePortalLevelRegistry.findAndConsumeArmedRecipient(packet.getX(), packet.getZ());
		if (entry == null) return;

		tts$inFakeChunkLoad.set(true);

		int cx = packet.getX();
		int cz = packet.getZ();
		ClientLevel fakeLevel = entry.fakeLevel();

		// Call replaceWithPacketData directly on fakeLevel's OWN chunk cache.
		// This completely bypasses ClientPacketListener and its this.level field,
		// so there is no recursion path whatsoever.
		ClientChunkCache fakeChunkCache = fakeLevel.getChunkSource();
		ClientboundLevelChunkPacketData chunkData = packet.getChunkData();

		LevelChunk chunk = fakeChunkCache.replaceWithPacketData(
				cx, cz,
				chunkData.getReadBuffer(),
				chunkData.getHeightmaps(),
				// Block entity consumer — discard; fakeLevel BEs are visual-only.
				// BlockEntityTagOutput is a @FunctionalInterface, just pass a no-op.
				blockEntityTagOutput -> {}
		);

		if (chunk != null) {
			// Notify fakeLevel that this chunk is now loaded (invalidates tint caches,
			// starts entity tracking for the chunk pos — both harmless on fakeLevel).
			fakeLevel.onChunkLoaded(new ChunkPos(cx, cz));

			// Queue light data exactly as vanilla does, but against fakeLevel's light engine.
			ClientboundLightUpdatePacketData lightData = packet.getLightData();
			fakeLevel.queueLightUpdate(() -> {
				LevelLightEngine lightEngine = fakeLevel.getLightEngine();
				ChunkPos chunkPos = new ChunkPos(cx, cz);

				// setLightEnabled is the correct 1.20 API on LevelLightEngine directly.
				lightEngine.setLightEnabled(chunkPos, true);

				// Apply sky and block light sections.
				applyLightLayer(lightEngine, LightLayer.SKY,
						lightData.getSkyYMask(), lightData.getEmptySkyYMask(),
						lightData.getSkyUpdates(), chunkPos);
				applyLightLayer(lightEngine, LightLayer.BLOCK,
						lightData.getBlockYMask(), lightData.getEmptyBlockYMask(),
						lightData.getBlockUpdates(), chunkPos);

				lightEngine.setLightEnabled(chunkPos, true);
			});

			// Mark sections dirty for the fake renderer.
			for (int sy = fakeLevel.getMinSection(); sy < fakeLevel.getMaxSection(); sy++) {
				entry.fakeRenderer().setSectionDirtyWithNeighbors(cx, sy, cz);
			}
		}

		ci.cancel();
	}

	@Unique
	private static void applyLightLayer(
			LevelLightEngine lightEngine,
			LightLayer layer,
			java.util.BitSet hasMask,
			java.util.BitSet emptyMask,
			java.util.List<byte[]> updates,
			ChunkPos chunkPos) {

		int updateIdx = 0;
		int sectionCount = lightEngine.getLightSectionCount();
		int minSection = lightEngine.getMinLightSection();

		for (int i = 0; i < sectionCount; i++) {
			SectionPos sectionPos = SectionPos.of(chunkPos, minSection + i);
			if (emptyMask.get(i)) {
				lightEngine.queueSectionData(layer, sectionPos, new DataLayer());
			} else if (hasMask.get(i) && updateIdx < updates.size()) {
				lightEngine.queueSectionData(layer, sectionPos, new DataLayer(updates.get(updateIdx++)));
			}
		}
	}
}