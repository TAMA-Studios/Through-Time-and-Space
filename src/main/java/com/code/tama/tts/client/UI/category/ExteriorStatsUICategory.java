/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.triggerapi.helpers.GravityHelper;
import com.code.tama.triggerapi.helpers.OxygenHelper;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class ExteriorStatsUICategory extends UICategory {
	public ExteriorStatsUICategory() {
		super();
		this.overlay = new ResourceLocation(MODID, "textures/gui/overlay_large_title.png");
	}

	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			RenderSystem.disableDepthTest();

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(style(monitor)), -40, 5, color(monitor), false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			poseStack.pushPose();
			poseStack.scale(0.5f, 0.5f, 0.5f);
			fontRenderer.drawInBatch(Component.literal("Exterior Environmental").withStyle(style(monitor)),
					-60f, 29, color(monitor), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL,
					0, combinedLight);

			fontRenderer.drawInBatch(Component.literal("Readout").withStyle(style(monitor)),
					-22.5f, 37, color(monitor), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL,
					0, combinedLight);
			poseStack.popPose();

			fontRenderer.drawInBatch(Component.literal("Gravity " + GravityHelper.getGravity(cap.GetCurrentLevel())),
					-40, 25, color(monitor), false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0,
					combinedLight);

			fontRenderer.drawInBatch("Oxygen " + OxygenHelper.getO2(cap.GetCurrentLevel()), -40, 35, color(monitor),
					false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);
		});
	}
}
