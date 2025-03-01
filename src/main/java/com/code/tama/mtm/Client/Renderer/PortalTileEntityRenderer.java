package com.code.tama.mtm.Client.Renderer;

import com.code.tama.mtm.Networking.Networking;
import com.code.tama.mtm.Networking.Packets.Portal.PortalChunkDataPacket;
import com.code.tama.mtm.Networking.Packets.Portal.PortalChunkRequestPacket;
import com.code.tama.mtm.TileEntities.PortalTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.Map;

@SuppressWarnings("deprecation")
public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
    private final BlockEntityRendererProvider.Context context;
    private final Minecraft mc = Minecraft.getInstance();
    private float lastRenderTick = -1;

    public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull PortalTileEntity tileEntity, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
        assert mc.level != null;
        if (!mc.level.isClientSide() || tileEntity.getTargetLevel() == null || tileEntity.getTargetPos() == null) {
            return;
        }

        long currentTime = mc.level.getGameTime();
        if (mc.getPartialTick() == lastRenderTick) {
            return; // Throttle to once per tick
        }
        lastRenderTick = mc.getPartialTick();

        poseStack.pushPose();
        poseStack.translate(0, -1, 0);
        float scale = 1;
        poseStack.scale(scale, scale, scale);

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.solid());
        renderPortalFrame(poseStack, vertexConsumer, packedLight, packedOverlay);

        if (currentTime - tileEntity.lastUpdateTime >= 20) {
            updateChunkModel(tileEntity);
            tileEntity.lastUpdateTime = currentTime;
        }

        if (tileEntity.chunkModel != null) {
            poseStack.pushPose();
            poseStack.translate(-8, -8, -8);
            VertexConsumer chunkConsumer = buffer.getBuffer(RenderType.cutout());
            context.getBlockRenderDispatcher().getModelRenderer().renderModel(
                    poseStack.last(),
                    chunkConsumer,
                    null,
                    tileEntity.chunkModel,
                    1.0F, 1.0F, 1.0F,
                    packedLight,

                    packedOverlay
            );
            poseStack.popPose();
        }

        // Render block entities
        for (Map.Entry<BlockPos, BlockEntity> entry : tileEntity.blockEntities.entrySet()) {
            BlockPos offsetPos = entry.getKey();
            BlockEntity be = entry.getValue();
            BlockEntityRenderer<BlockEntity> renderer = mc.getBlockEntityRenderDispatcher().getRenderer(be);
            if (renderer != null) {
                poseStack.pushPose();
                poseStack.translate(offsetPos.getX() - 8, offsetPos.getY() - 8, offsetPos.getZ() - 8);
                renderer.render(be, partialTick, poseStack, buffer, packedLight, packedOverlay);
                poseStack.popPose();
            } else {
                System.out.println("No renderer found for block entity " + be + " at " + offsetPos);
            }
        }

        poseStack.popPose();
    }

    private void updateChunkModel(PortalTileEntity tileEntity) {
        assert mc.level != null;
        if (!mc.level.isClientSide()) return;

        long currentTime = mc.level.getGameTime();
        if (tileEntity.lastRequestTime == 0 || currentTime - tileEntity.lastRequestTime >= 20) {
            Level targetLevel = mc.level.dimension() == tileEntity.getTargetLevel() ? mc.level : null;
            if (targetLevel != null) {
                ChunkPos chunkPos = new ChunkPos(tileEntity.getTargetPos());
                LevelChunk chunk = targetLevel.getChunk(chunkPos.x, chunkPos.z);
                tileEntity.updateChunkModelFromServer(new PortalChunkDataPacket(tileEntity.getBlockPos(), chunk, tileEntity.getTargetPos()).chunkData);
            } else {
                Networking.INSTANCE.sendToServer(new PortalChunkRequestPacket(
                        tileEntity.getBlockPos(),
                        tileEntity.getTargetLevel(),
                        tileEntity.getTargetPos()
                ));
            }
            tileEntity.lastRequestTime = currentTime;
        }
    }

    private void renderPortalFrame(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay) {
        Matrix4f matrix = poseStack.last().pose();
        float size = 1.0f;


        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        consumer.vertex(matrix, -size, -size, 0).color(0, 128, 255, 255)
                .uv(0, 0).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 0, 1).endVertex();
        consumer.vertex(matrix, size, -size, 0).color(0, 128, 255, 255)
                .uv(1, 0).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 0, 1).endVertex();
        consumer.vertex(matrix, size, size, 0).color(0, 128, 255, 255)
                .uv(1, 1).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 0, 1).endVertex();
        consumer.vertex(matrix, -size, size, 0).color(0, 128, 255, 255)
                .uv(0, 1).overlayCoords(packedOverlay).uv2(packedLight).normal(0, 0, 1).endVertex();

        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 16;
    }
}