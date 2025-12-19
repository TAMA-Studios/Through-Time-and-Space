/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.server.tileentities.FaultLocatorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class FaultLocatorRenderer implements BlockEntityRenderer<FaultLocatorTile> {
	public static final int fullBright = 0xf000f0;
	public final BlockEntityRendererProvider.Context context;

	public FaultLocatorRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(@NotNull FaultLocatorTile example, // Make sure this is the Tile Entity class
			float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
			int combinedLight, int combinedOverlay) {
		if (example.getLevel() == null)
			return;

		// If the TARDIS level cap is present
		GetTARDISCapSupplier(example.getLevel()).ifPresent(cap -> {
			// Do all of this ("cap" is the TARDIS level capability)
			poseStack.pushPose();
				List<String> CODES = new ArrayList<>();
				if(!cap.GetData().getSubSystemsData().DematerializationCircuit.isActivated(cap.GetLevel()))
					CODES.add("0xFF");

				int i = 0;
			for (String code : CODES) {
				RenderText(code, poseStack, bufferSource, 0, (i += 10));
			}
			poseStack.popPose();
		});
	}

	public static void RenderText(String text, PoseStack stack, MultiBufferSource builder,
								  int x, int y) {
		Minecraft.getInstance().font.drawInBatch(Component.literal(text), x, y, 0xFFFFFF, false,
				stack.last().pose(), builder, Font.DisplayMode.NORMAL, 0, 0xf000f0);
	}
}
