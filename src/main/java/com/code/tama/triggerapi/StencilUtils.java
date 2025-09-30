/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

public class StencilUtils {
    public static void DrawStencil(PoseStack pose, Consumer<PoseStack> drawFrame, Consumer<PoseStack> drawScene) {
        RenderSystem.assertOnRenderThread();
        Minecraft mc = Minecraft.getInstance();

        mc.getMainRenderTarget().enableStencil();

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        pose.pushPose();

        // Disable writing to color and depth buffers so only stencil will update
        RenderSystem.colorMask(false, false, false, false);
        RenderSystem.depthMask(false);

        drawFrame.accept(pose);

        // Re-enable color + depth writes
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.depthMask(true);

        pose.popPose();

        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

        pose.pushPose();
        pose.translate(0, 0, 0);

        assert mc.level != null;
        drawScene.accept(pose);

        pose.popPose();

        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

    public static void drawFrame(PoseStack poseStack, float width, float height) {
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
}
