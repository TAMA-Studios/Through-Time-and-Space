/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;

import static com.mojang.math.Axis.ZN;

public class InteriorPropsUICategory extends UICategory {
    @Override
    public void Render(
            AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        monitor.getLevel()
                .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> {
                    Font fontRenderer = Minecraft.getInstance().font;

                    int light = (int) (cap.GetLightLevel() * 10);
                    light -= 1;
                    StringBuilder line1 = new StringBuilder();
                    for (int i = 1; i < 15; i++) {
                        if (i <= light) {
                            line1.append("▀");
                        } else line1.append("☐");
                    }

                    int white = 0xFFFFFF;

                    RenderSystem.disableDepthTest();

                    poseStack.pushPose();

                    fontRenderer.drawInBatch(
                            "TARDISOS - 1.0",
                            -40,
                            5,
                            white,
                            false,
                            poseStack.last().pose(),
                            bufferSource,
                            Font.DisplayMode.NORMAL,
                            0,
                            combinedLight);

                    fontRenderer.drawInBatch(
                            "Light",
                            -12.5f,
                            15,
                            white,
                            false,
                            poseStack.last().pose(),
                            bufferSource,
                            Font.DisplayMode.NORMAL,
                            0,
                            combinedLight);

                    poseStack.scale(1f, 0.5f, 0.5f);
                    poseStack.mulPose(ZN.rotationDegrees(90));
                    fontRenderer.drawInBatch(
                            line1.toString(),
                            -150,
                            -30,
                            white,
                            false,
                            poseStack.last().pose(),
                            bufferSource,
                            Font.DisplayMode.NORMAL,
                            0,
                            combinedLight);

                    poseStack.popPose();
                });
    }
}
