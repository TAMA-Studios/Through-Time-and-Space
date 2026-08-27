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
 * Registers/unregisters animated blocks with AnimatedBlockRegistry.
 *
 * Two entry points, called from your Block subclass (see AnimatedGeoBlockBase
 * below) and from chunk load/unload: 1. Block.onPlace / onRemove ->
 * single-position add/remove, cheap, fires client-side as soon as the block
 * update reaches the client. 2. ChunkEvent.Load -> one-time scan for animated
 * blocks that were already part of a chunk being loaded (rejoin, teleport,
 * etc). A palette pre-check (see maybeHas below) means chunks with none of your
 * blocks cost roughly nothing — only sections that could plausibly contain one
 * pay for the full 16x16x16 walk.
 */
@Mod.EventBusSubscriber(modid = TriggerAPI.MOD_ID, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class AnimatedBlockClientEvents {

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event) { // Only runs once on chunk load and only actually does shit
															// if animated blocks exist, so there is basically 0 cost.
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

			// Cheap early-out: PalettedContainer only holds the DISTINCT block states
			// present
			// in this section (typically a handful), so checking it costs nothing close to
			// a
			// full 16x16x16 scan. Skip the section entirely if none of its distinct states
			// are
			// one of ours, instead of visiting all 4096 positions on every chunk load
			// regardless.
			if (!section.getStates().maybeHas(state -> state.getBlock() instanceof IGeoAnimatedBlock))
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