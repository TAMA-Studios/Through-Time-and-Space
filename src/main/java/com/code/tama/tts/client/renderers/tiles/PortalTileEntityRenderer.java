/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
    private final BlockEntityRendererProvider.Context context;
    private float lastRenderTick = -1;
    private final Minecraft mc = Minecraft.getInstance();

    public boolean mode = true; // 0 - Fast but Innacurate (VBO) 1 - Slow but accurate (Native)

    public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
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

        //        StencilUtils.DrawStencil(poseStack, (pose) -> {
        //            pose.pushPose();
        //            pose.translate(0.5, 1, 0);
        //            StencilUtils.drawFrame(pose, 1, 2);
        //            pose.popPose();
        //        }, (pose) -> {
        long currentTime = mc.level.getGameTime();
        //            if (mc.getPartialTick() == lastRenderTick) {
        //                return; // Throttle to once per tick
        //            }
        lastRenderTick = mc.getPartialTick();

        pose.pushPose();

        // Move to block center
        pose.translate(-6, -6, -6);
        if (currentTime - tileEntity.lastUpdateTime >= 10) {
            BOTIUtils.updateChunkModel(tileEntity);
            tileEntity.lastUpdateTime = currentTime;
        }
        if (mode) {
            if (mc.level.getGameTime() % 40 == 0) tileEntity.MODEL_VBO = BOTIUtils.buildModelVBO(tileEntity.containers);

            if (tileEntity.MODEL_VBO == null) {
                tileEntity.MODEL_VBO = BOTIUtils.buildModelVBO(tileEntity.containers);
            } else {
                pose.pushPose();

                RenderSystem.setupShaderLights(GameRenderer.getPositionColorTexLightmapShader());

                RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
                RenderSystem.setShaderTexture(0, InventoryMenu.BLOCK_ATLAS);

                RenderSystem.enableDepthTest();
                RenderSystem.disableCull();

                tileEntity.MODEL_VBO.bind();
                tileEntity.MODEL_VBO.drawWithShader(
                        pose.last().pose(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
                VertexBuffer.unbind();

                pose.popPose();
            }
        } else {
            tileEntity.containers.forEach(container -> {
                pose.pushPose();
                pose.translate(
                        container.getPos().getX(),
                        container.getPos().getY(),
                        container.getPos().getZ());

                BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(container.getState());
                // Get the BlockColors instance
                BlockColors blockColors = Minecraft.getInstance().getBlockColors();

                // Get the grass color for the block state at the given position
                int color = blockColors.getColor(
                        container.getState(), Minecraft.getInstance().level, container.getPos(), 0);

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
                                container.getState(),
                                model,
                                r,
                                g,
                                b,
                                container.getLight(),
                                OverlayTexture.NO_OVERLAY);

                pose.popPose();
            });
        }

        //            tileEntity.blockEntities.forEach((pos, ent) -> {
        //                pose.pushPose();
        //                pose.translate(pos.getX(), pos.getY(), pos.getZ());
        //                Minecraft.getInstance().getBlockEntityRenderDispatcher().render(ent,
        // Minecraft.getInstance().getPartialTick(), pose, buffer);
        //                pose.popPose();
        pose.popPose();
        //        });
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }
}
