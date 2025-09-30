/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
            @NotNull PoseStack pose,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        assert mc.level != null;
        if (!mc.level.isClientSide() || tileEntity.getTargetLevel() == null || tileEntity.getTargetPos() == null) {
            return;
        }

//        StencilUtils.DrawStencil(poseStack, (pose) -> StencilUtils.drawFrame(pose, 1, 3), (pose) -> {
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

            if(mc.level.getGameTime() % 40 == 0)
                MODEL_VBO = buildModelVBO(pose, tileEntity);

            if (MODEL_VBO == null) {
                MODEL_VBO = buildModelVBO(pose, tileEntity);
            } else {
                pose.pushPose();
                pose.translate(0.5, 0.5, 0.5);

                RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
                RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

                RenderSystem.enableDepthTest();
                RenderSystem.disableCull();

                MODEL_VBO.bind();
                MODEL_VBO.drawWithShader(
                        pose.last().pose(),
                        RenderSystem.getProjectionMatrix(),
                        RenderSystem.getShader()
                );
                VertexBuffer.unbind();

                pose.popPose();
            }

            pose.popPose();
//            mc.getBlockRenderer().getModelRenderer().renderModel(pose.last(), buffer.getBuffer(RenderType.translucent()), null, GetChunkModel(tileEntity, poseStack), 1, 1, 1, 0xf000f0, packedOverlay);


            //            pose.translate(-6, -10, -8);
//        });
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

            poseStack.pushPose();

            poseStack.translate(container.getPos().getX(), container.getPos().getY(), container.getPos().getZ());
            for (BakedQuad quad : getModelFromBlock(container.getState(), entity)) {
                buffer.putBulkData(
                        poseStack.last(), quad, r, g, b, 1.0F, container.getLight(), OverlayTexture.NO_OVERLAY, false);
            }

            poseStack.popPose();
        });

        BufferBuilder.RenderedBuffer rendered = buffer.end();

        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        vbo.bind();
        vbo.upload(rendered);
        VertexBuffer.unbind();

        return vbo;
    }
    public List<BakedQuad> getModelFromBlock(BlockState state, PortalTileEntity tileEntity) {

        BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();

        ModelBlockRenderer modelRenderer = blockRenderer.getModelRenderer();

        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        RandomSource rand = RandomSource.create(42L);

        Direction[] directions = Direction.values();

        BakedModel model = blockRenderer.getBlockModel(state);

        List<BakedQuad> quads = new java.util.ArrayList<>(List.of());

        // render only non-occluded faces

        for (Direction dir : directions) {
//            BlockPos neighbourPos = pos.relative(dir);
//            BlockState neighbourState = tileEntity.stateMap.get(neighbourPos);
//            boolean occluded = neighbourState != null &&
//                    neighbourState.isSolidRender(Minecraft.getInstance().level, neighbourPos);

            if (true) { // !occluded
                // pull just this side’s quads and render them
                quads.addAll(model.getQuads(state, dir, rand));
            }
        }
        return quads;
    };


}