/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class SkyBlock {
    private static ShaderInstance skyShader;
    private static int skyWidth = -1;
    private static int skyHeight = -1;
    private static TextureTarget skyRenderTarget;
    public static boolean updateSky = false;
    private static boolean isRenderingSky = false;

    public static final RenderType SKY_RENDER_TYPE = RenderType.create(
            "sky",
            DefaultVertexFormat.POSITION,
            VertexFormat.Mode.QUADS,
            256,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(new RenderStateShard.ShaderStateShard(SkyBlock::getSkyShader))
                    .setTextureState(new RenderStateShard.EmptyTextureStateShard(SkyBlock::setSkyTexture, () -> {}))
                    .createCompositeState(false));

    public static void init() {}

    public static ShaderInstance getSkyShader() {
        return skyShader;
    }

    public static void setSkyShader(ShaderInstance shader) {
        skyShader = shader;
    }

    private static void setSkyTexture() {
        if (skyRenderTarget != null) {
            RenderSystem.setShaderTexture(0, skyRenderTarget.getColorTextureId());
        } else {
            RenderSystem.setShaderTexture(0, 0);
        }
    }

    public record RenderData(PoseStack poseStack, float partialTick, Matrix4f projectionMatrix) {}

    public static void renderSky(RenderData renderData) {
        if (isRenderingSky) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();
        int ww = window.getWidth();
        int wh = window.getHeight();

        if (ww <= 0 || wh <= 0) {
            return;
        }

        boolean update = false;

        if (skyRenderTarget == null || skyWidth != ww || skyHeight != wh) {
            update = true;
            skyWidth = ww;
            skyHeight = wh;
        }

        if (update) {
            if (skyRenderTarget != null) {
                skyRenderTarget.destroyBuffers();
            }

            skyRenderTarget = new TextureTarget(skyWidth, skyHeight, true, Minecraft.ON_OSX);
        }

        //        if (irisLoaded) IrisCompat.preRender(mc.levelRenderer);
        mc.gameRenderer.setRenderBlockOutline(false);
        mc.levelRenderer.graphicsChanged();
        skyRenderTarget.bindWrite(true);

        isRenderingSky = true;
        RenderTarget mainRenderTarget = mc.getMainRenderTarget();
        renderActualSky(mc, renderData);
        isRenderingSky = false;

        mc.gameRenderer.setRenderBlockOutline(true);
        skyRenderTarget.unbindRead();
        skyRenderTarget.unbindWrite();
        mc.levelRenderer.graphicsChanged();
        mainRenderTarget.bindWrite(true);
        //        if (irisLoaded) IrisCompat.postRender(mc.levelRenderer);
    }

    public static void renderActualSky(Minecraft mc, RenderData renderData) {
        if (mc == null || mc.level == null || mc.player == null) {
            return;
        }

        PoseStack poseStack = renderData.poseStack();
        final float delta = renderData.partialTick();
        Matrix4f projectionMatrix = renderData.projectionMatrix();
        LevelRenderer levelRenderer = mc.levelRenderer;
        LevelRendererBOS levelRendererBOS = (LevelRendererBOS) levelRenderer;
        GameRenderer gameRenderer = mc.gameRenderer;
        final Camera camera = gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        LightTexture lightTexture = gameRenderer.lightTexture();

        FogRenderer.setupColor(
                camera,
                delta,
                mc.level,
                mc.options.getEffectiveRenderDistance(),
                gameRenderer.getDarkenWorldAmount(delta));
        FogRenderer.levelFogColor();
        RenderSystem.clear(16640, Minecraft.ON_OSX);
        final float renderDistance = gameRenderer.getRenderDistance();
        final boolean hasSpecialFog = mc.level.effects().isFoggyAt(Mth.floor(cameraPos.x), Mth.floor(cameraPos.z))
                || mc.gui.getBossOverlay().shouldCreateWorldFog();
        FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, renderDistance, hasSpecialFog, delta);
        RenderSystem.setShader(GameRenderer::getPositionShader);
        levelRenderer.renderSky(
                poseStack,
                projectionMatrix,
                delta,
                camera,
                false,
                () -> FogRenderer.setupFog(camera, FogRenderer.FogMode.FOG_SKY, renderDistance, hasSpecialFog, delta));

        PoseStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushPose();
        modelViewStack.mulPoseMatrix(poseStack.last().pose());
        RenderSystem.applyModelViewMatrix();

        if (mc.options.getCloudsType() != CloudStatus.OFF) {
            RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            levelRenderer.renderClouds(poseStack, projectionMatrix, delta, cameraPos.x, cameraPos.y, cameraPos.z);
        }

        RenderSystem.depthMask(false);
        levelRendererBOS.BOS$renderSnowAndRain(lightTexture, delta, cameraPos.x, cameraPos.y, cameraPos.z);

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        modelViewStack.popPose();
        RenderSystem.applyModelViewMatrix();
        FogRenderer.setupNoFog();
    }
}
