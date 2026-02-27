/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.boti;

import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class BotiWindowRenderer implements BlockEntityRenderer<BotiWindowTile> {

    // Per-master stencil VBO cache. Keyed by master tile instance.
    // WeakHashMap so it auto-clears if the tile is unloaded.
    private static final List<BlockPos> stencilVBOCache = new ArrayList<>();
    private static VertexBuffer stencilVBO = null;
    public BotiWindowRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(@NotNull BotiWindowTile tile, float partialTick, @NotNull PoseStack pose,
                       @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (!tile.isMaster()) return; // non-masters do nothing
        RenderSystem.enableDepthTest();

        BlockPos masterPos = tile.getBlockPos();

        if(stencilVBO == null) {
            List<BlockPos> cluster = BotiWindowCluster.findCluster(tile.getLevel(), masterPos);
            stencilVBO = BotiWindowCluster.buildStencilVBO(cluster);
        }

        pose.pushPose();

        // Translate so that 0,0,0 in the VBO = world origin
        // The VBO was built in world space, but the pose stack is relative to the tile pos,
        // so we need to undo that offset.
        pose.translate(-masterPos.getX(), -masterPos.getY(), -masterPos.getZ() + 0.5);

        tile.getFBOContainer().Render(pose,
                (stack, source) -> {
            stack.pushPose();
                    RenderSystem.setShader(GameRenderer::getPositionShader);
                    stencilVBO.bind();
                    stencilVBO.drawWithShader(pose.last().pose(), RenderSystem.getProjectionMatrix(),
                            RenderSystem.getShader());
                    VertexBuffer.unbind();
                    stack.popPose();
                },
                (stack, source) -> {},
                (stack, source) -> {
            stack.pushPose();
                    pose.translate(masterPos.getX(), masterPos.getY() - 1.5, masterPos.getZ() + 0.5);
                    StencilUtils.drawColoredCube(stack, 500, new Vec3(0, 0, 100));
                    BOTIUtils.RenderScene(pose, tile);
                    stack.popPose();
                });

        pose.popPose();
        if(true) return; // WHEN ENABLING NEXT CODE MAKE SURE TO PREVENT POSE NOT CLOSED OR WHATEVER BULLSHIT
        // ---- Stencil pass: write 1 where the cluster faces are ----
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
        GL11.glColorMask(false, false, false, false);
        RenderSystem.depthMask(false);

        RenderSystem.setShader(GameRenderer::getPositionShader);
        stencilVBO.bind();
        stencilVBO.drawWithShader(pose.last().pose(), RenderSystem.getProjectionMatrix(),
                RenderSystem.getShader());
        VertexBuffer.unbind();

        // ---- Scene pass: only draw where stencil == 1 ----
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        GL11.glColorMask(true, true, true, true);
        RenderSystem.depthMask(true);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);

        // Undo the world-space offset before passing to RenderScene,
        // which expects the pose to be at the portal tile's position.
        pose.translate(masterPos.getX(), masterPos.getY(), masterPos.getZ());
        BOTIUtils.RenderScene(pose, tile);

        // ---- Cleanup ----
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);

        pose.popPose();
    }

    /** Call this when a cluster member is added/removed to force a stencil VBO rebuild. */
    public static void invalidateStencilVBO() {
        stencilVBO = null;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}