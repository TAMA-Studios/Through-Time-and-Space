/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class PreflightChecklistCategory extends UICategory {
	public static final ResourceLocation CHECKMARK = UniversalCommon.modRL("textures/gui/checkbox.png");
	public PreflightChecklistCategory() {
		super();
		this.overlay = UniversalCommon.modRL("textures/gui/overlay_large_title.png");
	}

	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			int white = 0xFFFFFF;

			RenderSystem.disableDepthTest();

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(style(monitor)), -40, 5, white, false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			poseStack.pushPose();
			poseStack.scale(0.65f, 0.65f, 0f);
			fontRenderer.drawInBatch(Component.literal("Preflight Checklist").withStyle(style(monitor)), -47.5f, 25,
					white, false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);
			poseStack.popPose();

			poseStack.pushPose();
			poseStack.scale(0.7f, 0.7f, 0.7f);
			poseStack.translate(0, 14, 0);
			RenderText(monitor, "Coordinate Lock", poseStack, bufferSource, -40, 25);
			RenderText(monitor, "Vortex Anchor Up", poseStack, bufferSource, -40, 40);
			RenderText(monitor, "Power Switch", poseStack, bufferSource, -40, 55);

			poseStack.popPose();

			drawDriftBar(poseStack, 0, cap.GetData().getControlData().isCoordinateLock());
			drawDriftBar(poseStack, 1, cap.GetData().getControlData().isVortexAnchor());
			drawDriftBar(poseStack, 2, cap.GetData().isPowered());

			// poseStack.pushPose();
			//
			////			poseStack.translate(25, 70, 0);
////			poseStack.scale(20, 20, 20);
////			poseStack.mulPose(Axis.YP.rotationDegrees(180));
			//
			////			poseStack.mulPose(Axis.XP.rotationDegrees(45));
			// BufferBuilder builder = Tesselator.getInstance().getBuilder();
			//
			// builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
			//
			//
			// builder.vertex(poseStack.last().pose(), 0, 0, 0).color(0xFFFFFF).endVertex();
			// builder.vertex(poseStack.last().pose(), 1, 0, 0).color(0xFFFFFF).endVertex();
			// builder.vertex(poseStack.last().pose(), 1, 1, 0).color(0xFFFFFF).endVertex();
			// builder.vertex(poseStack.last().pose(), 0, 1, 0).color(0xFFFFFF).endVertex();
			//
			// Tesselator.getInstance().end();
			// poseStack.popPose();
		});
	}

	public void drawDriftBar(PoseStack stack, float why, boolean checked) {
		stack.pushPose();

		BufferBuilder builder = Tesselator.getInstance().getBuilder();

		stack.translate(-35, 30 + (why * 10), 1);
		stack.scale(.5f, .5f, 0);

		stack.pushPose();
		stack.translate(-9.5, -10, 0);

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

		RenderSystem.setShaderTexture(0, UniversalCommon.modRL("textures/gui/checkbox.png"));

		RenderSystem.setShaderColor(1, 1, 1, 1);

		builder.vertex(stack.last().pose(), 20, 0, 0).color(0xFFFFFFFF).uv(checked ? 0 : 0.5f, 0).endVertex();
		builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFFFFFFFF).uv(checked ? 0.5f : 1, 0).endVertex();
		builder.vertex(stack.last().pose(), 0, 20, 0).color(0xFFFFFFFF).uv(checked ? 0.5f : 1, 1).endVertex();
		builder.vertex(stack.last().pose(), 20, 20, 0).color(0xFFFFFFFF).uv(checked ? 0 : 0.5f, 1).endVertex();

		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
		// BufferUploader.drawWithShader(builder.end());
		Tesselator.getInstance().end();

		stack.popPose();

		stack.popPose();
	}
}
