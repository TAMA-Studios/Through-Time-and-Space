/* (C) TAMA Studios 2026 - DIAGNOSTIC VERSION */
package com.code.tama.triggerapi.boti.teleporting;

import com.code.tama.triggerapi.boti.packets.SeamlessTeleportPacket;
import com.code.tama.tts.server.networking.Networking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Function;

public class SeamlessTeleport {

	private static Logger LOGGER = LogManager.getLogger("SeamlessTeleport");

	public static void teleportTo(Entity entity, ServerLevel destLevel, double x, double y, double z, float yaw,
								  float pitch) {
		if (!(entity instanceof ServerPlayer player)) {
			teleportEntityTo(entity, destLevel, x, y, z, yaw, pitch);
			return;
		}

		ServerLevel currentLevel = (ServerLevel) player.level();

		if (currentLevel == destLevel) {
			teleportSameDimension(player, x, y, z, yaw, pitch);
		} else {
			teleportCrossDimension(player, destLevel, x, y, z, yaw, pitch);
		}
	}

	private static void teleportSameDimension(ServerPlayer player, double x, double y, double z, float yaw,
											  float pitch) {
		player.connection.teleport(x, y, z, yaw, pitch);
	}

	private static void teleportCrossDimension(ServerPlayer player, ServerLevel destLevel, double x, double y, double z,
											   float yaw, float pitch) {

		if(LOGGER == null) LOGGER =LogManager.getLogger("SeamlessTeleport");
		LOGGER.info("[ST] ===== teleportCrossDimension START =====");
		LOGGER.info("[ST] Player: {}, Dest: {}, Pos: {}/{}/{}",
				player.getName().getString(), destLevel.dimension().location(), x, y, z);

		// Ticket-only, non-blocking chunk preload
		LOGGER.info("[ST] Adding chunk tickets...");
		addChunkTickets(destLevel, BlockPos.containing(x, y, z), 3);
		LOGGER.info("[ST] Chunk tickets added.");

		// Mark pending
		SeamlessTeleportContext ctx = new SeamlessTeleportContext(x, y, z, yaw, pitch);
		SeamlessTeleportState.setPending(player, ctx);
		LOGGER.info("[ST] State marked pending.");

		Vec3 targetPos = new Vec3(x, y, z);

		LOGGER.info("[ST] About to call changeDimension...");
		try {
			player.changeDimension(destLevel, new ITeleporter() {
				@Override
				public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
												Function<ServerLevel, PortalInfo> defaultPortalInfo) {
					LOGGER.info("[ST] ITeleporter.getPortalInfo called");
					return new PortalInfo(targetPos, entity.getDeltaMovement(), yaw, pitch);
				}

				@Override
				public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yRot,
										  Function<Boolean, Entity> repositionEntity) {
					LOGGER.info("[ST] ITeleporter.placeEntity called");
					Entity placed = repositionEntity.apply(false);
					LOGGER.info("[ST] repositionEntity.apply done, placed={}", placed);
					if (placed != null) {
						placed.teleportTo(x, y, z);
						placed.setYRot(yaw);
						placed.setXRot(pitch);
					}
					LOGGER.info("[ST] placeEntity returning");
					return placed;
				}

				@Override
				public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceLevel, ServerLevel destLevel) {
					return false;
				}
			});
			LOGGER.info("[ST] changeDimension RETURNED NORMALLY.");
		} catch (Exception e) {
			LOGGER.error("[ST] changeDimension threw an EXCEPTION:", e);
			return;
		}

		LOGGER.info("[ST] Sending SeamlessTeleportPacket...");
		Networking.sendToPlayer(player,
				new SeamlessTeleportPacket(destLevel.dimension().location(), x, y, z, yaw, pitch));

		LOGGER.info("[ST] Scheduling ticket release...");
		TickScheduler.runAfter(100, () -> removeChunkTickets(destLevel, BlockPos.containing(x, y, z), 3));

		LOGGER.info("[ST] ===== teleportCrossDimension COMPLETE =====");
	}

	private static void addChunkTickets(ServerLevel level, BlockPos centre, int chunkRadius) {
		ChunkPos centreChunk = new ChunkPos(centre);
		for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
			for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
				ChunkPos pos = new ChunkPos(centreChunk.x + dx, centreChunk.z + dz);
				level.getChunkSource().addRegionTicket(TicketType.FORCED, pos, 0, pos);
			}
		}
	}

	private static void removeChunkTickets(ServerLevel level, BlockPos centre, int chunkRadius) {
		ChunkPos centreChunk = new ChunkPos(centre);
		for (int dx = -chunkRadius; dx <= chunkRadius; dx++) {
			for (int dz = -chunkRadius; dz <= chunkRadius; dz++) {
				ChunkPos pos = new ChunkPos(centreChunk.x + dx, centreChunk.z + dz);
				level.getChunkSource().removeRegionTicket(TicketType.FORCED, pos, 0, pos);
			}
		}
	}

	private static void teleportEntityTo(Entity entity, ServerLevel destLevel, double x, double y, double z, float yaw,
										 float pitch) {
		if (entity.level() == destLevel) {
			entity.teleportTo(x, y, z);
			entity.setYRot(yaw);
			entity.setXRot(pitch);
			return;
		}

		entity.changeDimension(destLevel, new ITeleporter() {
			@Override
			public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yRot,
									  Function<Boolean, Entity> repositionEntity) {
				Entity placed = repositionEntity.apply(false);
				if (placed != null) {
					placed.teleportTo(x, y, z);
					placed.setYRot(yaw);
					placed.setXRot(pitch);
				}
				return placed;
			}

			@Override
			public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
											Function<ServerLevel, PortalInfo> defaultPortalInfo) {
				return new PortalInfo(new Vec3(x, y, z), entity.getDeltaMovement(), yaw, pitch);
			}

			@Override
			public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceLevel, ServerLevel destLevel) {
				return false;
			}
		});
	}
}