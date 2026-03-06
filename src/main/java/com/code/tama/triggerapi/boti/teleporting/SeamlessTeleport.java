/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import com.code.tama.triggerapi.boti.ChunkGatheringThread;
import com.code.tama.triggerapi.boti.packets.S2C.SeamlessChunkPreloadPacketS2C;
import com.code.tama.triggerapi.boti.packets.SeamlessPreparePacket;
import com.code.tama.triggerapi.boti.packets.SeamlessTeleportPacket;
import com.code.tama.tts.config.TTSConfig;
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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class SeamlessTeleport {

    private static final Logger LOGGER = LogManager.getLogger("SeamlessTeleport");

    public static int preloadRadius = 3;

    /**
     * Tracks in-progress prepare() gathers so we don't double-start one.
     * Key: player UUID, Value: teleport UUID for that gather.
     */
    private static final Map<UUID, UUID> ACTIVE_PREPARES = new ConcurrentHashMap<>();

    /**
     * Kicks off a background geometry gather so that when the player actually
     * walks through the portal the geometry is already (or nearly) staged.
     *
     * This is purely a performance hint — teleportTo() does NOT wait for it.
     * Safe to call every tick; the ACTIVE_PREPARES guard debounces it.
     */
    public static void prepare(ServerPlayer player,
                                ServerLevel destLevel,
                                BlockPos destPos,
                                float yaw) {
        if (ACTIVE_PREPARES.containsKey(player.getUUID())) return;

        addChunkTickets(destLevel, destPos, preloadRadius);

        UUID teleportId = UUID.randomUUID();
        ACTIVE_PREPARES.put(player.getUUID(), teleportId);

        // Tell the client to open its staging buffer under this UUID.
        Networking.sendToPlayer(player, new SeamlessPreparePacket(teleportId));

        startGatherThread(
                player, destLevel, destPos, yaw, teleportId,
                "SeamlessTeleport-Prepare-" + player.getName().getString(),
                () -> {
                    SeamlessTeleportState.markPrepared(player);
                    LOGGER.debug("[ST] prepare() finished for {}.", player.getName().getString());
                },
                () -> {
                    ACTIVE_PREPARES.remove(player.getUUID());
                    TickScheduler.runAfter(200, () -> removeChunkTickets(destLevel, destPos, preloadRadius));
                }
        );
    }

    public static void teleportTo(Entity entity, ServerLevel destLevel,
                                   double x, double y, double z,
                                   float yaw, float pitch) {
        if (!(entity instanceof ServerPlayer player)) {
            teleportEntityTo(entity, destLevel, x, y, z, yaw, pitch);
            return;
        }
        if (entity.level() == destLevel) {
            player.connection.teleport(x, y, z, yaw, pitch);
            return;
        }
        teleportCrossDimension(player, destLevel, x, y, z, yaw, pitch);
    }

    /**
     * HEY! This method is supposed to be internal, why are you here!?
     */
    private static void teleportCrossDimension(ServerPlayer player,
                                                ServerLevel destLevel,
                                                double x, double y, double z,
                                                float yaw, float pitch) {
        LOGGER.info("[ST] teleportCrossDimension → {}", destLevel.dimension().location());

        BlockPos centre = BlockPos.containing(x, y, z);
        addChunkTickets(destLevel, centre, preloadRadius);

        // Cancel any in-flight prepare() gather for this player. Its geometry
        // packets will be discarded on the client by the UUID mismatch guard.
        UUID oldPrepareId = ACTIVE_PREPARES.remove(player.getUUID());

        boolean geometryPreloaded = oldPrepareId == null
                && SeamlessTeleportState.isPrepared(player);

        if (!geometryPreloaded) {
            // No preloaded geometry, kick off async gather so it arrives
            // shortly after the player lands.
            LOGGER.info("[ST] No pre-loaded geometry — firing async gather.");
            UUID teleportId = UUID.randomUUID();
            Networking.sendToPlayer(player, new SeamlessPreparePacket(teleportId));
            startGatherThread(
                    player, destLevel, centre, yaw, teleportId,
                    "SeamlessTeleport-InlineGather",
                    null,
                    () -> TickScheduler.runAfter(100, () -> removeChunkTickets(destLevel, centre, preloadRadius))
            );
        } else {
            LOGGER.info("[ST] Geometry pre-loaded, instant switch.");
        }

        // COMMIT SeamlessPreparePacket is sent by MixinServerPlayer at the HEAD
        // of changeDimension, that is the only place guaranteed to run
        // synchronously before changeDimension writes the respawn packet to the
        // connection, ensuring isSeamlessPending() is true on the client when
        // handleRespawn fires.
        SeamlessTeleportState.setPending(player, new SeamlessTeleportContext(x, y, z, yaw, pitch));

        // Perform the actual dimension switch — fires ClientboundRespawnPacket.
        Vec3 targetPos = new Vec3(x, y, z);
        LOGGER.info("[ST] Calling changeDimension...");
        try {
            player.changeDimension(destLevel, new ITeleporter() {
                @Override
                public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
                                                Function<ServerLevel, PortalInfo> def) {
                    return new PortalInfo(targetPos, entity.getDeltaMovement(), yaw, pitch);
                }

                @Override
                public Entity placeEntity(Entity entity, ServerLevel cw, ServerLevel dw,
                                          float yr, Function<Boolean, Entity> reposition) {
                    Entity placed = reposition.apply(false);
                    if (placed != null) {
                        placed.teleportTo(x, y, z);
                        placed.setYRot(yaw);
                        placed.setXRot(pitch);
                    }
                    return placed;
                }

                @Override
                public boolean playTeleportSound(ServerPlayer p, ServerLevel src, ServerLevel dst) {
                    return false;
                }
            });
            LOGGER.info("[ST] changeDimension returned.");
        } catch (Exception e) {
            LOGGER.error("[ST] changeDimension threw:", e);
            SeamlessTeleportState.consumePending(player);
            SeamlessTeleportState.clearPrepared(player);
            return;
        }

        Networking.sendToPlayer(player,
                new SeamlessTeleportPacket(destLevel.dimension().location(), x, y, z, yaw, pitch));

        TickScheduler.runAfter(100, () -> removeChunkTickets(destLevel, centre, preloadRadius));
        SeamlessTeleportState.consumePending(player);
        SeamlessTeleportState.clearPrepared(player);

        LOGGER.info("[ST] teleportCrossDimension COMPLETE.");
    }

    /**
     * Starts a ChunkGatheringThread on a daemon thread.
     * Does NOT block the calling (server) thread.
     *
     * @param onSuccess  run on the gather thread after successful completion (nullable)
     * @param onFinally  always run on the gather thread after completion (nullable)
     */
    private static void startGatherThread(ServerPlayer player,
                                           ServerLevel destLevel,
                                           BlockPos destPos,
                                           float yaw,
                                           UUID teleportId,
                                           String threadName,
                                           Runnable onSuccess,
                                           Runnable onFinally) {
        AtomicInteger batchIndex = new AtomicInteger(0);

        ChunkGatheringThread gather = new ChunkGatheringThread(
                TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get(),
                (ServerLevel) player.level(),
                destLevel,
                destPos,
                yaw,
                (batch, total) -> {
                    int idx = batchIndex.getAndIncrement();
                    Networking.sendToPlayer(player,
                            new SeamlessChunkPreloadPacketS2C(teleportId, batch, idx, total));
                }
        );

        Thread t = new Thread(() -> {
            try {
                gather.start();
                if (onSuccess != null) onSuccess.run();
            } catch (Exception e) {
                LOGGER.error("[ST] Gather thread '{}' threw: {}", threadName, e.getMessage(), e);
            } finally {
                if (onFinally != null) onFinally.run();
            }
        }, threadName);
        t.setDaemon(true);
        t.start();
    }



    private static void addChunkTickets(ServerLevel level, BlockPos centre, int radius) {
        ChunkPos centreChunk = new ChunkPos(centre);
        for (int dx = -radius; dx <= radius; dx++)
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos pos = new ChunkPos(centreChunk.x + dx, centreChunk.z + dz);
                level.getChunkSource().addRegionTicket(TicketType.FORCED, pos, 0, pos);
            }
    }

    private static void removeChunkTickets(ServerLevel level, BlockPos centre, int radius) {
        ChunkPos centreChunk = new ChunkPos(centre);
        for (int dx = -radius; dx <= radius; dx++)
            for (int dz = -radius; dz <= radius; dz++) {
                ChunkPos pos = new ChunkPos(centreChunk.x + dx, centreChunk.z + dz);
                level.getChunkSource().removeRegionTicket(TicketType.FORCED, pos, 0, pos);
            }
    }



    private static void teleportEntityTo(Entity entity, ServerLevel destLevel,
                                          double x, double y, double z,
                                          float yaw, float pitch) {
        if (entity.level() == destLevel) {
            entity.teleportTo(x, y, z);
            entity.setYRot(yaw);
            entity.setXRot(pitch);
            return;
        }
        entity.changeDimension(destLevel, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel cw, ServerLevel dw,
                                      float yr, Function<Boolean, Entity> reposition) {
                Entity placed = reposition.apply(false);
                if (placed != null) {
                    placed.teleportTo(x, y, z);
                    placed.setYRot(yaw);
                    placed.setXRot(pitch);
                }
                return placed;
            }

            @Override
            public PortalInfo getPortalInfo(Entity entity, ServerLevel dw,
                                            Function<ServerLevel, PortalInfo> def) {
                return new PortalInfo(new Vec3(x, y, z), entity.getDeltaMovement(), yaw, pitch);
            }

            @Override
            public boolean playTeleportSound(ServerPlayer p, ServerLevel src, ServerLevel dst) {
                return false;
            }
        });
    }
}