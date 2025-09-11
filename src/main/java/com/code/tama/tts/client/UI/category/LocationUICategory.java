/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class LocationUICategory extends UICategory {
    public LocationUICategory() {
        super();
        this.overlay = new ResourceLocation(MODID, "textures/gui/overlay_large_title.png");
    }

    @Override
    public void Render(
            AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        monitor.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            Font fontRenderer = Minecraft.getInstance().font;

            int white = 0xFFFFFF;

            RenderSystem.disableDepthTest();

            ResourceLocation OLD_HIGH_GALLIFREYAN = new ResourceLocation(MODID, "old_high_gallifreyan");
            ResourceLocation DEFAULT = new ResourceLocation("default");
            ResourceLocation STANDARD_GALACTIC = new ResourceLocation("alt");
            Style STYLE = Style.EMPTY.withFont(DEFAULT);

            Component line1 = Component.literal(cap.GetCurrentLevel()
                                    .location()
                                    .getPath()
                                    .substring(0, 1)
                                    .toUpperCase(Locale.ROOT)
                            + cap.GetCurrentLevel()
                                    .location()
                                    .getPath()
                                    .substring(1)
                                    .replace("_", " "))
                    .setStyle(STYLE);

            Component line2 = Component.literal(cap.GetExteriorLocation().ReadableStringShort());

            fontRenderer.drawInBatch(
                    OS_VER.copy().setStyle(STYLE),
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
                    Component.literal("Location").withStyle(STYLE),
                    -22.5f,
                    15,
                    white,
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    combinedLight);

            fontRenderer.drawInBatch(
                    line1,
                    -40,
                    25,
                    white,
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    combinedLight);

            fontRenderer.drawInBatch(
                    line2,
                    -40,
                    35,
                    white,
                    false,
                    poseStack.last().pose(),
                    bufferSource,
                    Font.DisplayMode.NORMAL,
                    0,
                    combinedLight);

            fontRenderer.drawInBatch(
                    Component.literal("T").setStyle(STYLE),
                    -40,
                    45,
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
