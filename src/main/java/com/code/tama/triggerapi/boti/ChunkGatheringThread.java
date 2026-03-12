/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import javax.annotation.Nullable;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.config.TTSConfig;
import com.code.tama.tts.core.networking.Networking;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.network.PacketDistributor;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;

/**
 * Off-thread chunk geometry gatherer.
 *
 * Can be constructed in two modes:
 *
 * 1. PORTAL mode -- legacy option. Pass an {@link AbstractPortalTile}; on
 * completion the gathered batches are broadcast to the portal's dimension via
 * {@link PortalChunkDataPacketS2C}.
 *
 * 2. TELEPORT mode -- new option. Pass a {@code resultCallback}; on completion
 * each batch is delivered to the callback instead of being broadcast.
 * {@link com.code.tama.triggerapi.boti.SeamlessTeleport} uses this to push
 * geometry to a specific player before changeDimension fires.
 *
 * The caller must supply the {@link ServerLevel} that contains the destination
 * geometry, the target {@link BlockPos} at the centre of the region to gather,
 * and the number of chunks to gather in each axis.
 *
 * THREAD SAFETY ------------- The thread submits small work items to the main
 * server thread via {@code server.submit(...).join()} where Minecraft requires
 * it (chunk loading, light engine access), and does everything else off-thread.
 */
@SuppressWarnings({"unchecked", "deprecation"})
public class ChunkGatheringThread extends Thread {

	private static final int MAX_BLOCKS_PER_BATCH = 40_000;

	private final int chunks;
	private final ServerLevel level; // level that OWNS the portal tile (used for broadcast dimension)
	private final ServerLevel targetLevel; // level whose geometry we are gathering
	private final BlockPos targetPos;
	private final float yaw;

	/** Non-null in PORTAL mode. */
	@Nullable private final AbstractPortalTile portalTile;

	/**
	 * Non-null in TELEPORT mode. Receives (batchList, totalBatchCount) once per
	 * batch.
	 */
	@Nullable private final BiConsumer<List<BotiBlockContainer>, Integer> resultCallback;

	/**
	 * PORTAL mode constructor
	 */
	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ChunkGatheringThread(int chunks, ServerLevel level, AbstractPortalTile portalTile, BlockPos targetPos) {
		this.setName("BOTIChunkGatheringThread");
		this.chunks = chunks;
		this.level = level;
		this.portalTile = portalTile;
		this.targetLevel = level.getServer().getLevel(portalTile.getTargetLevel());
		this.targetPos = targetPos;
		this.yaw = portalTile.targetY;
		this.resultCallback = null;
	}

	/**
	 * TELEPORT mode constructor.
	 *
	 * @param chunks
	 *            number of chunks per axis to gather
	 * @param sourceLevel
	 *            the level the portal tile lives in (used only for its server
	 *            reference; geometry is gathered from {@code destLevel})
	 * @param destLevel
	 *            the destination level whose geometry to gather
	 * @param targetPos
	 *            centre of the region to gather
	 * @param yaw
	 *            portal facing yaw (for behind-portal culling)
	 * @param resultCallback
	 *            receives each batch; first arg is the batch, second arg is the
	 *            TOTAL number of batches that will be sent (known only after
	 *            gathering; the callback is invoked once per batch with the same
	 *            total each time so receivers can track completion)
	 */
	public ChunkGatheringThread(int chunks, ServerLevel sourceLevel, ServerLevel destLevel, BlockPos targetPos,
			float yaw, BiConsumer<List<BotiBlockContainer>, Integer> resultCallback) {
		this.setName("BOTIChunkGatheringThread");
		this.chunks = chunks;
		this.level = sourceLevel;
		this.portalTile = null;
		this.targetLevel = destLevel;
		this.targetPos = targetPos;
		this.yaw = yaw;
		this.resultCallback = resultCallback;
	}

	@Override
	public void run() {
		if (targetLevel == null) {
			TTSMod.LOGGER.error("[ChunkGatheringThread] targetLevel is null – aborting gather.");
			return;
		}

		float localYaw = yaw;
		boolean facingPosZ = localYaw >= -45 && localYaw < 45;
		boolean facingNegZ = localYaw >= 135 || localYaw < -135;
		boolean facingPosX = localYaw >= 45 && localYaw < 135;
		// facingNegX is the else branch

		int maxBlocks = MAX_BLOCKS_PER_BATCH;

		try {
			ArrayList<BotiBlockContainer> containers = new ArrayList<>();
			ArrayList<List<BotiBlockContainer>> batches = new ArrayList<>();

			int chunksToRender = Math.min(this.chunks, TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get());

			int uMin = -chunksToRender / 2;
			int uMax = chunksToRender / 2;
			int vMin = -chunksToRender / 2;
			int vMax = chunksToRender / 2;

			int baseChunkX = (targetPos.getX() >> 4);
			int baseChunkZ = (targetPos.getZ() >> 4);
			int sectionBaseY = (targetPos.getY() - 16) & ~15;
			int sectionBaseYAbove = targetPos.getY() & ~15;

			int worldXMin = (baseChunkX + uMin + 1) * 16;
			int worldXMax = (baseChunkX + uMax) * 16 + 15;
			int worldZMin = (baseChunkZ + vMin + 1) * 16;
			int worldZMax = (baseChunkZ + vMax) * 16 + 15;
			int worldYMin = sectionBaseY;
			int worldYMax = sectionBaseYAbove + 15;

			int sizeX = worldXMax - worldXMin + 1;
			int sizeY = worldYMax - worldYMin + 1;
			int sizeZ = worldZMax - worldZMin + 1;

			boolean[][][] solid = new boolean[sizeX][sizeY][sizeZ];
			BlockState[][][] blockStates = new BlockState[sizeX][sizeY][sizeZ];
			FluidState[][][] fluidStates = new FluidState[sizeX][sizeY][sizeZ];
			int[][][] packedLights = new int[sizeX][sizeY][sizeZ];

			// --- Phase 1: gather block data from server thread ---
			for (int u = uMin + 1; u < uMax; u++) {
				for (int v = vMin + 1; v < vMax; v++) {
					ChunkPos chunkPos = new ChunkPos(baseChunkX + u, baseChunkZ + v);
					targetLevel.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true);
					LevelChunk chunk = targetLevel.getChunk(chunkPos.x, chunkPos.z);

					LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY() - 16));
					LevelChunkSection sectionAbove = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

					if (!chunk.isLightCorrect()) {
						targetLevel.getServer().submit(() -> {
							for (int ly = targetLevel.getMinBuildHeight(); ly < targetLevel
									.getMaxBuildHeight(); ly += 16) {
								targetLevel.getLightEngine().checkBlock(
										new BlockPos(chunkPos.getMiddleBlockX(), ly, chunkPos.getMiddleBlockZ()));
							}
						}).join();
						Thread.sleep(50);
					}

					final int fu = u, fv = v;
					targetLevel.getServer().submit(() -> {
						for (int y = 0; y < 16; y++) {
							for (int x = 0; x < 16; x++) {
								for (int z = 0; z < 16; z++) {
									// --- lower section ---
									int gx = chunkPos.getMinBlockX() + x;
									int gy = sectionBaseY + y;
									int gz = chunkPos.getMinBlockZ() + z;

									int lx = gx - worldXMin;
									int ly = gy - worldYMin;
									int lz = gz - worldZMin;

									if (lx < 0 || lx >= sizeX || ly < 0 || ly >= sizeY || lz < 0 || lz >= sizeZ)
										continue;

									BlockState state = section.getBlockState(x, y, z);
									FluidState fluid = section.getFluidState(x, y, z);

									blockStates[lx][ly][lz] = state;
									fluidStates[lx][ly][lz] = fluid;
									solid[lx][ly][lz] = !state.isAir();

									BlockPos samplePos = new BlockPos(gx, gy, gz);
									DataLayer bl = targetLevel.getLightEngine().getLayerListener(LightLayer.BLOCK)
											.getDataLayerData(SectionPos.of(samplePos));
									DataLayer sl = targetLevel.getLightEngine().getLayerListener(LightLayer.SKY)
											.getDataLayerData(SectionPos.of(samplePos));
									int blockLight = bl != null ? bl.get(gx & 15, gy & 15, gz & 15) : 0;
									int skyLight = sl != null ? sl.get(gx & 15, gy & 15, gz & 15) : 15;
									packedLights[lx][ly][lz] = LightTexture.pack(blockLight, skyLight);

									// --- upper section ---
									int gyA = sectionBaseYAbove + y;
									int lyA = gyA - worldYMin;
									if (lyA < 0 || lyA >= sizeY)
										continue;

									BlockState stateA = sectionAbove.getBlockState(x, y, z);
									FluidState fluidA = sectionAbove.getFluidState(x, y, z);

									blockStates[lx][lyA][lz] = stateA;
									fluidStates[lx][lyA][lz] = fluidA;
									solid[lx][lyA][lz] = !stateA.isAir()
											&& stateA.isSolidRender(chunk, new BlockPos(gx, gyA, gz));

									BlockPos samplePosA = new BlockPos(gx, gyA, gz);
									DataLayer blA = targetLevel.getLightEngine().getLayerListener(LightLayer.BLOCK)
											.getDataLayerData(SectionPos.of(samplePosA));
									DataLayer slA = targetLevel.getLightEngine().getLayerListener(LightLayer.SKY)
											.getDataLayerData(SectionPos.of(samplePosA));
									int blockLightA = blA != null ? blA.get(gx & 15, gyA & 15, gz & 15) : 0;
									int skyLightA = slA != null ? slA.get(gx & 15, gyA & 15, gz & 15) : 15;
									packedLights[lx][lyA][lz] = LightTexture.pack(blockLightA, skyLightA);
								}
							}
						}
					}).join();
				}
			}

			// --- Phase 2: flood-fill to find exposed faces ---
			boolean[][][] reachable = new boolean[sizeX][sizeY][sizeZ];
			java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();

			for (int y = 0; y < sizeY; y++) {
				for (int x = 0; x < sizeX; x++) {
					tryEnqueue(queue, reachable, solid, x, y, 0);
					tryEnqueue(queue, reachable, solid, x, y, sizeZ - 1);
				}
				for (int z = 0; z < sizeZ; z++) {
					tryEnqueue(queue, reachable, solid, 0, y, z);
					tryEnqueue(queue, reachable, solid, sizeX - 1, y, z);
				}
			}
			for (int x = 0; x < sizeX; x++) {
				for (int z = 0; z < sizeZ; z++) {
					tryEnqueue(queue, reachable, solid, x, 0, z);
					tryEnqueue(queue, reachable, solid, x, sizeY - 1, z);
				}
			}

			int[] dx = {1, -1, 0, 0, 0, 0};
			int[] dy = {0, 0, 1, -1, 0, 0};
			int[] dz = {0, 0, 0, 0, 1, -1};

			while (!queue.isEmpty()) {
				int[] cell = queue.poll();
				int cx = cell[0], cy = cell[1], cz = cell[2];
				for (int d = 0; d < 6; d++) {
					tryEnqueue(queue, reachable, solid, cx + dx[d], cy + dy[d], cz + dz[d]);
				}
			}

			// --- Phase 3: emit containers ---
			for (int lx = 0; lx < sizeX; lx++) {
				for (int ly = 0; ly < sizeY; ly++) {
					for (int lz = 0; lz < sizeZ; lz++) {
						BlockState state = blockStates[lx][ly][lz];
						if (state == null || state.isAir())
							continue;

						boolean exposed = false;
						for (int d = 0; d < 6; d++) {
							int nx = lx + dx[d], ny = ly + dy[d], nz = lz + dz[d];
							if (nx < 0 || nx >= sizeX || ny < 0 || ny >= sizeY || nz < 0 || nz >= sizeZ) {
								exposed = false;
								break;
							}
							if (reachable[nx][ny][nz]) {
								exposed = true;
								break;
							}
						}
						if (!exposed)
							continue;

						int globalX = worldXMin + lx;
						int globalY = worldYMin + ly;
						int globalZ = worldZMin + lz;

						BlockPos relPos = new BlockPos(globalX - targetPos.getX(), globalY - targetPos.getY(),
								globalZ - targetPos.getZ());

						boolean isBehind;
						if (facingPosZ)
							isBehind = relPos.getZ() >= 0;
						else if (facingNegZ)
							isBehind = relPos.getZ() <= 0;
						else if (facingPosX)
							isBehind = relPos.getX() >= 0;
						else
							isBehind = relPos.getX() <= 0;
						if (isBehind)
							continue;

						FluidState fluid = fluidStates[lx][ly][lz];
						int packed = packedLights[lx][ly][lz];

						if (fluid == null || fluid.isEmpty())
							containers.add(new BotiBlockContainer(targetLevel, packed, relPos, state));
						else
							containers.add(new BotiBlockContainer(targetLevel, state, fluid, relPos, packed));

						if (containers.size() >= maxBlocks - 1) {
							batches.add(new ArrayList<>(containers));
							containers.clear();
						}
					}
				}
			}

			if (!containers.isEmpty()) {
				batches.add(new ArrayList<>(containers));
			}

			// --- Phase 4: deliver ---
			if (resultCallback != null) {
				// TELEPORT mode: deliver to callback (caller will wrap in packets)
				int total = batches.size();
				for (List<BotiBlockContainer> batch : batches) {
					resultCallback.accept(batch, total);
				}
			} else if (portalTile != null) {
				// PORTAL mode: broadcast via PortalChunkDataPacketS2C (legacy)
				TTSMod.LOGGER.debug("[ChunkGatheringThread] Sending {} portal batch packet(s).", batches.size());
				for (int i = 0; i < batches.size(); i++) {
					final int idx = i;
					final int total = batches.size();
					Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
						assert portalTile.getLevel() != null;
						return portalTile.getLevel().dimension();
					}), new PortalChunkDataPacketS2C(portalTile.getBlockPos(), batches.get(idx), idx, total));
				}
			}

		} catch (Exception e) {
			TTSMod.LOGGER.error("[ChunkGatheringThread] Exception during gather: {}", e.getMessage(), e);
		}

		super.run();
	}

	// -------------------------------------------------------------------------
	// BFS helper
	// -------------------------------------------------------------------------

	private static void tryEnqueue(java.util.ArrayDeque<int[]> queue, boolean[][][] reachable, boolean[][][] solid,
			int x, int y, int z) {
		if (x < 0 || x >= solid.length)
			return;
		if (y < 0 || y >= solid[0].length)
			return;
		if (z < 0 || z >= solid[0][0].length)
			return;
		if (solid[x][y][z] || reachable[x][y][z])
			return;
		reachable[x][y][z] = true;
		queue.add(new int[]{x, y, z});
	}
}