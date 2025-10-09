/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.server.tileentities.PortalTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
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
            @NotNull PortalTileEntity portal,
            float partialTick,
            @NotNull PoseStack pose,
            @NotNull MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        assert mc.level != null;
        if (portal.getTargetLevel() == null || portal.getTargetPos() == null) {
            return;
        }

//        StencilUtils.DrawStencil(poseStack, (pose) -> {
//            pose.pushPose();

//            pose.translate(0, 0, 1);
//            StencilUtils.drawFrame(pose, 4, 8);
//            pose.translate(0.005, 0.005, 0.005);
//            pose.scale(2f, 2f, 2f);
//
//            pose.pushPose();
//            pose.translate(0.5, 1, 0.5);
//            pose.mulPose(Axis.XP.rotationDegrees(90));
//
//            StencilUtils.drawFrame(pose, 1, 1);
//            pose.popPose();
//
//            pose.pushPose();
//            pose.translate(0.5, 0.5, 0);
//
//            StencilUtils.drawFrame(pose, 1, 1);
//            pose.popPose();
//
//            pose.pushPose();
//            pose.mulPose(Axis.YP.rotationDegrees(90));
//            pose.translate(-0.5, 0.5, 0);
//
//            StencilUtils.drawFrame(pose, 1, 1);
//            pose.popPose();
//
//            pose.pushPose();
//            pose.mulPose(Axis.YP.rotationDegrees(180));
//            pose.translate(-0.5, 0.5, -1);
//
//            StencilUtils.drawFrame(pose, 1, 1);
//            pose.popPose();
//
//            pose.pushPose();
//            pose.mulPose(Axis.YP.rotationDegrees(270));
//            pose.translate(0.5, 0.5, -1);
//
//            StencilUtils.drawFrame(pose, 1, 1);
//            pose.popPose();

//            pose.popPose();
//        }, (pose) -> {

//        StencilUtils.DrawStencil(pose, (stack) -> {
//            stack.pushPose();
//            pose.translate(0, 1, 0);
//            StencilUtils.drawFrame(stack, 2, 2);
//            stack.popPose();
//        }, (stack) -> {
//            stack.pushPose();
//
////            stack.translate(0, 0, 10);
////            stack.scale(100, 100, 100);
////            StencilUtils.drawBlackFrame(stack, 1, 1);
//
//            stack.popPose();
//
//            stack.pushPose();
//                BOTIUtils.RenderStuff(p, portal);
//            stack.popPose();
//        });


//        pose.pushPose();
//        RenderSystem.disableDepthTest();
//        pose.translate(0, 0, 100);
//        pose.scale(100, 100, 100);
//        StencilUtils.drawBlackFrame(pose, 1, 1);
//        RenderSystem.enableDepthTest();
//        pose.popPose();
//
//        pose.pushPose();
//        pose.translate(0.5, 0.5, 0.5);
//            BOTIUtils.RenderStuff(pose, portal);
//        pose.popPose();

        pose.pushPose();

        pose.translate(0.5, 0.5, 0.5);
//            portal.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
//                pose.mulPose(Axis.YP.rotationDegrees(cap.GetNavigationalData().getFacing().toYRot()));
//            });
//            pose.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().level.getGameTime() % 360));

//            pose.scale(0.05f, 0.05f, 0.05f);

        BOTIUtils.Render(pose, buffer, portal);

        pose.popPose();
//        });
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }
}