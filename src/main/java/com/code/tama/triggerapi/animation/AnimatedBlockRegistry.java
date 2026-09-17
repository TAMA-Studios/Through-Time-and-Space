/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;

/**
 * Tracks which world positions currently hold an animated block, without any
 * BlockEntity involved. This is the entire "cost" of the no-BE approach — one
 * map entry per currently-animated block, nothing else.
 *
 * Single flat map, single packed long key (chunk section + local block offset),
 * plain HashMap. Earlier version used a chunk-keyed map of position-keyed maps
 * plus ConcurrentHashMap throughout — neither was warranted: local block
 * coordinates pack into far fewer bits than a BlockPos, and everything that
 * touches this map (onPlace/onRemove, the chunk-load scan, and the render hook)
 * runs on the client thread, so there's no actual cross-thread access to guard
 * against.
 *
 * Key layout (all client-side world coords, Y offset by -64 to keep it unsigned
 * for 1.20.1's -64..320 build range): bits 0-3 : local X (0-15) bits 4-11 :
 * local Z... (see pack()) - packed together with chunk X/Z bits 12+ : chunk X,
 * chunk Z, world Y
 */
public class AnimatedBlockRegistry {

	public static class Entry {
		public final IGeoAnimatedBlock block;
		public final AnimationPlayer player = new AnimationPlayer();

		public Entry(IGeoAnimatedBlock block) {
			this.block = block;
		}
	}

	private static final Map<Long, Entry> ENTRIES = new HashMap<>();

	public static boolean contains(BlockPos pos) {
		return ENTRIES.containsKey(pos.asLong());
	}

	private static long pack(BlockPos pos) {
		// Same bit-packing vanilla itself uses for BlockPos.asLong (X:26 bits, Z:26
		// bits, Y:12 bits),
		// reused here rather than inventing a second scheme — it already fits
		// chunk-local extraction
		// fine since we only ever unpack via BlockPos.of anyway.
		return pos.asLong();
	}

	public static Entry add(BlockPos pos, IGeoAnimatedBlock block) {
		return ENTRIES.computeIfAbsent(pack(pos), k -> new Entry(block));
	}

	public static void remove(BlockPos pos) {
		ENTRIES.remove(pack(pos));
	}

	/**
	 * Removes every entry inside the given chunk. O(n) over the whole registry —
	 * fine, since n is only ever the count of currently-animated blocks, not all
	 * loaded blocks.
	 */
	public static void removeChunk(int chunkX, int chunkZ) {
		int minX = chunkX << 4, maxX = minX + 15;
		int minZ = chunkZ << 4, maxZ = minZ + 15;
		ENTRIES.keySet().removeIf(key -> {
			BlockPos p = BlockPos.of(key);
			return p.getX() >= minX && p.getX() <= maxX && p.getZ() >= minZ && p.getZ() <= maxZ;
		});
	}

	public static Map<Long, Entry> all() {
		return ENTRIES;
	}
}