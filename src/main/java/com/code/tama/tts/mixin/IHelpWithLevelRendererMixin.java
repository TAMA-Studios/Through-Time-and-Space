/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import com.code.tama.tts.client.renderers.worlds.IHelpWithLevelRenderer;
import com.code.tama.tts.client.renderers.worlds.SkyBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class IHelpWithLevelRendererMixin
        implements ResourceManagerReloadListener, AutoCloseable, IHelpWithLevelRenderer {

    @Shadow protected abstract void renderSnowAndRain(LightTexture p_109704_, float p_109705_, double p_109706_, double p_109707_, double p_109708_);

    @Shadow @Final private RenderBuffers renderBuffers;

    @Inject(
            method = "renderLevel",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endBatch(Lnet/minecraft/client/renderer/RenderType;)V",
                            ordinal = 6,
                            shift = At.Shift.AFTER))
    private void renderLevelBOS(
            PoseStack poseStack,
            float delta,
            long time,
            boolean blockOutlines,
            Camera camera,
            GameRenderer gameRenderer,
            LightTexture lightTexture,
            Matrix4f matrix,
            CallbackInfo ci) {
        this.renderBuffers.bufferSource().endBatch(SkyBlock.SKY_RENDER_TYPE);
    }

    @Override
    public void TTS$renderSnowAndRain(
            LightTexture lightTexture, float delta, double cameraX, double cameraY, double cameraZ) {
        this.renderSnowAndRain(lightTexture, delta, cameraX, cameraY, cameraZ);
    }
}
