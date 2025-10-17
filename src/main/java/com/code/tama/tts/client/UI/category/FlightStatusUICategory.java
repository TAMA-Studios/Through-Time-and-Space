/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public class FlightStatusUICategory extends UICategory {
	public FlightStatusUICategory() {
		super();
		this.overlay = new ResourceLocation(MODID, "textures/gui/overlay_large_title.png");
	}

	@Override
	public void Render(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		monitor.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
			Font fontRenderer = Minecraft.getInstance().font;

			int white = 0xFFFFFF;

			RenderSystem.disableDepthTest();

			ResourceLocation OLD_HIGH_GALLIFREYAN = new ResourceLocation(MODID, "old_high_gallifreyan");
			ResourceLocation DEFAULT = new ResourceLocation("default");
			ResourceLocation STANDARD_GALACTIC = new ResourceLocation("alt");
			Style STYLE = Style.EMPTY.withFont(DEFAULT);

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

			fontRenderer.drawInBatch(OS_VER.copy().setStyle(STYLE), -40, 5, white, false, poseStack.last().pose(),
					bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			fontRenderer.drawInBatch(Component.literal("Flight Status").withStyle(STYLE), -22.5f, 15, white, false,
					poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);

			RenderText(String.format("Flight Status: %s", flightState), poseStack, bufferSource, -40, 25);
			if (!flightState.equals("Landed")) {
				RenderText(String.format("Time in Flight: %s T | %s S | %s M", flightTicks, flightTicks / 60,
						(flightTicks / 60) / 60), poseStack, bufferSource, -40, 35);
				RenderText(String.format("Time until Destination reached: %s T | %s S | %s M", destTicks,
						destTicks / 60, (destTicks / 60) / 60), poseStack, bufferSource, -40, 45);
			}
		});
	}

	public void RenderText(String text, PoseStack stack, MultiBufferSource builder, int x, int y) {
		Minecraft.getInstance().font.drawInBatch(text, x, y, 0xFFFFFF, false, stack.last().pose(), builder,
				Font.DisplayMode.NORMAL, 0, 0xf000f0);
	}
}
