/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.networking.Networking;
import lombok.AllArgsConstructor;
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

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ChunkGatheringThread extends Thread {
	int chunks;
	ServerLevel level;
	AbstractPortalTile portalTile;
	BlockPos targetPos;

	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		this.setName("BOTIChunkGatheringThread");

		float yaw = portalTile.targetY;

		boolean facingPosZ = yaw >= -45 && yaw < 45;
		boolean facingNegZ = yaw >= 135 || yaw < -135;
		boolean facingPosX = yaw >= 45 && yaw < 135;
		boolean facingNegX = yaw >= -135 && yaw < -45;
		BlockPos portalPos = portalTile.getTargetPos();
		int maxBlocks = 40000;

		try {
			ArrayList<BotiBlockContainer> containers = new ArrayList<>();
			ArrayList<List<BotiBlockContainer>> containerLists = new ArrayList<>();

			int chunksToRender = Math.min(this.chunks, TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get());

			int uMin = -chunksToRender / 2;
			int uMax =  chunksToRender / 2;
			int vMin = -chunksToRender / 2;
			int vMax =  chunksToRender / 2;

			int baseChunkX = (targetPos.getX() >> 4);
			int baseChunkZ = (targetPos.getZ() >> 4);
			int sectionBaseY     = (targetPos.getY() - 16) & ~15;
			int sectionBaseYAbove = targetPos.getY() & ~15;

			// World-space bounds of the entire gathered region (in blocks)
			int worldXMin = (baseChunkX + uMin + 1) * 16;
			int worldXMax = (baseChunkX + uMax)     * 16 + 15;
			int worldZMin = (baseChunkZ + vMin + 1) * 16;
			int worldZMax = (baseChunkZ + vMax)     * 16 + 15;
			int worldYMin = sectionBaseY;
			int worldYMax = sectionBaseYAbove + 15;

			int sizeX = worldXMax - worldXMin + 1;
			int sizeY = worldYMax - worldYMin + 1;
			int sizeZ = worldZMax - worldZMin + 1;

			// Build a solid/passable grid for the whole region
			// We'll collect all BlockStates first, then flood-fill, then emit containers.
			// solid[x][y][z] == true means block is opaque/solid (not passable by flood fill)
			boolean[][][] solid = new boolean[sizeX][sizeY][sizeZ];

			// We also need to store states for later container creation
			BlockState[][][] blockStates       = new BlockState[sizeX][sizeY][sizeZ];
			FluidState[][][] fluidStates       = new FluidState[sizeX][sizeY][sizeZ];
			int[][][]        packedLights      = new int[sizeX][sizeY][sizeZ];

			for (int u = uMin + 1; u < uMax; u++) {
				for (int v = vMin + 1; v < vMax; v++) {
					ChunkPos chunkPos = new ChunkPos(baseChunkX + u, baseChunkZ + v);
					level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true);
					LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);

					LevelChunkSection section      = chunk.getSection(chunk.getSectionIndex(targetPos.getY() - 16));
					LevelChunkSection sectionAbove = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

					if (!chunk.isLightCorrect()) {
						level.getServer().submit(() -> {
							for (int ly = level.getMinBuildHeight(); ly < level.getMaxBuildHeight(); ly += 16) {
								level.getLightEngine().checkBlock(new BlockPos(
										chunkPos.getMiddleBlockX(), ly, chunkPos.getMiddleBlockZ()));
							}
						}).join();
						Thread.sleep(50);
					}

					level.getServer().submit(() -> {
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

									BlockState state  = section.getBlockState(x, y, z);
									FluidState fluid  = section.getFluidState(x, y, z);

									blockStates[lx][ly][lz] = state;
									fluidStates[lx][ly][lz] = fluid;
									solid[lx][ly][lz] = !state.isAir();

									BlockPos samplePos = new BlockPos(gx, gy, gz);
									DataLayer bl = level.getLightEngine().getLayerListener(LightLayer.BLOCK)
											.getDataLayerData(SectionPos.of(samplePos));
									DataLayer sl = level.getLightEngine().getLayerListener(LightLayer.SKY)
											.getDataLayerData(SectionPos.of(samplePos));
									int blockLight = bl != null ? bl.get(gx & 15, gy & 15, gz & 15) : 0;
									int skyLight   = sl != null ? sl.get(gx & 15, gy & 15, gz & 15) : 15;
									packedLights[lx][ly][lz] = LightTexture.pack(blockLight, skyLight);

									// Upper section ---
									int gyA = sectionBaseYAbove + y;
									int lyA = gyA - worldYMin;
									if (lyA < 0 || lyA >= sizeY) continue;

									BlockState stateA = sectionAbove.getBlockState(x, y, z);
									FluidState fluidA = sectionAbove.getFluidState(x, y, z);

									blockStates[lx][lyA][lz] = stateA;
									fluidStates[lx][lyA][lz] = fluidA;
									solid[lx][lyA][lz] = !stateA.isAir() && stateA.isSolidRender(chunk, new BlockPos(gx, gyA, gz));

									BlockPos samplePosA = new BlockPos(gx, gyA, gz);
									DataLayer blA = level.getLightEngine().getLayerListener(LightLayer.BLOCK)
											.getDataLayerData(SectionPos.of(samplePosA));
									DataLayer slA = level.getLightEngine().getLayerListener(LightLayer.SKY)
											.getDataLayerData(SectionPos.of(samplePosA));
									int blockLightA = blA != null ? blA.get(gx & 15, gyA & 15, gz & 15) : 0;
									int skyLightA   = slA != null ? slA.get(gx & 15, gyA & 15, gz & 15) : 15;
									packedLights[lx][lyA][lz] = LightTexture.pack(blockLightA, skyLightA);
								}
							}
						}
					}).join();
				}
			}

			// Flood fill
			boolean[][][] reachable = new boolean[sizeX][sizeY][sizeZ];
			// Use an iterative BFS with an ArrayDeque to avoid stack overflow
			java.util.ArrayDeque<int[]> queue = new java.util.ArrayDeque<>();

			// Seed from all 6 faces of the bounding box
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

			int[] dx = {1,-1,0,0,0,0};
			int[] dy = {0,0,1,-1,0,0};
			int[] dz = {0,0,0,0,1,-1};

			while (!queue.isEmpty()) {
				int[] cell = queue.poll();
				int cx = cell[0], cy = cell[1], cz = cell[2];
				for (int d = 0; d < 6; d++) {
					tryEnqueue(queue, reachable, solid, cx + dx[d], cy + dy[d], cz + dz[d]);
				}
			}

			for (int lx = 0; lx < sizeX; lx++) {
				for (int ly = 0; ly < sizeY; ly++) {
					for (int lz = 0; lz < sizeZ; lz++) {
						BlockState state = blockStates[lx][ly][lz];
						if (state == null || state.isAir()) continue;

						// Check if any neighbor is reachable (= exposed face)
						boolean exposed = false;
						for (int d = 0; d < 6; d++) {
							int nx = lx + dx[d], ny = ly + dy[d], nz = lz + dz[d];
							if (nx < 0 || nx >= sizeX || ny < 0 || ny >= sizeY || nz < 0 || nz >= sizeZ) {
								exposed = false; // border of region = assume not exposed
								break;
							}
							if (reachable[nx][ny][nz]) {
								exposed = true;
								break;
							}
						}
						if (!exposed) continue;

						// Behind-portal cull
						int globalX = worldXMin + lx;
						int globalY = worldYMin + ly;
						int globalZ = worldZMin + lz;

						BlockPos relPos = new BlockPos(
								globalX - targetPos.getX(),
								globalY - targetPos.getY(),
								globalZ - targetPos.getZ()
						);

						boolean isBehind;
						if (facingPosZ)      isBehind = relPos.getZ() >= 0;
						else if (facingNegZ) isBehind = relPos.getZ() <= 0;
						else if (facingPosX) isBehind = relPos.getX() >= 0;
						else                 isBehind = relPos.getX() <= 0;
						if (isBehind) continue;

						FluidState fluid = fluidStates[lx][ly][lz];
						int packed = packedLights[lx][ly][lz];

						if (fluid == null || fluid.isEmpty())
							containers.add(new BotiBlockContainer(level, packed, relPos, state));
						else
							containers.add(new BotiBlockContainer(level, state, fluid, relPos, packed));

						if (containers.size() >= maxBlocks - 1) {
							containerLists.add((List<BotiBlockContainer>) containers.clone());
							containers.clear();
						}
					}
				}
			}

			if (!containers.isEmpty()) {
				containerLists.add((List<BotiBlockContainer>) containers.clone());
				containers.clear();
			}

			System.out.println("Sending packets for BOTI");
			for (int i = 0; i < containerLists.size(); i++) {
                Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
					assert portalTile.getLevel() != null;
					return portalTile.getLevel().dimension();
				}), new PortalChunkDataPacketS2C(portalTile.getBlockPos(), containerLists.get(i), i, containerLists.size()));
			}

		} catch (Exception e) {
			TTSMod.LOGGER.error("Exception in packet construction: {}", e.getMessage());
		}
		super.run();
	}

	// Helper — only enqueue if in-bounds, not solid, and not already visited
	private static void tryEnqueue(java.util.ArrayDeque<int[]> queue,
								   boolean[][][] reachable,
								   boolean[][][] solid,
								   int x, int y, int z) {
		if (x < 0 || x >= solid.length)    return;
		if (y < 0 || y >= solid[0].length) return;
		if (z < 0 || z >= solid[0][0].length) return;
		if (solid[x][y][z] || reachable[x][y][z]) return;
		reachable[x][y][z] = true;
		queue.add(new int[]{x, y, z});
	}

}
