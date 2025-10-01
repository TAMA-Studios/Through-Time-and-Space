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

        // Uncomment these lines to re-enable the stencil buf
        //        StencilUtils.DrawStencil(poseStack, (pose) -> {
        //            pose.pushPose();
        //            pose.translate(0.5, 1, 0);
        //            StencilUtils.drawFrame(pose, 1, 2);
        //            pose.popPose();
        //        }, (pose) -> {

        BOTIUtils.Render(pose, buffer, portal);
        //        });
    }

    @Override
    public boolean shouldRenderOffScreen(@NotNull PortalTileEntity tileEntity) {
        return true;
    }
}
