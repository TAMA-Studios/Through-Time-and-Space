/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import com.code.tama.triggerapi.NativeLoader;
import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.client.OccupancyGrid;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.core.config.TTSConfig;
import com.code.tama.tts.core.networking.Networking;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Off-thread chunk geometry gatherer. <br />
 * Phase 1 (block data collection) runs in Java -- it has to, since it touches
 * MC chunk/light APIs. Phases 2 and 3 (flood-fill BFS + exposed-face detection
 * with behind-portal culling) are handed off to native Rust. <br />
 * Two arrays drive the algorithm: solid -- anything non-air; determines what
 * gets rendered blocksFlow -- full opaque cubes only (isSolidRender); the BFS
 * barrier <br />
 * Keeping these separate is critical. The BFS models exterior air, so it must
 * flow through glass, leaves, slabs, snow layers, redstone dust, piston heads,
 * etc. If any of those blocked the BFS, the solid blocks behind/under them
 * would never receive a reachable neighbour and would be incorrectly skipped.
 * <br />
 * <br />
 * NOTE: {@code solid[]} also gets packed into a {@link BitSet} and handed off
 * as {@link #lastSolidBits} (+ dimensions/origin) after gathering completes,
 * even though most of it never becomes a rendered {@code BotiBlockContainer}.
 * That full, pre-culling volume is what the client-side AO pass needs for real
 * occlusion contrast -- see {@code OccupancyGrid} / {@code BOTIUtils}.
 */
public class ChunkGatheringThread extends Thread {

	static {
		NativeLoader.load("tts_native");
	}

	// -- Native (Rust) ---------------------------------------------------------

	/**
	 * BFS flood-fill from all 6 faces of the bounding box.
	 *
	 * @param blocksFlow
	 *            flat boolean array -- true ONLY for full opaque cubes
	 * @param sizeX/Y/Z
	 *            array dimensions
	 * @return flat boolean array of cells reachable from outside, same layout
	 */
	private static native boolean[] floodFill(boolean[] blocksFlow, int sizeX, int sizeY, int sizeZ);

	/**
	 * Finds every non-air block that has at least one reachable neighbour, then
	 * culls blocks behind the portal.
	 *
	 * @param solid
	 *            flat boolean array -- true for any non-air block
	 * @param reachable
	 *            output of floodFill
	 * @param facing
	 *            0=+Z 1=-Z 2=+X 3=-X
	 * @param originX/Y/Z
	 *            local coords of targetPos within the array
	 * @return flat indices of blocks to emit as BotiBlockContainers
	 */
	private static native int[] findExposedBlocks(boolean[] solid, boolean[] reachable, int sizeX, int sizeY, int sizeZ,
			int originX, int originY, int originZ, int facing);

	// -- Fields ----------------------------------------------------------------

	private static final int MAX_BLOCKS_PER_BATCH = 40_000;

	private final int chunks;
	private final ServerLevel level;
	private final ServerLevel targetLevel;
	private final BlockPos targetPos;
	private final float yaw;
	private int lastLight = 15;

	@Nullable private final AbstractPortalTile portalTile;
	@Nullable private final BiConsumer<List<BotiBlockContainer>, Integer> resultCallback;

	/**
	 * Full pre-culling occupancy for the volume gathered in the most recent
	 * {@link #run()}, exposed so callers (packet senders) can ship it to the client
	 * alongside the container batches. Null until a gather completes.
	 */
	@Nullable private volatile OccupancyGrid lastOccupancyGrid;

	/** PORTAL mode */
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

	/** TELEPORT mode */
	public ChunkGatheringThread(int chunks, ServerLevel sourceLevel, ServerLevel destLevel, BlockPos targetPos,
			float yaw,
			@org.jetbrains.annotations.Nullable BiConsumer<List<BotiBlockContainer>, Integer> resultCallback) {
		this.setName("BOTIChunkGatheringThread");
		this.chunks = chunks;
		this.level = sourceLevel;
		this.portalTile = null;
		this.targetLevel = destLevel;
		this.targetPos = targetPos;
		this.yaw = yaw;
		this.resultCallback = resultCallback;
	}

	/**
	 * @return the occupancy grid from the most recently completed gather, or null
	 *         if none has completed yet. Read this AFTER run() finishes (e.g. from
	 *         the same point batches get sent) to attach it to the outgoing
	 *         packet(s).
	 */
	@Nullable public OccupancyGrid getLastOccupancyGrid() {
		return lastOccupancyGrid;
	}

	@Override
	public void run() {
		if (portalTile == null) {
			TTSMod.LOGGER.debug("[ChunkGatheringThread] Portal tile is null!");
			return;
		}

		if (targetLevel == null) {
			TTSMod.LOGGER.error("[ChunkGatheringThread] targetLevel is null – aborting gather.");
			return;
		}

		// Encode facing as int for Rust: 0=+Z 1=-Z 2=+X 3=-X
		float localYaw = yaw;
		int facing;
		if (localYaw >= -45 && localYaw < 45)
			facing = 0;
		else if (localYaw >= 135 || localYaw < -135)
			facing = 1;
		else if (localYaw >= 45 && localYaw < 135)
			facing = 2;
		else
			facing = 3;

		try {
			ArrayList<BotiBlockContainer> containers = new ArrayList<>();
			ArrayList<List<BotiBlockContainer>> batches = new ArrayList<>();

			int chunksToRender = Math.min(this.chunks, TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get());

			int uMin = -chunksToRender / 2;
			int uMax = chunksToRender / 2;
			int vMin = -chunksToRender / 2;
			int vMax = chunksToRender / 2;

			// -- Base coordinates adjustments for 3 sections --
			int baseChunkX = (targetPos.getX() >> 4);
			int baseChunkZ = (targetPos.getZ() >> 4);

			// Section base Y starts 1 block below targetPos area or standard 16-block
			// alignment:
			int sectionBaseY = (targetPos.getY() - 16) & ~15;
			int sectionBaseYAbove = targetPos.getY() & ~15;
			int sectionBaseYHigher = (targetPos.getY() + 16) & ~15; // NEW higher section

			int worldXMin = (baseChunkX + uMin + 1) * 16;
			int worldXMax = (baseChunkX + uMax) * 16 + 15;
			int worldZMin = (baseChunkZ + vMin + 1) * 16;
			int worldZMax = (baseChunkZ + vMax) * 16 + 15;

			int worldYMin = sectionBaseY;
			int worldYMax = sectionBaseYHigher + 15; // Extended to cover the 3rd section

			int sizeX = worldXMax - worldXMin + 1;
			int sizeY = worldYMax - worldYMin + 1;
			int sizeZ = worldZMax - worldZMin + 1;
			int total = sizeX * sizeY * sizeZ;

			// All flat arrays -- contiguous memory, index = x*sY*sZ + y*sZ + z
			boolean[] solid = new boolean[total]; // non-air → render candidate
			boolean[] blocksFlow = new boolean[total]; // full opaque only → BFS barrier
			BlockState[] blockStates = new BlockState[total];
			FluidState[] fluidStates = new FluidState[total];
			boolean[] teLocations = new boolean[total];
			BlockEntity[] tileEntities = new BlockEntity[total];
			int[] packedLights = new int[total];

			// -- Phase 1: gather block data ------------------------------------
			for (int u = uMin + 1; u < uMax; u++) {
				for (int v = vMin + 1; v < vMax; v++) {
					ChunkPos chunkPos = new ChunkPos(baseChunkX + u, baseChunkZ + v);
					ChunkAccess chunk = targetLevel.getChunkSource().getChunk(chunkPos.x, chunkPos.z, ChunkStatus.FULL,
							true);
					if (chunk == null)
						continue;

					targetLevel.getChunkSource().getLightEngine().lightChunk(chunk, false).join();
					LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY() - 16));
					LevelChunkSection sectionAbove = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));
					LevelChunkSection sectionHigher = chunk.getSection(chunk.getSectionIndex(targetPos.getY() + 16)); // NEW
					// section
					// fetch

					for (int y = 0; y < 16; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								int gx = chunkPos.getMinBlockX() + x;
								int gz = chunkPos.getMinBlockZ() + z;
								int lx = gx - worldXMin;
								int lz = gz - worldZMin;

								if (lx < 0 || lx >= sizeX || lz < 0 || lz >= sizeZ)
									continue;

								// -- 1. Lower section -------------------------
								gatherSection(sectionBaseY, worldYMin, sizeY, sizeZ, solid, blocksFlow, blockStates,
										fluidStates, teLocations, tileEntities, packedLights, chunk, section, y, x, z,
										gx, gz, lx, lz);

								// -- 2. Above section -------------------------
								gatherSection(sectionBaseYAbove, worldYMin, sizeY, sizeZ, solid, blocksFlow,
										blockStates, fluidStates, teLocations, tileEntities, packedLights, chunk,
										sectionAbove, y, x, z, gx, gz, lx, lz);

								// -- 3. Higher section ------------------
								if (sectionHigher != null) {
									gatherSection(sectionBaseYHigher, worldYMin, sizeY, sizeZ, solid, blocksFlow,
											blockStates, fluidStates, teLocations, tileEntities, packedLights, chunk,
											sectionHigher, y, x, z, gx, gz, lx, lz);
								}

							}
						}
					}
				}
			}

			// -- Phase 2: BFS flood-fill (Rust) -------------------------------
			// Pass blocksFlow -- NOT solid -- so the BFS can flow through
			// transparent and partial blocks.
			boolean[] reachable = floodFill(blocksFlow, sizeX, sizeY, sizeZ);

			// -- Phase 3: exposed face detection + culling (Rust) -------------
			// Pass solid -- emit every non-air block with a reachable neighbor,
			// minus anything behind the portal.
			int originX = targetPos.getX() - worldXMin;
			int originY = targetPos.getY() - worldYMin;
			int originZ = targetPos.getZ() - worldZMin;

			int[] exposedIndices = findExposedBlocks(solid, reachable, sizeX, sizeY, sizeZ, originX, originY, originZ,
					facing);

			// Stash the FULL pre-culling occupancy (not just what got exposed) --
			// the client-side AO pass needs this for real occlusion contrast
			// against interior blocks that never get sent as containers.
			BitSet solidBits = OccupancyGrid.pack(solid);
			this.lastOccupancyGrid = new OccupancyGrid(solidBits, sizeX, sizeY, sizeZ, originX, originY, originZ);

			// -- Phase 4: emit BotiBlockContainers (Java -- needs MC objects) --
			for (int fi : exposedIndices) {
				int lx = fi / (sizeY * sizeZ);
				int rem = fi % (sizeY * sizeZ);
				int ly = rem / sizeZ;
				int lz = rem % sizeZ;

				BlockState state = blockStates[fi];
				if (state == null || state.isAir())
					continue;

				int globalX = worldXMin + lx;
				int globalY = worldYMin + ly;
				int globalZ = worldZMin + lz;

				BlockPos relPos = new BlockPos(globalX - targetPos.getX(), globalY - targetPos.getY(),
						globalZ - targetPos.getZ());

				FluidState fluid = fluidStates[fi];
				int packed = packedLights[fi];
				if (packed == 0)
					packed = lastLight;
				lastLight = packed;

				if (fluid == null || fluid.isEmpty())
					containers.add(new BotiBlockContainer(targetLevel, packed, relPos, state));

				if (teLocations[fi])
					containers.add(new BotiBlockContainer(targetLevel, state, relPos, packed, true,
							tileEntities[fi].saveWithFullMetadata()));
				else
					containers.add(new BotiBlockContainer(targetLevel, state, fluid, relPos, packed));

				if (containers.size() >= MAX_BLOCKS_PER_BATCH - 1) {
					batches.add(new ArrayList<>(containers));
					containers.clear();
				}
			}

			if (!containers.isEmpty())
				batches.add(new ArrayList<>(containers));

			// -- Phase 5: deliver ----------------------------------------
			if (resultCallback != null) {
				int totalBatches = batches.size();
				for (List<BotiBlockContainer> batch : batches)
					resultCallback.accept(batch, totalBatches);

			} else {
				TTSMod.LOGGER.debug("[ChunkGatheringThread] Sending {} portal batch packet(s).", batches.size());
				// Attach the occupancy grid to ONLY the first packet -- it covers the
				// whole gathered volume regardless of which batch a given block
				// landed in, so there's no reason to resend it once per batch.
				// If batches ends up empty (nothing exposed this gather), the
				// occupancy data simply won't be sent this round; that's fine since
				// there'd be no containers to shade with it anyway.
				for (int i = 0; i < batches.size(); i++) {
					final int idx = i;
					final int total2 = batches.size();
					OccupancyGrid occupancyForThisPacket = (idx == 0) ? this.lastOccupancyGrid : null;
					Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
						assert portalTile.getLevel() != null;
						return portalTile.getLevel().dimension();
					}), new PortalChunkDataPacketS2C(portalTile.getBlockPos(), batches.get(idx), idx, total2,
							occupancyForThisPacket));
				}
			}

		} catch (Exception e) {
			TTSMod.LOGGER.error("[CGT] Exception during gather: {}", e.getMessage(), e);
		}

		super.run();
	}

	private void gatherSection(int base, int worldYMin, int sizeY, int sizeZ, boolean[] solid, boolean[] blocksFlow,
			BlockState[] blockStates, FluidState[] fluidStates, boolean[] teLocations, BlockEntity[] tileEntities,
			int[] packedLights, ChunkAccess chunk, LevelChunkSection sectionAbove, int y, int x, int z, int gx, int gz,
			int lx, int lz) {
		int gy2 = base + y;
		int ly2 = gy2 - worldYMin;
		if (ly2 >= 0 && ly2 < sizeY) {
			int fi2 = lx * sizeY * sizeZ + ly2 * sizeZ + lz;
			BlockState stateA = sectionAbove.getBlockState(x, y, z);
			FluidState fluidA = sectionAbove.getFluidState(x, y, z);
			BlockPos gpos = new BlockPos(gx, gy2, gz);

			assert portalTile != null;
			if (!gpos.equals(portalTile.getTargetPos())) {
				boolean isAirA = stateA.isAir();
				solid[fi2] = !isAirA;
				blocksFlow[fi2] = !isAirA && stateA.isSolidRender(chunk, gpos);
				blockStates[fi2] = stateA;
				fluidStates[fi2] = fluidA;
				BlockEntity teA = chunk.getBlockEntity(gpos);
				teLocations[fi2] = teA != null;
				tileEntities[fi2] = teA;
				packedLights[fi2] = targetLevel.getMaxLocalRawBrightness(gpos);
			}
		}
	}
}