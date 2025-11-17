/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

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

			Component line1 = Component
					.literal(cap.GetCurrentLevel().location().getPath().substring(0, 1).toUpperCase(Locale.ROOT)
							+ cap.GetCurrentLevel().location().getPath().substring(1).replace("_", " "))
					.setStyle(style(monitor));

			Component line2 = Component.literal(cap.GetNavigationalData().GetExteriorLocation().ReadableStringShort());

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(style(monitor)), -40, 5, color(monitor), false, poseStack.last().pose(),
					bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch(Component.literal("Location").withStyle(style(monitor)), -22.5f, 15, color(monitor), false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch(line1, -40, 25, color(monitor), false, poseStack.last().pose(), bufferSource,
					Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch(line2, -40, 35, color(monitor), false, poseStack.last().pose(), bufferSource,
					Font.DisplayMode.NORMAL, 0, combinedLight);
		});
	}
}
