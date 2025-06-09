/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.renderPlanet;

import com.code.tama.tts.client.renderers.worlds.helper.AbstractLevelRenderer;
import com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import java.util.Random;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4i;

public abstract class BasicSkyRenderer extends AbstractLevelRenderer {
    private static void RenderStars(@NotNull PoseStack poseStack, Matrix4f projectionMatrix, float PartialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        poseStack.pushPose();
        float size = 0.5f;
        poseStack.translate(0, 100, 0); // ((float) relativePosition.x, (float) relativePosition.y, (float)
        // relativePosition.z);
        poseStack.scale(30.0F, 30.0F, 30.0F);

        Matrix4f matrix = poseStack.last().pose();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int i = 0; i < 1000; ++i) {
            assert Minecraft.getInstance().level != null;
            float x = 190 - new Random(Minecraft.getInstance().level.getGameTime()).nextFloat() * (190 * 2);
            float z = 190 - new Random(Minecraft.getInstance().level.getGameTime()).nextFloat() * (190 * 2);
            float y = 190;
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();

            // UP
            buffer.vertex(matrix, x - size, y + size, z - size)
                    .color(1F, 1, 1, 1)
                    .endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();

            // East
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();

            // West
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z - size)
                    .color(1F, 1, 1, 1)
                    .endVertex();
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();

            // SOUTH
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z - size)
                    .color(1F, 1, 1, 1)
                    .endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();

            // Down
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();
        }

        BufferUploader.drawWithShader(buffer.end());

        poseStack.popPose();
        RenderSystem.disableBlend();
    }

    private final int SkyColor[];

    public BasicSkyRenderer(int skyColor[]) {
        this.SkyColor = skyColor;
    }

    @Override
    public void RenderLevel(
            @NotNull Camera camera,
            Matrix4f matrix4f,
            @NotNull PoseStack poseStack,
            Frustum frustum,
            float partialTicks) {
        CustomLevelRenderer.renderImageSky(
                poseStack,
                new ResourceLocation(MODID, "textures/environment/night_sky.png"),
                new Vector4i(255, 255, 255, (int) GetOpacityForSkybox(partialTicks)));
        CustomLevelRenderer.renderImageSky(
                poseStack,
                new ResourceLocation(MODID, "textures/environment/void.png"),
                new Vector4i(this.SkyColor[0], this.SkyColor[1], this.SkyColor[2], -(int)
                        GetOpacityForSkybox(partialTicks)));
        assert Minecraft.getInstance().level != null;
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
    }

    @Override
    public abstract boolean ShouldRenderVoid();
}
