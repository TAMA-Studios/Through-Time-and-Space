/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.UI.category;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class FaultLocatorCategory extends UICategory {
	public static final ResourceLocation CHECKMARK = UniversalCommon.modRL("textures/gui/checkbox.png");
	public FaultLocatorCategory() {
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
			fontRenderer.drawInBatch(Component.literal("Fault Locator").withStyle(style(monitor)), -47.5f, 25, white,
					false, poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, combinedLight);
			poseStack.popPose();

			poseStack.pushPose();
			poseStack.scale(0.7f, 0.7f, 0.7f);
			poseStack.translate(0, 14, 0);

			List<String> faults = new ArrayList<>();

			if (!cap.GetData().getSubSystemsData().DematerializationCircuit.isActivated(cap.GetLevel()))
				faults.add("E.DM");
			if (!cap.GetData().getSubSystemsData().DynamorphicController.isActivated(cap.GetLevel()))
				faults.add("E.DC");
			if (!cap.GetData().getSubSystemsData().DynamorphicGeneratorStacks.isEmpty())
				faults.add("E.DG");
			if (!cap.GetData().getSubSystemsData().NetherReactorCoreSubsystem.isActivated(cap.GetLevel()))
				faults.add("E.NR");

			if (cap.GetData().getFuel() <= 0 && cap.getEnergy().getEnergy() <= 0)
				faults.add("E.PW");

			int x = -40;
			int y = 25;
			for (String fault : faults) {
				y += 15;
				x += 15;
				if (x > -10)
					x = -40;
				if (y > 55)
					y = 25;

				RenderText(monitor, fault, poseStack, bufferSource, x, y);
			}

			poseStack.popPose();

		});
	}
}
