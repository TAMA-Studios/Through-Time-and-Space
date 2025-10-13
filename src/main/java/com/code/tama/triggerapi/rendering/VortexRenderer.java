/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class VortexRenderer {
    public ResourceLocation TEXTURE_LOCATION;
    public ResourceLocation SECOND_LAYER_LOCATION;
    public ResourceLocation THIRD_LAYER_LOCATION;
    private final float wobbleSpeed;
    private final float wobbleSeperationFactor;
    private final float wobbleFactor;
    private final float diameter;
    private final float speed;
    private float time = 0;

    public VortexRenderer(ResourceLocation texture) {
        TEXTURE_LOCATION = texture;
        SECOND_LAYER_LOCATION = texture.withPath(texture.getPath().replace(".png", "") + "_two" + ".png");
        THIRD_LAYER_LOCATION = texture.withPath(texture.getPath().replace(".png", "") + "_three" + ".png");
        this.wobbleSpeed = 0.5f;
        this.wobbleSeperationFactor = 32f;
        this.wobbleFactor = 2;
        this.diameter = 32f;
        this.speed = 4f;
    }

    public void renderVortex(PoseStack stack) {

        time += Minecraft.getInstance().getDeltaFrameTime() / 360f;

        stack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);

        stack.scale(diameter, diameter, diameter);

        Minecraft.getInstance().getTextureManager().bindForSetup(TEXTURE_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (int i = 0; i < 32; ++i) {
            assert Minecraft.getInstance().level != null;
            this.renderSection(
                    buffer,
                    i,
                    (Minecraft.getInstance().level.getGameTime() / 200.0f) * -this.speed,
                    (float) Math.sin(i * Math.PI / 32),
                    (float) Math.sin((i + 1) * Math.PI / 32),
                    stack.last().normal(),
                    stack.last().pose());
        }

        tesselator.end();
        stack.popPose();
    }

    public void renderVortexLayer(PoseStack stack, float scaleFactor) {
        ResourceLocation currentTexture = scaleFactor == 1.5f ? SECOND_LAYER_LOCATION : THIRD_LAYER_LOCATION;
        if (Minecraft.getInstance()
                .getResourceManager()
                .getResource(currentTexture)
                .isEmpty()) return;
        this.renderVortexLayer(stack, scaleFactor, currentTexture);
    }

    private void renderVortexLayer(PoseStack stack, float scaleFactor, ResourceLocation layer) {

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, layer);

        time += Minecraft.getInstance().getDeltaFrameTime() / 360f;

        stack.pushPose();

        stack.scale(diameter / scaleFactor, diameter / scaleFactor, diameter);

        Minecraft.getInstance().getTextureManager().bindForSetup(layer);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        for (int i = 0; i < 32; ++i) {
            assert Minecraft.getInstance().level != null;
            this.renderSection(
                    buffer,
                    i,
                    (Minecraft.getInstance().level.getGameTime() / 200.0f) * -this.speed,
                    (float) Math.sin(i * Math.PI / 32),
                    (float) Math.sin((i + 1) * Math.PI / 32),
                    stack.last().normal(),
                    stack.last().pose());
        }

        tesselator.end();
        stack.popPose();

        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }

    public void renderSection(
            VertexConsumer builder,
            int zOffset,
            float textureDistanceOffset,
            float startScale,
            float endScale,
            Matrix3f matrix3f,
            Matrix4f matrix4f) {
        float panel = 1 / 6f;
        float sqrt = (float) Math.sqrt(3) / 2.0f;
        int vOffset = (zOffset * panel + textureDistanceOffset > 1.0) ? zOffset - 6 : zOffset;
        float distortion = this.computeDistortionFactor(time, zOffset);
        float distortionPlusOne = this.computeDistortionFactor(time, zOffset + 1);
        float panelDistanceOffset = panel + textureDistanceOffset;
        float vPanelOffset = (vOffset * panel) + textureDistanceOffset;

        int uOffset = 0;

        float uPanelOffset = uOffset * panel;

        addVertex(builder, matrix3f, matrix4f, 0f, -startScale + distortion, -zOffset, uPanelOffset, vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                0f,
                -endScale + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * -sqrt,
                endScale / -2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * -sqrt,
                startScale / -2f + distortion,
                -zOffset,
                uPanelOffset + panel,
                vPanelOffset);

        uOffset = 1;

        uPanelOffset = uOffset * panel;

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * -sqrt,
                startScale / -2f + distortion,
                -zOffset,
                uPanelOffset,
                vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * -sqrt,
                endScale / -2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * -sqrt,
                endScale / 2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * -sqrt,
                startScale / 2f + distortion,
                -zOffset,
                uPanelOffset + panel,
                vPanelOffset);

        uOffset = 2;

        uPanelOffset = uOffset * panel;

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                0f,
                endScale + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder, matrix3f, matrix4f, 0f, startScale + distortion, -zOffset, uPanelOffset + panel, vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * -sqrt,
                startScale / 2f + distortion,
                -zOffset,
                uPanelOffset,
                vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * -sqrt,
                endScale / 2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        uOffset = 3;

        uPanelOffset = uOffset * panel;

        addVertex(builder, matrix3f, matrix4f, 0f, startScale + distortion, -zOffset, uPanelOffset, vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                0f,
                endScale + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * sqrt,
                (endScale / 2f + distortionPlusOne),
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * sqrt,
                (startScale / 2f + distortion),
                -zOffset,
                uPanelOffset + panel,
                vPanelOffset);

        uOffset = 4;

        uPanelOffset = uOffset * panel;

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * sqrt,
                (startScale / 2f + distortion),
                -zOffset,
                uPanelOffset,
                vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * sqrt,
                endScale / 2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * sqrt,
                endScale / -2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * sqrt,
                startScale / -2f + distortion,
                -zOffset,
                uPanelOffset + panel,
                vPanelOffset);

        uOffset = 5;

        uPanelOffset = uOffset * panel;

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                0f,
                -endScale + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset + panel,
                vOffset * panel + panelDistanceOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                0f,
                -startScale + distortion,
                -zOffset,
                uPanelOffset + panel,
                vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                startScale * sqrt,
                startScale / -2f + distortion,
                -zOffset,
                uPanelOffset,
                vPanelOffset);

        addVertex(
                builder,
                matrix3f,
                matrix4f,
                endScale * sqrt,
                endScale / -2f + distortionPlusOne,
                -zOffset - 1,
                uPanelOffset,
                vOffset * panel + panelDistanceOffset);
    }

    private void addVertex(
            VertexConsumer builder,
            Matrix3f normalMatrix,
            Matrix4f matrix,
            float x,
            float y,
            float z,
            float u,
            float v) {
        builder.vertex(matrix, x, y, z)
                .color(1, 1, 1, 1f)
                .uv(u, v)
                .uv2(0xF000F0)
                .normal(normalMatrix, 0, 0.0f, 0)
                .endVertex();
    }

    private float computeDistortionFactor(float time, int t) {
        return (float) (Math.sin(time * this.wobbleSpeed * 2.0 * Math.PI + (13 - t) * this.wobbleSeperationFactor)
                        * this.wobbleFactor)
                / 8;
    }
}
