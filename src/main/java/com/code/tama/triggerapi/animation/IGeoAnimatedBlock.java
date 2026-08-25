/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Implement this on any Block that should be picked up by the no-block-entity
 * animation system. Only used client-side.
 */
public interface IGeoAnimatedBlock {
	GeoModel getGeoModel();
	ResourceLocation getGeoTexture();
	void transformRender(BlockState state, PoseStack poseStack, MultiBufferSource.BufferSource buffer,
	                     float partialTick);
}
