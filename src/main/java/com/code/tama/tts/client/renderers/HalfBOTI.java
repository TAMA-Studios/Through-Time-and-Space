/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class HalfBOTI {

    private static RenderTarget framebuffer;
    public static Pair<Float, Float>[] positions;

    public static void render(
            Level tardis,
            ExteriorTile tile,
            PoseStack pose,
            MultiBufferSource buffer,
            float ageInTicks,
            int packedLight,
            int packedOverlay) {

        RenderSystem.assertOnRenderThread();
        Minecraft mc = Minecraft.getInstance();
        Window window = mc.getWindow();

        // --- Offscreen: render vortex into its own RenderTarget ---
        RenderTarget fb = getTargetResized(window);
        fb.bindWrite(true);
        fb.clear(Minecraft.ON_OSX); // clears color + depth (+ stencil if present)

        PoseStack vortexPose = new PoseStack();
        assert mc.player != null;
        renderVortexOffscreen(mc.level, mc.player.tickCount, mc.getPartialTick(), vortexPose);

        // Back to main framebuffer (world is already rendered at this point)
        mc.getMainRenderTarget().bindWrite(true);

        // --- Stencil: mark mask area with 1 ---
        mc.getMainRenderTarget().enableStencil();
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        pose.pushPose();
        pose.translate(0, 0, -2);
        drawFrame(pose, 2, 4); // writes "1" into stencil where the quad is
        pose.popPose();

        // Only allow drawing where stencil == 1
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        // Draw the offscreen texture OVER the world, restricted to the mask
        drawFramebufferToScreen(fb, mc.getWindow());

        // Cleanup
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    // ------------------------------
    // RENDER VORTEX OFFSCREEN
    // ------------------------------
    public static void renderVortexOffscreen(ClientLevel level, int ticks, float partialTick, PoseStack poseStack) {
        //        boolean isInVortex = Capabilities.getCap(CapabilityConstants.TARDIS_LEVEL_CAPABILITY, level)
        //                .map(t -> t.IsInFlight())
        //                .orElse(false);
        //        if (!isInVortex) return;

        poseStack.pushPose();

        // Use POSITION_TEX (position + uv). Don't call .normal() later.
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        RenderSystem.setShader(GameRenderer::getRendertypeEndPortalShader);
        RenderSystem.setShaderGameTime(ticks, partialTick);

        poseStack.popPose();

        // push geometry to GPU while the framebuffer is still bound
        BufferUploader.drawWithShader(builder.end());
    }

    // ------------------------------
    // DRAW MASK FRAME
    // ------------------------------
    private static void drawFrame(PoseStack poseStack, float width, float height) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        poseStack.translate(-width / 2, -height / 2, 0);
        Matrix4f mat = poseStack.last().pose();

        builder.vertex(mat, 0, 0, 0).endVertex();
        builder.vertex(mat, 0, height, 0).endVertex();
        builder.vertex(mat, width, height, 0).endVertex();
        builder.vertex(mat, width, 0, 0).endVertex();

        Tesselator.getInstance().end();
    }

    // ------------------------------
    // BLIT FRAMEBUFFER TO SCREEN
    // ------------------------------
    private static void drawFramebufferToScreen(RenderTarget fb, Window window) {
        // make sure we draw on top of world geometry in the masked area
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        // Use standard alpha blending (src alpha, 1-src alpha)
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        // Use built-in position+tex shader and bind the framebuffer color texture to unit 0
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, fb.getColorTextureId());

        // Draw fullscreen NDC quad sampling the fb texture
        BufferBuilder b = Tesselator.getInstance().getBuilder();
        b.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // Note: texture coordinates (0,0)-(1,1). If you see vertical flip, swap the V coords.
        b.vertex(-1.0F, -1.0F, 0.0F).uv(0.0F, 1.0F).endVertex();
        b.vertex(1.0F, -1.0F, 0.0F).uv(1.0F, 1.0F).endVertex();
        b.vertex(1.0F, 1.0F, 0.0F).uv(1.0F, 0.0F).endVertex();
        b.vertex(-1.0F, 1.0F, 0.0F).uv(0.0F, 0.0F).endVertex();

        Tesselator.getInstance().end();

        // restore state
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    // ------------------------------
    // GET / RESIZE OFFSCREEN TARGET
    // ------------------------------
    public static RenderTarget getTarget() {
        Window w = Minecraft.getInstance().getWindow();
        if (framebuffer == null) {
            framebuffer = new TextureTarget(w.getWidth(), w.getHeight(), true, Minecraft.ON_OSX);
            framebuffer.setClearColor(0, 0, 0, 0);
        }
        return framebuffer;
    }

    private static RenderTarget getTargetResized(Window w) {
        RenderTarget fb = getTarget();
        if (fb.width != w.getWidth() || fb.height != w.getHeight()) {
            fb.resize(w.getWidth(), w.getHeight(), Minecraft.ON_OSX);
        }
        return fb;
    }
}
