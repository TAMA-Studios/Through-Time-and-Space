/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class DestinationUICategory extends UICategory {
    public DestinationUICategory() {
        super();
        this.overlay = new ResourceLocation(TTSMod.MODID, "textures/gui/overlay_large_title.png");
    }

    @Override
    public void Render(
            AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        monitor.getLevel()
                .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                .ifPresent(cap -> {
                    Font fontRenderer = Minecraft.getInstance().font;

                    String line1 = cap.GetCurrentLevel()
                                    .location()
                                    .getPath()
                                    .substring(0, 1)
                                    .toUpperCase(Locale.ROOT)
                            + cap.GetCurrentLevel()
                                    .location()
                                    .getPath()
                                    .substring(1)
                                    .replace("_", " ");

                    String line3 = cap.GetDestination().ReadableStringShort();

                    int white = 0xFFFFFF;

                    RenderSystem.disableDepthTest();

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
                            "Destination",
                            -27f,
                            15,
                            white,
                            false,
                            poseStack.last().pose(),
                            bufferSource,
                            Font.DisplayMode.NORMAL,
                            0,
                            combinedLight);

                    fontRenderer.drawInBatch(
                            line3,
                            -40,
                            25,
                            white,
                            false,
                            poseStack.last().pose(),
                            bufferSource,
                            Font.DisplayMode.NORMAL,
                            0,
                            combinedLight);
                });
    }
}
