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

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class FlightStatusUICategory extends UICategory {
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
			long destTicks = cap.GetFlightData().getTicksTillDestination();

			if (cap.GetFlightData().IsTakingOff())
				flightState = "Taking Off";
			else if (cap.GetFlightData().isInFlight())
				flightState = "In Flight";
			else if (!cap.GetFlightData().isInFlight() && !cap.GetFlightData().IsTakingOff())
				flightState = "Landed";
			else
				flightState = "N/A";

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(style(monitor)), -40, 5, white, false, poseStack.last().pose(),
					bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch(Component.literal("Flight Status").withStyle(style(monitor)), -22.5f, 15, white, false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			RenderText(monitor, String.format("Flight Status: %s", flightState), poseStack, bufferSource, -40, 25);
			if (!flightState.equals("Landed")) {
				RenderText(monitor, String.format("Time in Flight: %s T | %s S | %s M", flightTicks, flightTicks / 60,
						(flightTicks / 60) / 60), poseStack, bufferSource, -40, 35);
				RenderText(monitor, String.format("Time until Destination reached: %s T | %s S | %s M", destTicks,
						destTicks / 60, (destTicks / 60) / 60), poseStack, bufferSource, -40, 45);
			}
		});
	}
}
