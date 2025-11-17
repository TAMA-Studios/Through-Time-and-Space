/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;
import static com.mojang.math.Axis.ZN;

public class InteriorPropsUICategory extends UICategory {
	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			int light = (int) (cap.GetLightLevel() * 10);
			light -= 1;
			StringBuilder line1 = new StringBuilder();
//			for (int i = 1; i < 15; i++) {
//				if (i <= light) {
//					line1.append("▀");
//				} else
//					line1.append("☐");
//			}

			drawProgressBar(15, light, poseStack);

			RenderSystem.disableDepthTest();

			poseStack.pushPose();

			fontRenderer.drawInBatch(osVer(monitor), -40, 5, color(monitor), false, poseStack.last().pose(), bufferSource,
					Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch("Light", -12.5f, 15, color(monitor), false, poseStack.last().pose(), bufferSource,
					Font.DisplayMode.NORMAL, 0, combinedLight);

			poseStack.scale(1f, 0.5f, 0.5f);
			poseStack.mulPose(ZN.rotationDegrees(90));
			fontRenderer.drawInBatch(line1.toString(), -150, -30, color(monitor), false, poseStack.last().pose(), bufferSource,
					Font.DisplayMode.NORMAL, 0, combinedLight);

			poseStack.popPose();

			RenderSystem.enableDepthTest();
		});
	}

	public void drawProgressBar(int Max, int Progress, PoseStack stack) {
		stack.pushPose();

		BufferBuilder builder = Tesselator.getInstance().getBuilder();

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

		builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 0, Max, 0).color(0xFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 1, Max, 0).color(0xFFFFFF).endVertex();
		builder.vertex(stack.last().pose(), 1, 0, 0).color(0xFFFFFF).endVertex();

		for(int i = 0; i < Progress; i++) {
			builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFFFFFF).endVertex();
			builder.vertex(stack.last().pose(), 0, i, 0).color(0xFFFFFF).endVertex();
			builder.vertex(stack.last().pose(), 1, i, 0).color(0xFFFFFF).endVertex();
			builder.vertex(stack.last().pose(), 1, 0, 0).color(0xFFFFFF).endVertex();
		}

		Tesselator.getInstance().end();

		stack.popPose();
	}
}
