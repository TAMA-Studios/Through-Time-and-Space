/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;

public class SkyRendererHelper {
    private static VertexBuffer StarsVBO = null;
    private static VertexBuffer SunVBO = null;

    private static void RenderStars(@NotNull PoseStack poseStack, Matrix4f matrix4f) {
        poseStack.pushPose();

        if (StarsVBO == null) {
            RandomSource randomsource = RandomSource.create(10842L);

            StarsVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);

            BufferBuilder buffer = Tesselator.getInstance().getBuilder();
            if (buffer.building()) return;
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
            for (int i = 0; i < 1500; ++i) {
                double d0 = randomsource.nextFloat() * 2.0F - 1.0F;
                double d1 = randomsource.nextFloat() * 2.0F - 1.0F;
                double d2 = randomsource.nextFloat() * 2.0F - 1.0F;
                double d3 = 0.15F + randomsource.nextFloat() * 0.1F;
                double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d4 < 1.0 && d4 > 0.01) {
                    d4 = 1.0 / Math.sqrt(d4);
                    d0 *= d4;
                    d1 *= d4;
                    d2 *= d4;
                    double d5 = d0 * 100.0;
                    double d6 = d1 * 100.0;
                    double d7 = d2 * 100.0;
                    double d8 = Math.atan2(d0, d2);
                    double d9 = Math.sin(d8);
                    double d10 = Math.cos(d8);
                    double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                    double d12 = Math.sin(d11);
                    double d13 = Math.cos(d11);
                    double d14 = randomsource.nextDouble() * Math.PI * 2.0;
                    double d15 = Math.sin(d14);
                    double d16 = Math.cos(d14);

                    for (int j = 0; j < 4; ++j) {
                        double d18 = (double) ((j & 2) - 1) * d3;
                        double d19 = (double) ((j + 1 & 2) - 1) * d3;
                        double d21 = d18 * d16 - d19 * d15;
                        double d22 = d19 * d16 + d18 * d15;
                        double d23 = d21 * d12 + 0.0 * d13;
                        double d24 = 0.0 * d12 - d21 * d13;
                        double d25 = d24 * d9 - d22 * d10;
                        double d26 = d22 * d9 + d24 * d10;
                        buffer.vertex(d5 + d25, d6 + d23, d7 + d26).endVertex();
                    }
                }
            }

            StarsVBO.bind();
            StarsVBO.upload(buffer.end());
            VertexBuffer.unbind();
        }

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        FogRenderer.setupNoFog();

        RenderSystem.disableDepthTest();
        StarsVBO.bind();
        assert GameRenderer.getPositionShader() != null;
        StarsVBO.drawWithShader(poseStack.last().pose(), matrix4f, GameRenderer.getPositionShader());

        VertexBuffer.unbind();
        RenderSystem.enableDepthTest();

        poseStack.popPose();
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

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        if (SunVBO == null || SunVBO.isInvalid()) {
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            SunVBO = new VertexBuffer(VertexBuffer.Usage.STATIC);
            SunVBO.bind();
            SunVBO.upload(drawPlanet(buffer, size));
            VertexBuffer.unbind();
        }

        if (!SunVBO.isInvalid()) {
            SunVBO.bind();
            SunVBO.drawWithShader(poseStack.last().pose(), matrix4f, RenderSystem.getShader());
            VertexBuffer.unbind();
        }

        SunVBO.close();

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        poseStack.popPose();
    }
}
