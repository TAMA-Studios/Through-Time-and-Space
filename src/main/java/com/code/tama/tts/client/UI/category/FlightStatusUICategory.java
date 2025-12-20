/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.ThreadLocalRandom;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class FlightStatusUICategory extends UICategory {
	public static final ResourceLocation SPEEDOMETER = new ResourceLocation(MODID, "textures/gui/speedometer.png");
	public FlightStatusUICategory() {
		super();
		this.overlay = new ResourceLocation(MODID, "textures/gui/overlay_large_title.png");
	}

	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		assert monitor.getLevel() != null;
		GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			int white = 0xFFFFFF;

			RenderSystem.disableDepthTest();

			String flightState;
			long flightTicks = cap.GetFlightData().getTicksInFlight();
			long destTicks = cap.GetFlightData().getTicksUntilArrival();

			if (cap.GetFlightData().IsTakingOff())
				flightState = "Taking Off";
			else if (cap.GetFlightData().isInFlight())
				flightState = "In Flight";
			else if (!cap.GetFlightData().isInFlight() && !cap.GetFlightData().IsTakingOff())
				flightState = "Landed";
			else
				flightState = "N/A";

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(style(monitor)), -40, 5, white, false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			poseStack.pushPose();
			poseStack.scale(0.9f, 0.9f, 0f);
			fontRenderer.drawInBatch(Component.literal("Flight Status").withStyle(style(monitor)), -32.5f, 17, white,
					false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);
			poseStack.popPose();

			poseStack.pushPose();
			poseStack.scale(0.7f, 0.7f, 0.7f);
			poseStack.translate(-15, 12.5, 0);
			RenderText(monitor, String.format("Flight Status: %s", flightState), poseStack, bufferSource, -40, 25);
			if (!flightState.equals("Landed")) {
				RenderText(monitor, String.format("Time in Flight: %s T | %s S | %s M", flightTicks, flightTicks / 20,
						(flightTicks / 20) / 60), poseStack, bufferSource, -40, 35);
				RenderText(monitor, String.format("Time until Destination reached: %s T | %s S | %s M", destTicks,
						destTicks / 60, (destTicks / 60) / 60), poseStack, bufferSource, -40, 45);
			}

			poseStack.popPose();

			drawDriftBar(poseStack, cap.GetFlightData().getDrift() + (cap.GetFlightData().isInFlight() ? ThreadLocalRandom.current().nextFloat(3) - 1.5f : 0), cap.GetData().getControlData().getHelmicRegulator());

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

	public void drawDriftBar(PoseStack stack, float rot, float slide) {
		stack.pushPose();

		BufferBuilder builder = Tesselator.getInstance().getBuilder();

		stack.scale(2.5f, 2.5f, 0);
		stack.translate(-5, 30, 1);

		stack.pushPose();
		stack.translate(-9.5, -10, 0);


		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);

		RenderSystem.setShaderTexture(0, SPEEDOMETER);

		RenderSystem.setShaderColor(1, 1, 1, 1);

		builder.vertex(stack.last().pose(), 20, 0, 0).color(0xFF00569F).uv(0, 0).endVertex();
		builder.vertex(stack.last().pose(), 0, 0, 0).color(0xFF00569F).uv(1, 0).endVertex();
		builder.vertex(stack.last().pose(), 0, 10, 0).color(0xFF00569F).uv(1, 1).endVertex();
		builder.vertex(stack.last().pose(), 20, 10, 0).color(0xFF00569F).uv(0, 1).endVertex();


		RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
//		BufferUploader.drawWithShader(builder.end());
		Tesselator.getInstance().end();

		stack.popPose();

		stack.pushPose();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		stack.mulPose(Axis.ZN.rotationDegrees(rot));

		stack.translate(0, -10, 0);
		RenderSystem.setShaderColor(1, 1, 1, 1);

		builder.vertex(stack.last().pose(), 0, 0, 0).color(0XFF005AA7).endVertex();
		builder.vertex(stack.last().pose(), 0, 10, 0).color(0XFF005AA7).endVertex();
		builder.vertex(stack.last().pose(), 1, 10, 0).color(0XFF005AA7).endVertex();
		builder.vertex(stack.last().pose(), 1, 0, 0).color(0XFF005AA7).endVertex();

		Tesselator.getInstance().end();

		stack.popPose();

		stack.translate(-slide + 5, 1.5, 0);

		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		RenderSystem.setShaderColor(1, 1, 1, 1);

		builder.vertex(stack.last().pose(), 0, 0, 0).color(0XFF0062B6).endVertex();
		builder.vertex(stack.last().pose(), 0, 1, 0).color(0XFF0062B6).endVertex();
		builder.vertex(stack.last().pose(), 1, 1, 0).color(0XFF0062B6).endVertex();
		builder.vertex(stack.last().pose(), 1, 0, 0).color(0XFF0062B6).endVertex();

		Tesselator.getInstance().end();

		stack.popPose();
	}
}
