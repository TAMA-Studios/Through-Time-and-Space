/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The actual "walk the registry and draw everything" logic. Both
 * AnimatedBlockLevelRenderer (Forge event) and AnimatedBlockRenderMixin
 * (bytecode injection) call this, identical draw code either way, only the
 * thing that triggers it per frame differs.
 */
public class AnimatedBlockRenderCore {

	private static final int RENDER_DISTANCE_CHUNKS = 12;

	public static void renderAll(Level level, Vec3 camPos, PoseStack poseStack, MultiBufferSource.BufferSource buffer,
			float partialTick) {
		int camChunkX = (int) camPos.x >> 4;
		int camChunkZ = (int) camPos.z >> 4;
		boolean usedAnyBuffer = false;

		for (Map.Entry<Long, Map<BlockPos, AnimatedBlockRegistry.Entry>> chunkEntry : AnimatedBlockRegistry
				.allChunks()) {
			long key = chunkEntry.getKey();
			int chunkX = ChunkPos.getX(key);
			int chunkZ = ChunkPos.getZ(key);
			if (Math.abs(chunkX - camChunkX) > RENDER_DISTANCE_CHUNKS
					|| Math.abs(chunkZ - camChunkZ) > RENDER_DISTANCE_CHUNKS)
				continue;

			for (Map.Entry<BlockPos, AnimatedBlockRegistry.Entry> e : chunkEntry.getValue().entrySet()) {
				BlockPos pos = e.getKey();
				AnimatedBlockRegistry.Entry entry = e.getValue();

				if (!(level.getBlockState(pos).getBlock() instanceof IGeoAnimatedBlock current)
						|| current != entry.block) {
					AnimatedBlockRegistry.remove(pos);
					continue;
				}

				GeoModel model = entry.block.getGeoModel();
				RenderType type = RenderType.entityCutout(entry.block.getGeoTexture());
				var consumer = buffer.getBuffer(type);
				usedAnyBuffer = true;

				float nowTicks = level.getGameTime();
				entry.player.apply(model, nowTicks, partialTick);

				int packedLight = LevelRenderer.getLightColor(level, pos);

				poseStack.pushPose();
				poseStack.translate(pos.getX() - camPos.x + 0.5, pos.getY() - camPos.y, pos.getZ() - camPos.z + 0.5);
				entry.block.transformRender(level.getBlockState(pos), poseStack, buffer, partialTick);
				GeoRenderer.render(model, poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
				poseStack.popPose();
			}
		}

		if (usedAnyBuffer)
			buffer.endBatch();
	}
}
