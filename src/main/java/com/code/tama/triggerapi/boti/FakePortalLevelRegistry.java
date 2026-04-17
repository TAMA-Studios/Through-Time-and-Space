/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import com.code.tama.tts.TTSMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Client-side registry mapping portal {@link BlockPos} keys to their
 * ({@link ClientLevel}, {@link LevelRenderer}) pair and the chunk window they
 * are expecting vanilla chunk packets for.
 *
 * <p>Thread-safety: chunk packets arrive on the Netty thread; registration and
 * arm calls happen on the render/game thread. All maps are
 * {@link ConcurrentHashMap} so cross-thread reads are safe without explicit
 * locking.
 */
public class FakePortalLevelRegistry {

	// -------------------------------------------------------------------------
	// Data
	// -------------------------------------------------------------------------

	public record FakePortalEntry(
			BlockPos portalPos,
			ResourceKey<Level> targetDimension,
			ClientLevel fakeLevel,
			LevelRenderer fakeRenderer,
			int centerChunkX,
			int centerChunkZ,
			int chunkRadius) {
	}

	/** portalPos.asLong() → entry */
	private static final Map<Long, FakePortalEntry> REGISTRY = new ConcurrentHashMap<>();

	/**
	 * portalPos.asLong() → remaining chunk packets to intercept.
	 * Set by {@link #armForIncomingChunks} just before the server streams chunks;
	 * decremented by the mixin on each intercepted packet; cleared when it reaches 0.
	 */
	private static final Map<Long, Integer> ARMED = new ConcurrentHashMap<>();

	// -------------------------------------------------------------------------
	// Registration
	// -------------------------------------------------------------------------

	public static void register(FakePortalEntry entry) {
		REGISTRY.put(entry.portalPos().asLong(), entry);
	}

	public static void unregister(BlockPos portalPos) {
		long key = portalPos.asLong();
		FakePortalEntry removed = REGISTRY.remove(key);
		ARMED.remove(key);
		if (removed != null) {
			// Must run on render thread — fakeRenderer.close() releases GL resources.
			// recordRenderCall is safe to call from any thread.
			RenderSystem.recordRenderCall(() -> {
				removed.fakeRenderer().allChanged(); // forces chunk rebuild invalidation
				removed.fakeRenderer().close();
			});

			try {
				removed.fakeLevel.close();
				removed.fakeRenderer.setLevel(null);

			} catch (IOException e) {
				TTSMod.LOGGER.error("Oops.");
			}
		}
		Minecraft.getInstance().levelRenderer.allChanged();
	}

	public static void unregisterAll() {
		REGISTRY.values().forEach(e ->
				com.mojang.blaze3d.systems.RenderSystem.recordRenderCall(() ->
						e.fakeRenderer().close()));
		REGISTRY.clear();
		ARMED.clear();
	}

	// -------------------------------------------------------------------------
	// Arming (called from ArmPortalChunksPacketS2C before chunk packets arrive)
	// -------------------------------------------------------------------------

	/**
	 * Arms the registry to intercept the next {@code chunkCount} vanilla chunk
	 * packets and route them into the fakeLevel for {@code portalPos}.
	 *
	 * <p>Safe to call from any thread.
	 */
	public static void armForIncomingChunks(BlockPos portalPos, int chunkCount) {
		long key = portalPos.asLong();
//		if (REGISTRY.containsKey(key)) {
			ARMED.put(key, chunkCount);
//		}
		// If the registry entry isn't up yet (arm arrived before fakeLevel was built),
		// the packet handler will just fall through\
		// next updateChunkModel cycle will
		// resend the request and the arm will re-fire.
	}

	// -------------------------------------------------------------------------
	// Lookup (called from Netty thread in the mixin)
	// -------------------------------------------------------------------------

	/**
	 * Returns the first armed portal entry that covers {@code (chunkX, chunkZ)}
	 * in the player's current dimension, and decrements its arm counter.
	 * Returns {@code null} if no armed portal matches.
	 *
	 * <p>Called on the Netty thread — must be fast and allocation-free.
	 */
	@Nullable
	public static FakePortalEntry findAndConsumeArmedRecipient(int chunkX, int chunkZ) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null) return null;

		ResourceKey<Level> currentDim = mc.level.dimension();

		for (Map.Entry<Long, Integer> armEntry : ARMED.entrySet()) {
			long key = armEntry.getKey();
			int remaining = armEntry.getValue();
			if (remaining <= 0) {
				ARMED.remove(key);
				continue;
			}

			FakePortalEntry portalEntry = REGISTRY.get(key);
			if (portalEntry == null) {
				ARMED.remove(key);
				continue;
			}

			// Only intercept chunks that belong to the target dimension.
			// The vanilla packet arrives tagged as the current player dimension,
			// so we match on chunk coords falling inside the armed window instead.
			if (!portalEntry.targetDimension().equals(currentDim)) continue;

			int dx = Math.abs(chunkX - portalEntry.centerChunkX());
			int dz = Math.abs(chunkZ - portalEntry.centerChunkZ());
			if (dx > portalEntry.chunkRadius() || dz > portalEntry.chunkRadius()) continue;

			// Consume one slot
			if (remaining <= 1) {
				ARMED.remove(key);
			} else {
				ARMED.put(key, remaining - 1);
			}

			return portalEntry;
		}

		return null;
	}

	// -------------------------------------------------------------------------
	// Accessors
	// -------------------------------------------------------------------------

	@Nullable
	public static FakePortalEntry get(BlockPos portalPos) {
		return REGISTRY.get(portalPos.asLong());
	}

	public static Collection<FakePortalEntry> all() {
		return REGISTRY.values();
	}
}