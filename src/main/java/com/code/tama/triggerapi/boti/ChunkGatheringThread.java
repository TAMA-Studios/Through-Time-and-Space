/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;
import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.networking.Networking;
import lombok.AllArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
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

		// System.out.println("Gathering chunks for BOTI");
// Outside the loops, compute the facing direction once
		float yaw = portalTile.targetY;

// Normalize to cardinal: determine if portal faces along X or Z axis
// and which side is "in front"
		boolean facingPosZ = yaw >= -45 && yaw < 45;
		boolean facingNegZ = yaw >= 135 || yaw < -135;
		boolean facingPosX = yaw >= 45 && yaw < 135;
		boolean facingNegX = yaw >= -135 && yaw < -45;
		BlockPos portalPos = portalTile.getTargetPos();
		int maxBlocks = 40000;

		try {
			ArrayList<BotiBlockContainer> containers = new ArrayList<>();
			ArrayList<List<BotiBlockContainer>> containerLists = new ArrayList<>();
			boolean isSquare = true;
			// \/ Use either client render distance, or server render distance, whichever's
			// smaller
			int chunksToRender = Math.min(this.chunks, TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get());
			int uMax; // = (axis.equals(Direction.WEST) ? 1 : chunksToRender / 2);
			int uMin; // = (axis.equals(Direction.EAST) ? 0 : -chunksToRender / 2);
			int vMax; // = (axis.equals(Direction.NORTH) ? 1 : chunksToRender / 2);
			int vMin; // = (axis.equals(Direction.SOUTH) ? 0 : -chunksToRender / 2);

			vMin = -chunksToRender / 2;
			vMax = chunksToRender / 2;
			uMax = chunksToRender / 2;
			uMin = -chunksToRender / 2;

			int baseChunkX = (targetPos.getX() >> 4);
			int baseChunkZ = (targetPos.getZ() >> 4);

			int sectionBaseY = (targetPos.getY() - 16) & ~15;  // floor to nearest multiple of 16
			int sectionBaseYAbove = targetPos.getY() & ~15;

			for (int u = uMin + 1; u < uMax; u++) { // turn either the u or the v to = 0 based on the direction you're
													// viewing from
				for (int v = vMin + 1; v < vMax; v++) {
					ChunkPos chunkPos = new ChunkPos(baseChunkX + u, baseChunkZ + v);
					level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true); // Force load chunk
					LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
					LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY() - 16));
					LevelChunkSection sectionAbove = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

					for (int y = 0; y < 16; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								BlockState state = section.getBlockState(x, y, z);
								BlockState stateAbove = sectionAbove.getBlockState(x, y, z);
								FluidState fluidState = section.getFluidState(x, y, z);
								FluidState fluidStateAbove = sectionAbove.getFluidState(x, y, z);

								if (!state.isAir()) {
									int globalY = sectionBaseY + y;
									int globalYAbove = sectionBaseYAbove + y;

									int globalX = chunkPos.getMinBlockX() + x;
									int globalZ = chunkPos.getMinBlockZ() + z;

									BlockPos pos = new BlockPos(
											globalX - targetPos.getX(),
											globalY - targetPos.getY(),
											globalZ - targetPos.getZ()
									);

									BlockPos posAbove = new BlockPos(
											globalX - targetPos.getX(),
											globalYAbove - targetPos.getY(),
											globalZ - targetPos.getZ()
									);

									boolean isBehind;
									if (facingPosZ)      isBehind = pos.getZ() > 0;
									else if (facingNegZ) isBehind = pos.getZ() < 0;
									else if (facingPosX) isBehind = pos.getX() > 0;
									else                 isBehind = pos.getX() < 0;  // facingNegX

									if (isBehind) continue;

//									boolean isVisible = false;
//
//									for (Direction dir : Direction.values()) {
//										if (section.getBlockState(
//												dir.equals(Direction.EAST) ? x + 1 : dir.equals(Direction.WEST) ? x - 1 : x,
//												dir.equals(Direction.UP) ? y + 1 : dir.equals(Direction.DOWN) ? y - 1 : y,
//												dir.equals(Direction.NORTH) ? z + 1 : dir.equals(Direction.SOUTH) ? z - 1 : z
//										).isAir()) {
//											isVisible = true;
//											break;
//										}
//									}
//
//									if (!isVisible) continue;


									//
									// if(BlockUtils.isBehind(relTargetPos.relative(exteriorAxis), pos,
									// exteriorAxis))
									// continue;

									//
									// if(level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
									// .atY(targetPos.getY())) != null) {
									// BlockEntity entity =
									// level.getBlockEntity(BlockUtils.fromChunkAndLocal(chunkPos, pos)
									// .atY(targetPos.getY()));
									// containers.add(new
									// BotiChunkContainer(level,
									// state,
									// pos,
									// BlockUtils.getPackedLight(
									// level,
									//
									// BlockUtils.fromChunkAndLocal(chunkPos, pos)
									//
									// .atY(targetPos.getY())), true, entity.saveWithFullMetadata()));
									// }

									if (fluidState.isEmpty())
										containers.add(new BotiBlockContainer(level,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY())),
												pos, state));
									else
										containers.add(new BotiBlockContainer(level, state, fluidState, pos,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY()))));

									if (fluidStateAbove.isEmpty())
										containers.add(new BotiBlockContainer(level,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY())),
												posAbove, stateAbove));
									else
										containers.add(new BotiBlockContainer(level, stateAbove, fluidState, posAbove,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY()))));
								}
								if (containers.size() >= maxBlocks - 1) {
									containerLists.add((List<BotiBlockContainer>) containers.clone());
									containers.clear();
								}
							}
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
			// 126142 (Too big)
			// 71267 (prob could go higher before hitting the limit but this works at 6-ish
			// chunks)

		} catch (Exception e) {
			TTSMod.LOGGER.error("Exception in packet construction: {}", e.getMessage());
		}
		super.run();
	}
}
