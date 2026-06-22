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

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.network.PacketDistributor;

import com.code.tama.triggerapi.NativeLoader;
import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;

/**
 * Off-thread chunk geometry gatherer.
 *
 * Phase 1 (block data collection) runs in Java -- it has to, since it touches
 * MC chunk/light APIs. Phases 2 and 3 (flood-fill BFS + exposed-face detection
 * with behind-portal culling) are handed off to native Rust.
 *
 * Two arrays drive the algorithm: solid -- anything non-air; determines what
 * gets rendered blocksFlow -- full opaque cubes only (isSolidRender); the BFS
 * barrier
 *
 * Keeping these separate is critical. The BFS models exterior air, so it must
 * flow through glass, leaves, slabs, snow layers, redstone dust, piston heads,
 * etc. If any of those blocked the BFS, the solid blocks behind/under them
 * would never receive a reachable neighbour and would be incorrectly skipped.
 */
@SuppressWarnings({"unchecked", "deprecation"})
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

	/** PORTAL mode -- legacy */
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

	/** TELEPORT mode */
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
			// Must stay in Java -- accesses chunk sections, light engine, block entities.
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

					for (int y = 0; y < 16; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {

								// -- lower section ----------------------------
								int gx = chunkPos.getMinBlockX() + x;
								int gy = sectionBaseY + y;
								int gz = chunkPos.getMinBlockZ() + z;

								int lx = gx - worldXMin;
								int ly = gy - worldYMin;
								int lz = gz - worldZMin;

								if (lx < 0 || lx >= sizeX || ly < 0 || ly >= sizeY || lz < 0 || lz >= sizeZ)
									continue;

								int fi = lx * sizeY * sizeZ + ly * sizeZ + lz;

								BlockState state = section.getBlockState(x, y, z);
								FluidState fluid = section.getFluidState(x, y, z);
								BlockPos pos = new BlockPos(gx, gy, gz);

								if (portalTile != null && pos.equals(portalTile.getTargetPos()))
									continue;

								boolean isAir = state.isAir();
								solid[fi] = !isAir;
								// BFS barrier: only full opaque cubes. Glass, leaves, slabs, snow,
								// redstone, pistons heads etc. must NOT block the flood-fill or the
								// blocks they sit on/next-to won't get reachable neighbours.
								blocksFlow[fi] = !isAir && state.isSolidRender(chunk, pos);
								blockStates[fi] = state;
								fluidStates[fi] = fluid;
								BlockEntity te = chunk.getBlockEntity(pos);
								teLocations[fi] = te != null;
								tileEntities[fi] = te;
								packedLights[fi] = targetLevel.getMaxLocalRawBrightness(pos);

								// -- upper section ----------------------------
								int gyA = sectionBaseYAbove + y;
								int lyA = gyA - worldYMin;
								if (lyA < 0 || lyA >= sizeY)
									continue;

								int fiA = lx * sizeY * sizeZ + lyA * sizeZ + lz;

								BlockState stateA = sectionAbove.getBlockState(x, y, z);
								FluidState fluidA = sectionAbove.getFluidState(x, y, z);
								BlockPos gpos = new BlockPos(gx, gyA, gz);

								if (portalTile != null && gpos.equals(portalTile.getTargetPos()))
									continue;

								boolean isAirA = stateA.isAir();
								solid[fiA] = !isAirA;
								blocksFlow[fiA] = !isAirA && stateA.isSolidRender(chunk, gpos);
								blockStates[fiA] = stateA;
								fluidStates[fiA] = fluidA;
								BlockEntity teA = chunk.getBlockEntity(gpos);
								teLocations[fiA] = teA != null;
								tileEntities[fiA] = teA;
								packedLights[fiA] = targetLevel.getMaxLocalRawBrightness(gpos);
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
			// Pass solid -- emit every non-air block with a reachable neighbour,
			// minus anything behind the portal.
			int originX = targetPos.getX() - worldXMin;
			int originY = targetPos.getY() - worldYMin;
			int originZ = targetPos.getZ() - worldZMin;

			int[] exposedIndices = findExposedBlocks(solid, reachable, sizeX, sizeY, sizeZ, originX, originY, originZ,
					facing);

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

			// -- Phase 5: deliver --------──────────────────────────────────────
			if (resultCallback != null) {
				int totalBatches = batches.size();
				for (List<BotiBlockContainer> batch : batches)
					resultCallback.accept(batch, totalBatches);

			} else if (portalTile != null) {
				TTSMod.LOGGER.debug("[ChunkGatheringThread] Sending {} portal batch packet(s).", batches.size());
				for (int i = 0; i < batches.size(); i++) {
					final int idx = i;
					final int total2 = batches.size();
					Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
						assert portalTile.getLevel() != null;
						return portalTile.getLevel().dimension();
					}), new PortalChunkDataPacketS2C(portalTile.getBlockPos(), batches.get(idx), idx, total2));
				}
			}

		} catch (Exception e) {
			TTSMod.LOGGER.error("[CGT] Exception during gather: {}", e.getMessage(), e);
		}

		super.run();
	}
}