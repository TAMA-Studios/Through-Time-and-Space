/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.code.tama.triggerapi.TriggerAPI;

/**
 * Registers/unregisters animated blocks with AnimatedBlockRegistry. <br />
 * Two entry points, called from your Block subclass (see AnimatedGeoBlockBase
 * below) and from chunk load/unload: 1. Block.onPlace / onRemove ->
 * single-position add/remove, cheap, fires client-side as soon as the block
 * update reaches the client. 2. ChunkEvent.Load -> one-time scan for animated
 * blocks that were already part of a chunk being loaded (rejoin, teleport,
 * etc.). Same order of cost as vanilla's own per-chunk block-entity gathering,
 * so this isn't new overhead on top of what the chunk load already does.
 */
@Mod.EventBusSubscriber(modid = TriggerAPI.MOD_ID, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class AnimatedBlockClientEvents {

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event) {
		if (!(event.getLevel() instanceof ClientLevel level))
			return;
		if (!(event.getChunk() instanceof LevelChunk chunk))
			return;

		LevelChunkSection[] sections = chunk.getSections();
		int minY = level.getMinBuildHeight();
		for (int sIdx = 0; sIdx < sections.length; sIdx++) {
			LevelChunkSection section = sections[sIdx];
			if (section == null || section.hasOnlyAir())
				continue;
			int baseY = minY + sIdx * 16;
			for (int x = 0; x < 16; x++) {
				for (int y = 0; y < 16; y++) {
					for (int z = 0; z < 16; z++) {
						var state = section.getBlockState(x, y, z);
						if (state.getBlock() instanceof IGeoAnimatedBlock geoBlock) {
							BlockPos pos = new BlockPos(chunk.getPos().getMinBlockX() + x, baseY + y,
									chunk.getPos().getMinBlockZ() + z);
							AnimatedBlockRegistry.add(pos, geoBlock);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload event) {
		if (!(event.getLevel() instanceof ClientLevel))
			return;
		AnimatedBlockRegistry.removeChunk(event.getChunk().getPos().x, event.getChunk().getPos().z);
	}
}
