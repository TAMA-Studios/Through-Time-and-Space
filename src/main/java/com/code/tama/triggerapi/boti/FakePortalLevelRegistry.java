/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Client-side registry mapping portal block positions to their (fakeLevel,
 * fakeRenderer) pair and the chunk area they're expecting.
 *
 * Thread-safe: chunk packets arrive on the Netty thread, registration happens
 * on the render/game thread.
 */
public class FakePortalLevelRegistry {

	public record FakePortalEntry(BlockPos portalPos, ResourceKey<Level> targetDimension, ClientLevel fakeLevel,
			LevelRenderer fakeRenderer, int centerChunkX, int centerChunkZ, int chunkRadius) {
	}

	// portalPos (as long) → entry
	private static final Map<Long, FakePortalEntry> REGISTRY = new ConcurrentHashMap<>();

	public static void register(FakePortalEntry entry) {
		REGISTRY.put(entry.portalPos().asLong(), entry);
	}

	public static void unregister(BlockPos portalPos) {
		FakePortalEntry removed = REGISTRY.remove(portalPos.asLong());
		if (removed != null) {
			// Clean up GL resources on the render thread
			removed.fakeRenderer().close();
		}
	}

	public static void unregisterAll() {
		REGISTRY.values().forEach(e -> e.fakeRenderer().close());
		REGISTRY.clear();
	}

	/**
	 * Find the first portal whose target dimension and chunk area covers the given
	 * chunk coords. Called from the Netty thread — must be fast and thread-safe.
	 */
	@Nullable public static FakePortalEntry findRecipient(int chunkX, int chunkZ) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null)
			return null;

		ResourceKey<Level> currentDim = mc.level.dimension();

		for (FakePortalEntry entry : REGISTRY.values()) {
			// Only intercept chunks that belong to the target dimension
			// (the server sends them tagged with the current connection dim,
			// so we match by chunk position window instead)
			if (!entry.targetDimension().equals(currentDim))
				continue;

			int dx = Math.abs(chunkX - entry.centerChunkX());
			int dz = Math.abs(chunkZ - entry.centerChunkZ());

			if (dx <= entry.chunkRadius() && dz <= entry.chunkRadius()) {
				return entry;
			}
		}
		return null;
	}

	public static Collection<FakePortalEntry> all() {
		return REGISTRY.values();
	}
}