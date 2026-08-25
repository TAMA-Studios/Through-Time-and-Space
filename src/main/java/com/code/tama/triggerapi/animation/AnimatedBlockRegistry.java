/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

/**
 * Tracks which world positions currently hold an animated block, without any
 * BlockEntity involved. This is the entire "cost" of the no-BE approach`, ` one
 * long->state map entry per animated block, nothing else. <br />
 * Populated from two places (see AnimatedBlockClientEvents): - Block.onPlace /
 * onRemove, cheap, single-position updates for anything that changes while the
 * chunk is loaded and visible. - Chunk load, a one-time scan to pick up
 * animated blocks that were already part of a chunk being loaded (rejoining a
 * world, teleporting, etc.). This costs the same order of work vanilla already
 * does to find block entities in a newly loaded chunk, so it's not new
 * overhead. <br />
 * Self-healing: the renderer double-checks the actual BlockState every time it
 * renders an entry and silently drops stale ones, so a missed removal hook is a
 * non-issue rather than a leak.
 */
public class AnimatedBlockRegistry {

	public static class Entry {
		public final IGeoAnimatedBlock block;
		public final AnimationPlayer player = new AnimationPlayer();

		public Entry(IGeoAnimatedBlock block) {
			this.block = block;
		}
	}

	// Bucketed by chunk so the render hook only has to touch chunks near the
	// camera/frustum.
	private static final Map<Long, Map<BlockPos, Entry>> BY_CHUNK = new ConcurrentHashMap<>();

	private static long chunkKey(BlockPos pos) {
		return ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
	}

	public static Entry add(BlockPos pos, IGeoAnimatedBlock block) {
		Map<BlockPos, Entry> chunkMap = BY_CHUNK.computeIfAbsent(chunkKey(pos), k -> new ConcurrentHashMap<>());
		return chunkMap.computeIfAbsent(pos.immutable(), p -> new Entry(block));
	}

	public static boolean contains(BlockPos pos) {
		Map<BlockPos, Entry> chunkMap = BY_CHUNK.computeIfAbsent(chunkKey(pos), k -> new ConcurrentHashMap<>());
		return chunkMap.containsKey(pos);
	}

	public static void remove(BlockPos pos) {
		Map<BlockPos, Entry> chunkMap = BY_CHUNK.get(chunkKey(pos));
		if (chunkMap != null) {
			chunkMap.remove(pos);
			if (chunkMap.isEmpty())
				BY_CHUNK.remove(chunkKey(pos));
		}
	}

	public static void removeChunk(int chunkX, int chunkZ) {
		BY_CHUNK.remove(ChunkPos.asLong(chunkX, chunkZ));
	}

	/**
	 * Returns a snapshot map for one chunk, or null if nothing animated is
	 * registered there.
	 */
	public static Map<BlockPos, Entry> getChunk(int chunkX, int chunkZ) {
		return BY_CHUNK.get(ChunkPos.asLong(chunkX, chunkZ));
	}

	public static Iterable<Map.Entry<Long, Map<BlockPos, Entry>>> allChunks() {
		return BY_CHUNK.entrySet();
	}
}
