package com.code.tama.mtm.client;

import com.code.tama.mtm.client.renderers.worlds.AbstractLevelRenderer;
import com.code.tama.mtm.client.renderers.worlds.GallifreySkyRenderer;
import com.code.tama.mtm.client.renderers.worlds.TardisSkyRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4i;

import java.util.ArrayList;

import static com.code.tama.mtm.MTMMod.MODID;

public class CustomLevelRenderer {
    public static ArrayList<AbstractLevelRenderer> Renderers = new ArrayList<>();
    private static final Vec3 PLANET_POSITION = new Vec3(0, 100, 0); // Position of the cube planet in world coordinates
    static long Ticks;
    static boolean InittedSkyboxThread;

    /** Adds all the renderers to the renderer array **/
    public static void Register() {
        AddRenderer(new GallifreySkyRenderer());
        AddRenderer(new TardisSkyRenderer());
    }

    public static void AddRenderer(AbstractLevelRenderer renderer) {
        CustomLevelRenderer.Renderers.add(renderer);
    }

    // This method will handle the rendering event
    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
//        MTMMod.skyboxRenderThread.SetEvent(event);
//        if(!InittedSkyboxThread) {
//            MTMMod.skyboxRenderThread.start();
//            InittedSkyboxThread = true;
//        }

        Ticks = Minecraft.getInstance().level.getGameTime();
    }

    public static void applyLighting(float ambientLight) {
        ambientLight = Math.max(0.0f, Math.min(ambientLight, 1.5f));

        // Apply a non-linear scaling to preserve light sources
        float adjustedLight = (float) Math.pow(ambientLight, 1.05f);

        RenderSystem.setShaderColor(adjustedLight, adjustedLight, adjustedLight, 1.0f);
    }

    public static void applyFogEffect(float ambientLight) {
        float fogDensity = Math.max(0.1f, 1.0f - ambientLight); // Inverse relationship with ambient light
        RenderSystem.setShaderFogColor(fogDensity, fogDensity, fogDensity);
    }

    public static void renderImageSky(PoseStack poseStack, ResourceLocation resourceLocation, Vector4i Colors) {
        if(true) return; // Disabled but just doing `return` throws unreachable

        poseStack.pushPose();

        // Disable depth testing and culling for skybox
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.disableCull(); // Disable back face culling to ensure inner faces are visible

        // --- Render Panorama (Inside Cube) ---
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, resourceLocation);

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        float panoramaSize = 200.0f;

        poseStack.translate(-panoramaSize + panoramaSize, 0, 0);

        // Reverse vertex order (clockwise) to face inward
        // Front face (North)
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, -panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, -panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, -panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, -panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();

        // Back face (South)
        poseStack.mulPose(Axis.ZP.rotationDegrees(180)); // Flip the face upside down
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, panoramaSize, panoramaSize).uv(1.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), -panoramaSize, -panoramaSize, panoramaSize).uv(1.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, -panoramaSize, panoramaSize).uv(0.0f, 1.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        bufferBuilder.vertex(poseStack.last().pose(), panoramaSize, panoramaSize, panoramaSize).uv(0.0f, 0.0f).color(Colors.x, Colors.y, Colors.z, Colors.w).endVertex();
        poseStack.mulPose(Axis.ZN.rotationDegrees(180)); // Restore the matrix so all the other faces aren't affected

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


    public static void renderPlanet(@NotNull PoseStack poseStack, @NotNull Vec3 position, Quaternionf rotation, Vec3 PivotPoint, float size, String name) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/environment/" + name + ".png"));

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        poseStack.pushPose();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        poseStack.translate(position.x, position.y, position.z);
        poseStack.rotateAround(rotation, (float) PivotPoint.x, (float) PivotPoint.y, (float) PivotPoint.z);
        poseStack.scale(30.0F, 30.0F, 30.0F);

        Matrix4f matrix = poseStack.last().pose();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float BaseSize = 0.0F;

        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 0).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 0).endVertex();

        // Top
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(0, 0).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(1, 0).endVertex();

        //East
        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(0, 0).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 0).endVertex();

        //West
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 0).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(1, 0).endVertex();

        //SOUTH
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(0, 0).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize + size, BaseSize - size).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize + size, BaseSize - size).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(1, 0).endVertex();

        //Down
        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize - size).uv(1, 0).endVertex();
        buffer.vertex(matrix, BaseSize, BaseSize, BaseSize).uv(1, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize).uv(0, 1).endVertex();
        buffer.vertex(matrix, BaseSize - size, BaseSize, BaseSize - size).uv(0, 0).endVertex();

        BufferUploader.drawWithShader(buffer.end());

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        poseStack.popPose();
    }
}
