/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.client;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChunkPreloader {

	private static final Map<Long, List<ChunkPos>> HELD_TICKETS = new HashMap<>();

	public static void forceLoadChunks(ServerLevel level, BlockPos center, int radius) {
		ChunkPos centerChunk = new ChunkPos(center);
		List<ChunkPos> chunks = new ArrayList<>();

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dz = -radius; dz <= radius; dz++) {
				ChunkPos pos = new ChunkPos(centerChunk.x + dx, centerChunk.z + dz);
				chunks.add(pos);
				// FORCED ticket type keeps chunk fully loaded, bypassing lazy loading
				level.getChunkSource().addRegionTicket(TicketType.FORCED, pos, 2, pos);
			}
		}

		HELD_TICKETS.put(centerChunk.toLong(), chunks);
	}

	public static void releaseChunks(ServerLevel level, BlockPos center, int radius, ServerPlayer player) {
		ChunkPos centerChunk = new ChunkPos(center);
		List<ChunkPos> chunks = HELD_TICKETS.remove(centerChunk.toLong());
		if (chunks == null)
			return;

		for (ChunkPos pos : chunks) {
			level.getChunkSource().removeRegionTicket(TicketType.FORCED, pos, 2, pos);
		}
	}
}