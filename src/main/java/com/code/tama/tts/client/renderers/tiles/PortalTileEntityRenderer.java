/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.StencilUtils;
import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.inventory.InventoryMenu;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class PortalTileEntityRenderer implements BlockEntityRenderer<PortalTileEntity> {
    private final BlockEntityRendererProvider.Context context;
    private float lastRenderTick = -1;
    private final Minecraft mc = Minecraft.getInstance();

    public static VertexBuffer MODEL_VBO;

    public boolean mode = false; // 0 - Fast but Innacurate (VBO) 1 - Slow but accurate (Native)
    public PortalTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
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

        StencilUtils.DrawStencil(poseStack, (pose) -> StencilUtils.drawFrame(pose, 1, 2), (pose) -> {
            long currentTime = mc.level.getGameTime();
            if (mc.getPartialTick() == lastRenderTick) {
                return; // Throttle to once per tick
            }
            lastRenderTick = mc.getPartialTick();

            pose.pushPose();

            // Move to block center
            pose.translate(0, -10, 0);
            if (currentTime - tileEntity.lastUpdateTime >= 10) {
                BOTIUtils.updateChunkModel(tileEntity);
                tileEntity.lastUpdateTime = currentTime;
            }

            if(mode) {
                if (mc.level.getGameTime() % 40 == 0)
                    MODEL_VBO = BOTIUtils.buildModelVBO(tileEntity);

                if (MODEL_VBO == null) {
                    MODEL_VBO = BOTIUtils.buildModelVBO(tileEntity);
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
            }
            else {
                tileEntity.containers.forEach(container -> {
                    BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(container.getState());
                });
            }

//            tileEntity.blockEntities.forEach((pos, ent) -> {
//                pose.pushPose();
//                pose.translate(pos.getX(), pos.getY(), pos.getZ());
//                Minecraft.getInstance().getBlockEntityRenderDispatcher().render(ent, Minecraft.getInstance().getPartialTick(), pose, buffer);
//                pose.popPose();
            pose.popPose();
        });
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }
}