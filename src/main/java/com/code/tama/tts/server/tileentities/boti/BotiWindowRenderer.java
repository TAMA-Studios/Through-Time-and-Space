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
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class BotiWindowRenderer implements BlockEntityRenderer<BotiWindowTile> {
    private VertexBuffer stencilVBO = null;
    public BotiWindowRenderer() {}

    @Override
    public void render(@NotNull BotiWindowTile tile, float partialTick, @NotNull PoseStack pose,
                       @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (!tile.isMaster()) return; // non-masters do nothing
        if(tile.invalidated) stencilVBO = null;
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
                    if(stencilVBO == null) return; // I've had instances where it's still null, if this be one of those cases, abort before it crashes
                    stack.pushPose();
                    RenderSystem.setShader(GameRenderer::getPositionShader);
                    stencilVBO.bind();
                    stencilVBO.drawWithShader(pose.last().pose(), RenderSystem.getProjectionMatrix(),
                            Objects.requireNonNull(RenderSystem.getShader()));
                    VertexBuffer.unbind();
                    stack.popPose();
                },
                (stack, source) -> {},
                (stack, source) -> {
                    if(stencilVBO == null) return;
                    stack.pushPose();
                    pose.translate(masterPos.getX() + 1.5, masterPos.getY() - 1.5, masterPos.getZ() - 0.5);
                    StencilUtils.drawColoredCube(stack, 500, tile.SkyColor);
                    // Apply configurator rotation around Y axis
                    stack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(tile.getRotationDegrees()));
                    BOTIUtils.RenderScene(pose, tile);
                    stack.popPose();
                });

        pose.popPose();
    }

    /** Call this when a cluster member is added/removed to force a stencil VBO rebuild. */
    public void invalidateStencilVBO() {
        stencilVBO = null;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}