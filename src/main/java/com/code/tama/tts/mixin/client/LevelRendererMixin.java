/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import com.code.tama.tts.client.renderers.worlds.IHelpWithLevelRenderer;
import com.code.tama.tts.client.renderers.worlds.SkyBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin
		implements
			ResourceManagerReloadListener,
			AutoCloseable,
			IHelpWithLevelRenderer {

	@Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch(Lnet/minecraft/client/renderer/RenderType;)V", ordinal = 6, shift = At.Shift.AFTER))
	private void renderLevel(PoseStack poseStack, float delta, long time, boolean blockOutlines, Camera camera,
			GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f matrix, CallbackInfo ci) {
		((ILevelRendererAccessor) this).getRenderBuffers().bufferSource().endBatch(SkyBlock.SKY_RENDER_TYPE);
	}

	@Override
	public void TTS$renderSnowAndRain(LightTexture lightTexture, float delta, double cameraX, double cameraY,
			double cameraZ) {
		((ILevelRendererAccessor) this).renderSnowAndRain(lightTexture, delta, cameraX, cameraY, cameraZ);
	}
}
