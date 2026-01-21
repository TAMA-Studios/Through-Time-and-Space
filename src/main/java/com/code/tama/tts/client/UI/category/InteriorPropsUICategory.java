/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;

public class InteriorPropsUICategory extends UICategory {
	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			int light = (int) (cap.GetLightLevel() * 10);
			light -= 1;
			long line1 = cap.getEnergy().getArtron();
			long line2 = cap.getEnergy().getEnergy();

			RenderSystem.disableDepthTest();

			poseStack.pushPose();

			fontRenderer.drawInBatch(osVer(monitor), -40, 5, color(monitor), false, poseStack.last().pose(),
					bufferSource, Font.DisplayMode.NORMAL, 0, 0xf000f0);

			poseStack.pushPose();
			poseStack.scale(0.5f, 0.5f, 0.5f);
			fontRenderer.drawInBatch("Interior Properties", -12.5f, 15, color(monitor), false, poseStack.last().pose(),
					bufferSource, Font.DisplayMode.NORMAL, 0, 0xf000f0);

			poseStack.popPose();
			fontRenderer.drawInBatch("Stored Energy: " + line1 + "AU", -40, 25, color(monitor), false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 0xf000f0);

			fontRenderer.drawInBatch("Stored Energy: " + line2 + "FE", -40, 35, color(monitor), false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, 0xf000f0);

			poseStack.translate(-20, 0, 0);
			drawProgressBar(15, light, poseStack);

			poseStack.popPose();

			RenderSystem.enableDepthTest();
		});
	}

	public void drawProgressBar(int Max, int Progress, PoseStack stack) {
		stack.pushPose();
		stack.mulPose(Axis.ZP.rotationDegrees(180f));
		stack.scale(3, 3, 3);
		stack.translate(0, -20, 0);
		BufferBuilder builder = Tesselator.getInstance().getBuilder();

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		// builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFFFFFF00).endVertex();
		// builder.vertex(stack.last().pose(), 0, Max, 0).color(0xFFFFFF00).endVertex();
		// builder.vertex(stack.last().pose(), 1, Max, 0).color(0xFFFFFF00).endVertex();
		// builder.vertex(stack.last().pose(), 1, 0, 0).color(0xFFFFFF00).endVertex();

		builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFFFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 0, Progress, 0).color(0xFFFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 1, Progress, 0).color(0xFFFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 1, 0, 0).color(0xFFFFFFFF).endVertex();

		Tesselator.getInstance().end();

		stack.popPose();
	}
}
