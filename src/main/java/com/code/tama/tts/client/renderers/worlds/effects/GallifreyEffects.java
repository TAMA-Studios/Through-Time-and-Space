/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds.effects;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.renderPlanet;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class GallifreyEffects extends DimensionSpecialEffects {

    private static VertexBuffer StarsVBO = null;
    private static VertexBuffer SunsVBO = null;

    private final ResourceKey<DimensionType> targetType;

    public GallifreyEffects(ResourceKey<DimensionType> targetType) {
        super(Float.NaN, false, SkyType.NONE, false, false);
        this.targetType = targetType;
    }

    @Override
    public @NotNull Vec3 getBrightnessDependentFogColor(@NotNull Vec3 p_108878_, float p_108879_) {
        return new Vec3(0, 0, 0);
    }

    @Override
    public float getCloudHeight() {
        return -10;
    }

    @Override
    public boolean hasGround() {
        return false;
    }

    @Override
    public boolean isFoggyAt(int p_108874_, int p_108875_) {
        return false;
    }

    @Override
    public boolean renderClouds(
            ClientLevel level,
            int ticks,
            float partialTick,
            PoseStack poseStack,
            double camX,
            double camY,
            double camZ,
            Matrix4f projectionMatrix) {
        return true;
    }

    @Override
    public boolean renderSky(
            ClientLevel level,
            int ticks,
            float partialTick,
            PoseStack poseStack,
            Camera camera,
            Matrix4f projectionMatrix,
            boolean isFoggy,
            Runnable setupFog) {

        renderSun(
                poseStack,
                projectionMatrix,
                new Vec3(30, 400, 0),
                Axis.ZP.rotation(Minecraft.getInstance()
                        .level
                        .getSunAngle(Minecraft.getInstance().level.getGameTime())),
                new Vec3(0, 0, 0),
                2);
        renderSun(
                poseStack,
                projectionMatrix,
                new Vec3(0, 450, 75),
                Axis.ZP.rotation(Minecraft.getInstance()
                        .level
                        .getSunAngle(Minecraft.getInstance().level.getGameTime())),
                new Vec3(0, 0, 0),
                2);

        renderPlanet(
                poseStack,
                new Vec3(30, 400, 0),
                Axis.ZP.rotation(Minecraft.getInstance()
                        .level
                        .getSunAngle(Minecraft.getInstance().level.getGameTime())),
                new Vec3(0, 0, 0),
                2,
                "sun");
        renderPlanet(
                poseStack,
                new Vec3(0, 450, 75),
                Axis.ZP.rotation(Minecraft.getInstance()
                        .level
                        .getSunAngle(Minecraft.getInstance().level.getGameTime())),
                new Vec3(0, 0, 0),
                2,
                "sun");

        return false;
    }

    public static void renderSun(
            @NotNull PoseStack poseStack,
            Matrix4f matrix4f,
            @NotNull Vec3 position,
            Quaternionf rotation,
            Vec3 PivotPoint,
            float size) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/sun.png"));

        poseStack.pushPose();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        poseStack.translate(position.x, position.y, position.z);
        poseStack.rotateAround(rotation, (float) PivotPoint.x, (float) PivotPoint.y, (float) PivotPoint.z);
        poseStack.scale(5.0F, 5.0F, 5.0F);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        if (SunsVBO == null) {
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            SunsVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);
            SunsVBO.bind();
            SunsVBO.upload(drawPlanet(buffer, size));

            VertexBuffer.unbind();
        }

        SunsVBO.bind();
        SunsVBO.drawWithShader(poseStack.last().pose(), matrix4f, RenderSystem.getShader());
        VertexBuffer.unbind();

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        poseStack.popPose();
    }
}
