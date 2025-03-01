package com.code.tama.mtm.Client.Renderer.World;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector4i;

import static com.code.tama.mtm.mtm.MODID;

public abstract class AbstractLevelRenderer {
    abstract ResourceLocation EffectsLocation();

    public final void Render(Camera camera, Matrix4f matrix4f, PoseStack poseStack, Frustum frustum, float partialTicks) {
//        RenderVoid(poseStack);
        assert Minecraft.getInstance().level != null;
        if (!Minecraft.getInstance().level.dimensionType().effectsLocation().equals(this.EffectsLocation())) return;

        this.RenderLevel(camera, matrix4f, poseStack, frustum, partialTicks);
        if(this.ShouldRenderVoid()) renderVoid(poseStack);
    }

    abstract void RenderLevel(Camera camera, Matrix4f matrix4f, PoseStack poseStack, Frustum frustum, float partialTicks);

    abstract boolean ShouldRenderVoid();

    public float GetOpacityForSkybox(float PartialTicks) {
        assert Minecraft.getInstance().level != null;
        float Angle = (float) Math.toDegrees(Minecraft.getInstance().level.getSunAngle(PartialTicks)),
                Opacity;
        Opacity = Angle > 180F ? 180 - Math.abs(180F - Angle) : Angle;
        Opacity /= 0.7F;
        Opacity = Math.min(255, Opacity);
        return Opacity;
    }

    public static void renderVoid(PoseStack poseStack) {
        poseStack.pushPose();
        Vector4i Colors = new Vector4i(0, 0, 0, 255);
        // Disable depth testing and culling for skybox
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.disableCull(); // Disable backface culling to ensure inner faces are visible

        // --- Render Panorama (Inside Cube) ---
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/void.png"));

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        poseStack.scale(4, 4, 4);
        float panoramaSize = 200.0F;

        poseStack.translate(-panoramaSize + panoramaSize, 0, 0);

        // Reverse vertex order (clockwise) to face inward
        // Front face
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Back face
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Left face
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Right face
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Top face
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Bottom face
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        BufferUploader.drawWithShader(bufferBuilder.end());

        // Restore render state

        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
        poseStack.popPose();
    }
}
