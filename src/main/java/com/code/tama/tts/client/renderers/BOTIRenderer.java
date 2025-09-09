/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class BOTIRenderer {
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

        mc.getMainRenderTarget().enableStencil();

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        pose.pushPose();

        pose.translate(0, 0.3, -0.5);
        pose.scale(1, 0.8f, 1);

        // Disable writing to color and depth buffers so only stencil will update
        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(false);

        drawFrame(pose, 1, 3); // draws the "frame"

        // Re-enable color + depth writes
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);

        pose.popPose();

        //        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        pose.pushPose();
        pose.translate(0, 0, 0);

        assert mc.level != null;
        pose.scale(4, 4, 0);
        renderEndPortalQuad(mc.level, (int) mc.level.getGameTime(), mc.getPartialTick(), pose); // Render vortex

        pose.popPose();

        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    private static void drawFrame(PoseStack poseStack, float width, float height) {
        RenderSystem.setShader(GameRenderer::getPositionShader);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        poseStack.translate(-width / 2, -height / 2, 0);
        var matrix = poseStack.last().pose();

        builder.vertex(matrix, 0, 0, 0).endVertex();
        builder.vertex(matrix, 0, height, 0).endVertex();
        builder.vertex(matrix, width, height, 0).endVertex();
        builder.vertex(matrix, width, 0, 0).endVertex();

        Tesselator.getInstance().end();
    }

    public static boolean renderEndPortalQuad(ClientLevel level, int ticks, float partialTick, PoseStack poseStack) {
        poseStack.pushPose();

        // Translate to desired render position
        poseStack.translate(-0.5, -0.5, 0); // centers the quad
        Matrix4f matrix = poseStack.last().pose();

        // Get buffer for End Portal effect
        MultiBufferSource.BufferSource source =
                MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        VertexConsumer buffer = source.getBuffer(RenderType.endPortal());

        // Build a simple 1x1 quad
        buffer.vertex(matrix, 0, 0, 0).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0, 1, 0).uv(0, 1).endVertex();
        buffer.vertex(matrix, 1, 1, 0).uv(1, 1).endVertex();
        buffer.vertex(matrix, 1, 0, 0).uv(1, 0).endVertex();

        // Flush the buffer
        source.endBatch(RenderType.endPortal());

        poseStack.popPose();
        return true;
    }
}
