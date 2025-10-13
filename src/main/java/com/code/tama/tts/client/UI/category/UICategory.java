/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.registries.UICategoryRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@Getter
public class UICategory {
    public static final Component OS_VER = Component.literal("TARDISOS - 1.0");
    protected int ID;
    ResourceLocation overlay = new ResourceLocation(TTSMod.MODID, "textures/gui/overlay.png");

    public UICategory() {
        this.ID = UICategoryRegistry.getID();
    }

    public void Render(
            AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        // This method is intentionally left blank. It can be overridden by subclasses.
    }
}
