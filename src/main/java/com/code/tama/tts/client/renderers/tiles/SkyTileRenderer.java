/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.client.renderers.worlds.SkyBlock;
import com.code.tama.tts.server.tileentities.SkyTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;

public class SkyTileRenderer implements BlockEntityRenderer<SkyTile> {
    public SkyTileRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            SkyTile blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        if (!blockEntity.getBlockState().getValue(com.code.tama.tts.server.blocks.SkyBlock.ACTIVE)) return;
        Matrix4f m4f = poseStack.last().pose();
        var renderType =
                switch (blockEntity.getSkyType()) {
                    case Overworld -> SkyBlock.SKY_RENDER_TYPE;
                    case Void -> RenderType.endGateway();
                };
        renderCube(blockEntity, m4f, multiBufferSource.getBuffer(renderType));
        SkyBlock.updateSky = true;
    }

    private void renderCube(SkyTile entity, Matrix4f matrix, VertexConsumer buffer) {
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, Direction.SOUTH);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, Direction.NORTH);
        renderFace(entity, matrix, buffer, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.EAST);
        renderFace(entity, matrix, buffer, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, Direction.WEST);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, Direction.DOWN);
        renderFace(entity, matrix, buffer, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, Direction.UP);
    }

    private void renderFace(
            SkyTile entity,
            Matrix4f matrix,
            VertexConsumer buffer,
            float f,
            float g,
            float h,
            float i,
            float j,
            float k,
            float l,
            float m,
            Direction direction) {
        if (entity.shouldRenderFace(direction)) {
            buffer.vertex(matrix, f, h, j).endVertex();
            buffer.vertex(matrix, g, h, k).endVertex();
            buffer.vertex(matrix, g, i, l).endVertex();
            buffer.vertex(matrix, f, i, m).endVertex();
        }
    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}
