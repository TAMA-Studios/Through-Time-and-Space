/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.Iterator;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Draws everything. AnimatedBlockLevelRenderer (Forge event) and
 * AnimatedBlockRenderMixin (bytecode injection) call this, identical draw code
 * either way, only the thing that triggers it per frame differs.
 */
public class AnimatedBlockRenderCore {

	private static final int RENDER_DISTANCE_BLOCKS = 12 * 16;

	public static void renderAll(Level level, Vec3 camPos, PoseStack poseStack, MultiBufferSource.BufferSource buffer,
			float partialTick) {
		boolean usedAnyBuffer = false;

		Iterator<Map.Entry<Long, AnimatedBlockRegistry.Entry>> it = AnimatedBlockRegistry.all().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Long, AnimatedBlockRegistry.Entry> e = it.next();
			BlockPos pos = BlockPos.of(e.getKey());
			AnimatedBlockRegistry.Entry entry = e.getValue();

			if (Math.abs(pos.getX() - camPos.x) > RENDER_DISTANCE_BLOCKS
					|| Math.abs(pos.getZ() - camPos.z) > RENDER_DISTANCE_BLOCKS)
				continue;

			// Self-heal: drop stale entries instead of trusting onRemove alone.
			if (!(level.getBlockState(pos).getBlock() instanceof IGeoAnimatedBlock current) || current != entry.block) {
				it.remove();
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
			GeoRenderer.render(model, poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
			poseStack.popPose();
		}

		if (usedAnyBuffer)
			buffer.endBatch();
	}
}