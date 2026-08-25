/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;

/**
 * MIXIN backend: tail-injects vanilla LevelRenderer#renderLevel directly,
 * bypassing Forge's event bus entirely. Deliberately does NOT try to land at
 * the same spot as AFTER_TRANSLUCENT_BLOCKS (that spot is a Forge patch that
 * moves between Forge versions, chasing it would be more fragile than the event
 * itself, not less). Renders after everything else in the frame instead, which
 * is a real trade-off: correct even if something upstream interferes with
 * Forge's event firing, but composites after translucency rather than within
 * it, so expect different depth/blend behavior than the LEVEL_EVENT backend if
 * you compare them side by side. <br />
 * Requires a mixins.json entry and the Mixin annotation processor / refmap step
 * in your build (Forge ships Mixin itself, so no new runtime jar, but this does
 * add real build complexity, that's the trade for bypassing the event bus).
 * <br />
 * Only draws when AnimatedBlockConfig.MODE == MIXIN, so it and
 * AnimatedBlockLevelRenderer never both fire for the same block.
 */
@SuppressWarnings("unused")
@Mixin(LevelRenderer.class)
public class AnimatedBlockRenderMixin {
	@Inject(method = "renderLevel", at = @At("RETURN"))
	private void geoanim$onRenderLevelTail(PoseStack poseStack, float partialTick, long finishNanoTime,
			boolean drawBlockOutline, Camera camera, Object gameRenderer, Object lightTexture, Object projectionMatrix,
			CallbackInfo ci) {

		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.MIXIN)
			return;

		Minecraft mc = Minecraft.getInstance();
		ClientLevel level = mc.level;
		if (level == null)
			return;

		Vec3 camPos = camera.getPosition();
		MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

		AnimatedBlockRenderCore.renderAll(level, camPos, poseStack, buffer, partialTick);
	}
}
