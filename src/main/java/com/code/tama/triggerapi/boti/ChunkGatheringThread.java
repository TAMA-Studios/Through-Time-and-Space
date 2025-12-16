/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti;

import com.code.tama.triggerapi.boti.client.BotiChunkContainer;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;
import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.TTSConfig;
import com.code.tama.tts.TTSMod;
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
//		System.out.println("Gathering chunks for BOTI");
		// Direction axis = Direction.fromYRot(portalTile.targetY);
		BlockPos portalPos = portalTile.getBlockPos();
		int maxBlocks = 50000;

		try {
			ArrayList<BotiChunkContainer> containers = new ArrayList<>();
			ArrayList<List<BotiChunkContainer>> containerLists = new ArrayList<>();
			boolean isSquare = true;
			// \/ Use either client render distance, or server render distance, whichever's smaller
			int chunksToRender = Math.min(this.chunks, TTSConfig.ServerConfig.BOTI_RENDER_DISTANCE.get());
			int uMax; // = (axis.equals(Direction.WEST) ? 1 : chunksToRender / 2);
			int uMin; // = (axis.equals(Direction.EAST) ? 0 : -chunksToRender / 2);
			int vMax; // = (axis.equals(Direction.NORTH) ? 1 : chunksToRender / 2);
			int vMin; // = (axis.equals(Direction.SOUTH) ? 0 : -chunksToRender / 2);

			vMin = -chunksToRender / 2;
			vMax = chunksToRender / 2;
			uMax = chunksToRender / 2;
			uMin = -chunksToRender / 2;

			for (int u = uMin + 1; u < uMax; u++) { // turn either the u or the v to = 0 based on the direction you're
													// viewing from
				for (int v = vMin + 1; v < vMax; v++) {
					ChunkPos chunkPos = new ChunkPos(
							new BlockPos(targetPos.getX() + (u * 16), targetPos.getY(), targetPos.getZ() + (v * 16)));
					level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, true); // Force load chunk
					LevelChunk chunk = level.getChunk(chunkPos.x, chunkPos.z);
					LevelChunkSection section = chunk.getSection(chunk.getSectionIndex(targetPos.getY() - 16));
					LevelChunkSection sectionAbove = chunk.getSection(chunk.getSectionIndex(targetPos.getY()));

					BlockPos relTargetPos = new BlockPos(targetPos.getX() % 16, (targetPos.getY() - 16) % 16,
							targetPos.getZ() % 16);

					BlockPos relTargetPosAbove = new BlockPos(targetPos.getX() % 16, (targetPos.getY()) % 16,
							targetPos.getZ() % 16);

					for (int y = 0; y < 16; y++) {
						for (int x = 0; x < 16; x++) {
							for (int z = 0; z < 16; z++) {
								BlockState state = section.getBlockState(x, y, z);
								BlockState stateAbove = sectionAbove.getBlockState(x, y, z);
								FluidState fluidState = section.getFluidState(x, y, z);
								FluidState fluidStateAbove = sectionAbove.getFluidState(x, y, z);

								if (!state.isAir()) {
									BlockPos pos = new BlockPos(x + (u * 16) - relTargetPos.getX(),
											y - relTargetPos.getY() - 16, z + (v * 16) - relTargetPos.getZ());

									BlockPos posAbove = new BlockPos(x + (u * 16) - relTargetPosAbove.getX(),
											y - relTargetPosAbove.getY(), z + (v * 16) - relTargetPosAbove.getZ());

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
										containers.add(new BotiChunkContainer(level,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY())),
												pos, state));
									else
										containers.add(new BotiChunkContainer(level, state, fluidState, pos,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY()))));

									if (fluidStateAbove.isEmpty())
										containers.add(new BotiChunkContainer(level,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY())),
												posAbove, stateAbove));
									else
										containers.add(new BotiChunkContainer(level, stateAbove, fluidState, posAbove,
												BlockUtils.getPackedLight(level,
														BlockUtils.fromChunkAndLocal(chunkPos, new BlockPos(x, y, z))
																.atY(targetPos.getY()))));
								}
								if (containers.size() >= maxBlocks - 1) {
									containerLists.add((List<BotiChunkContainer>) containers.clone());
									containers.clear();
								}
							}
						}
					}
				}
			}
			if (!containers.isEmpty()) {
				containerLists.add((List<BotiChunkContainer>) containers.clone());
				containers.clear();
			}

			System.out.println("Sending packets for BOTI");
			for (int i = 0; i < containerLists.size(); i++) {
				Networking.INSTANCE.send(PacketDistributor.DIMENSION.with(() -> {
					assert portalTile.getLevel() != null;
					return portalTile.getLevel().dimension();
				}), new PortalChunkDataPacketS2C(portalPos, containerLists.get(i), i, containerLists.size()));
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
