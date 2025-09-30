/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.StencilUtils;
import com.code.tama.tts.client.BlockBakedModel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
    private final BlockEntityRendererProvider.Context context;
    private float lastRenderTick = -1;
    private final Minecraft mc = Minecraft.getInstance();

    private static VertexBuffer MODEL_VBO;

    public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public int getViewDistance() {
        return 24;
    }

    @Override
    public void render(
            @NotNull PortalTileEntity tileEntity,
            float partialTick,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        assert mc.level != null;
        if (!mc.level.isClientSide() || tileEntity.getTargetLevel() == null || tileEntity.getTargetPos() == null) {
            return;
        }

        StencilUtils.DrawStencil(poseStack, (pose) -> StencilUtils.drawFrame(pose, 1, 3), (pose) -> {
            long currentTime = mc.level.getGameTime();
            if (mc.getPartialTick() == lastRenderTick) {
                return; // Throttle to once per tick
            }
            lastRenderTick = mc.getPartialTick();

            pose.pushPose();

            if (currentTime - tileEntity.lastUpdateTime >= 10) {
                updateChunkModel(tileEntity);
                tileEntity.lastUpdateTime = currentTime;
            }

            if (MODEL_VBO == null) {
                MODEL_VBO = buildModelVBO(poseStack, tileEntity);
            } else {
                RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);

                MODEL_VBO.bind();
                MODEL_VBO.drawWithShader(
                        poseStack.last().pose(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
                VertexBuffer.unbind();

                MODEL_VBO.bind();
                MODEL_VBO.drawWithShader(
                        poseStack.last().pose(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
                VertexBuffer.unbind();
            }

            //            pose.translate(-6, -10, -8);

            if (false) {
                tileEntity.containers.forEach((container) -> {
                    BlockPos pos = container.getPos();
                    BlockState state = container.getState();
                    int Light = container.getLight();

                    pose.pushPose();
                    pose.translate(pos.getX(), pos.getY(), pos.getZ());

                    BakedModel model =
                            Minecraft.getInstance().getBlockRenderer().getBlockModel(state);

                    // Get the BlockColors instance
                    BlockColors blockColors = Minecraft.getInstance().getBlockColors();

                    // Get the block color (for grass and shit) for the block state at the given position
                    int color = blockColors.getColor(state, Minecraft.getInstance().level, pos, 0);

                    // Extract RGB components (normalize to 0-1 range)
                    float r = ((color >> 16) & 0xFF) / 255.0f;
                    float g = ((color >> 8) & 0xFF) / 255.0f;
                    float b = (color & 0xFF) / 255.0f;
                    Minecraft.getInstance()
                            .getBlockRenderer()
                            .getModelRenderer()
                            .renderModel(
                                    pose.last(),
                                    buffer.getBuffer(RenderType.translucent()),
                                    state,
                                    model,
                                    r,
                                    g,
                                    b,
                                    Light,
                                    packedOverlay);

                    pose.popPose();
                });
            }
            pose.popPose();
        });
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }

    private void updateChunkModel(PortalTileEntity tileEntity) {
        assert mc.level != null;
        if (!mc.level.isClientSide()) return;

        long currentTime = mc.level.getGameTime();
        if (tileEntity.lastRequestTime == 0 || currentTime - tileEntity.lastRequestTime >= 10) {
            Level targetLevel = mc.level.dimension() == tileEntity.getTargetLevel() ? mc.level : null;
            if (targetLevel != null) {
                ChunkPos chunkPos = new ChunkPos(tileEntity.getTargetPos());
                LevelChunk chunk = targetLevel.getChunk(chunkPos.x, chunkPos.z);
                tileEntity.updateChunkModelFromServer(
                        new PortalChunkDataPacketS2C(tileEntity.getBlockPos(), chunk, tileEntity.getTargetPos())
                                .containersL);
            } else {
                Networking.INSTANCE.sendToServer(new PortalChunkRequestPacketC2S(
                        tileEntity.getBlockPos(), tileEntity.getTargetLevel(), tileEntity.getTargetPos()));
            }
            tileEntity.lastRequestTime = currentTime;
        }
    }

    private VertexBuffer buildModelVBO(PoseStack poseStack, PortalTileEntity entity) {
        Minecraft mc = Minecraft.getInstance();

        BakedModel chunk = this.GetChunkModel(entity);
        RandomSource rand = RandomSource.create(42L);

        BufferBuilder buffer = new BufferBuilder((int) Math.pow(16, 3));
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

        // Dump all quads into the buffer
        entity.containers.forEach(container -> {
            BlockColors colors = mc.getBlockColors();
            int color = colors.getColor(container.getState(), Minecraft.getInstance().level, container.getPos(), 0);

            // Extract RGB components (normalize to 0-1 range)
            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;
            for (BakedQuad quad : mc.getBlockRenderer()
                    .getBlockModel(container.getState())
                    .getQuads(container.getState(), null, rand)) {
                buffer.putBulkData(
                        poseStack.last(), quad, r, g, b, 1.0F, container.getLight(), OverlayTexture.NO_OVERLAY, true);
            }
        });

        BufferBuilder.RenderedBuffer rendered = buffer.end();

        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        vbo.bind();
        vbo.upload(rendered);
        VertexBuffer.unbind();

        return vbo;
    }

    public BakedModel GetChunkModel(PortalTileEntity entity) {
        List<BakedQuad> quads = new java.util.ArrayList<>(List.of());
        entity.containers.forEach(container -> {
            quads.addAll(mc.getBlockRenderer()
                    .getBlockModel(container.getState())
                    .getQuads(container.getState(), null, mc.level.random));
        });

        return new BlockBakedModel(quads);
    }
}
