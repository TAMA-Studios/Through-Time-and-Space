/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.triggerapi.StencilUtils;
import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;

import static com.code.tama.tts.TTSMod.MODID;

public class BOTIUICategory extends UICategory {
    public BOTIUICategory() {
        super();
        this.overlay = new ResourceLocation(MODID, "textures/gui/overlay_large_title.png");
    }

    @Override
    public void Render(
            AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        assert monitor.getLevel() != null;

        poseStack.popPose();


        StencilUtils.DrawStencil(poseStack, (pose) -> {
            pose.pushPose();

            pose.translate(0.005, 0.005, 0.005);
            pose.scale(0.99f, 0.99f, 0.99f);

            pose.translate(0.5, 0.5, 1);
            pose.mulPose(Axis.YP.rotationDegrees(180));

            StencilUtils.drawFrame(pose, 1, 1);

            pose.popPose();
        }, (pose) -> {

            pose.pushPose();

            pose.translate(0.5, 0.5, 0.5);

            pose.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().level.getGameTime() % 360));

            pose.scale(0.05f, 0.05f, 0.05f);

            BOTIUtils.Render(pose, bufferSource, monitor);

            pose.popPose();
        });

        poseStack.pushPose();
    }
}
