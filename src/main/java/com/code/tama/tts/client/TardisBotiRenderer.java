/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

/**
 * this obv doesn't work, WIP implementation of real BOTI
 **/
public class TardisBotiRenderer {
    private static TextureTarget tardisFbo = null;

    // Call on window resize or lazily via ensureFbo()
    private static void initFbo(int width, int height) {
        destroyFbo();

        // true = use depth renderbuffer
        tardisFbo = new TextureTarget(width, height, true, Minecraft.ON_OSX);
        tardisFbo.setClearColor(0f, 0f, 0f, 1f); // opaque default (helpful for testing)
        // Ensure buffers created and cleared
        tardisFbo.clear(Minecraft.ON_OSX);
    }

    private static void destroyFbo() {
        if (tardisFbo != null) {
            tardisFbo.destroyBuffers();
            tardisFbo = null;
        }
    }

    private static void ensureFboSize(int w, int h) {
        if (tardisFbo == null || tardisFbo.width != w || tardisFbo.height != h) {
            initFbo(w, h);
        }
    }

    /**
     * Top-level call from BE renderer.
     */
    public static void render(
            Level level,
            ExteriorTile tile,
            PoseStack pose,
            MultiBufferSource buffer,
            float partialTicks,
            int packedLight,
            int packedOverlay) {
        RenderSystem.assertOnRenderThread();
        Minecraft mc = Minecraft.getInstance();

        // ensure FBO size
        int winW = mc.getWindow().getWidth();
        int winH = mc.getWindow().getHeight();
        ensureFboSize(winW, winH);

        // === PASS 1: Render interior into our FBO (right now a solid opaque test) ===
        // Bind and clear our FBO
        tardisFbo.bindWrite(true);
        // Use the FBO's clear color (set in initFbo) and clear color+depth
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // For testing: render an opaque quad filling the FBO so we can see it.
        // Replace this block with real interior rendering (use PoseStack & normal rendering).
        renderInteriorToFbo(pose);

        // Unbind fbo (bind main framebuffer for writing)
        mc.getMainRenderTarget().bindWrite(true);

        // === PASS 2: Create stencil mask on MAIN framebuffer ===
        // Make sure the main target has a stencil buffer
        mc.getMainRenderTarget().enableStencil();

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);

        // Clear stencil buffer
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

        // Configure stencil to write '1' wherever the mask is drawn
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        // Disable color/depth writes so only stencil is affected
        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(false);

        // Draw mask (frame) in screen/world space using PoseStack
        pose.pushPose();
        // transform to where frame is; adapt as needed
        pose.translate(0.0, 0.3, -0.5);
        pose.scale(1.0f, 0.8f, 1.0f);
        drawFrame(pose, 1f, 3f);
        pose.popPose();

        // Restore color/depth writing
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);

        // Lock stencil so we don't overwrite it
        GL11.glStencilMask(0x00);

        // Only pass where stencil == 1
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        // === PASS 3: Composite the FBO into the stencil area ===
        // Important: clear depth or disable depth test so the main-world depth doesn't clip our quad.
        // We'll clear the depth buffer to avoid world geometry cutting into the BOTI.
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        // Draw the FBO texture (as a textured quad) within the stencil region
        pose.pushPose();
        // Position/scale this quad so it lines up with the frame (use same transforms as mask)
        pose.translate(0.0, 0.3, -0.5);
        pose.scale(1.0f * 4f, 0.8f * 4f, 1.0f); // adjust scale to match FBO content vs mask
        drawFboQuad(pose, tardisFbo);
        pose.popPose();

        // restore depth test
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        // Turn off stencil test
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    // --- Helpers ---

    /**
     * TODO: Replace this with interior renderer. Right now it draws an opaque magenta quad
     * filling the FBO so I can visually confirm the FBO is being composited correctly.
     */
    private static void renderInteriorToFbo(PoseStack pose) {
        // Use an identity pose for full-screen coverage in FBO coordinates.
        pose.pushPose();
        pose.translate(0, 0, 0);

        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(0.6f, 0.0f, 0.6f, 1f); // visible magenta test color
        RenderSystem.setShader(GameRenderer::getPositionShader);

        BufferBuilder bb = Tesselator.getInstance().getBuilder();
        bb.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        Matrix4f mat = pose.last().pose();

        // Draw a quad in normalized FBO space. When using TextureTarget, drawing with these coordinates
        // will map to the FBO texture area as you'd expect for test purposes.
        // TODO: once it actually renders a 3D scene here, set up proper projection and view transforms.
        bb.vertex(mat, -1f, -1f, 0f).endVertex();
        bb.vertex(mat, -1f, 1f, 0f).endVertex();
        bb.vertex(mat, 1f, 1f, 0f).endVertex();
        bb.vertex(mat, 1f, -1f, 0f).endVertex();
        Tesselator.getInstance().end();

        pose.popPose();
    }

    /**
     * Draw the FBO's color texture to a quad using the PoseStack transform (so it aligns with the mask).
     */
    private static void drawFboQuad(PoseStack pose, RenderTarget fbo) {
        RenderSystem.setShaderTexture(0, fbo.getColorTextureId());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        Matrix4f mat = pose.last().pose();

        // Draw a unit quad which the current pose transforms into the mask area.
        builder.vertex(mat, 0f, 0f, 0f).uv(0f, 1f).endVertex();
        builder.vertex(mat, 0f, 1f, 0f).uv(0f, 0f).endVertex();
        builder.vertex(mat, 1f, 1f, 0f).uv(1f, 0f).endVertex();
        builder.vertex(mat, 1f, 0f, 0f).uv(1f, 1f).endVertex();

        Tesselator.getInstance().end();
    }

    /**
     * Draw the frame geometry into which the BOTI should appear. Reuse existing drawFrame method.
     */
    private static void drawFrame(PoseStack poseStack, float width, float height) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        poseStack.pushPose();
        poseStack.translate(-width / 2f, -height / 2f, 0f);
        Matrix4f matrix = poseStack.last().pose();

        builder.vertex(matrix, 0f, 0f, 0f).endVertex();
        builder.vertex(matrix, 0f, height, 0f).endVertex();
        builder.vertex(matrix, width, height, 0f).endVertex();
        builder.vertex(matrix, width, 0f, 0f).endVertex();

        Tesselator.getInstance().end();
        poseStack.popPose();
    }
}
