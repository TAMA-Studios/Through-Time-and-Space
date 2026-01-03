/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.misc.progressable.IWeldable;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fml.loading.FMLEnvironment;

import com.code.tama.triggerapi.TriggerAPI;
import com.code.tama.triggerapi.helpers.world.RayTraceUtils;

public class DevOverlayRenderer {
	public static int light = 0xf00f0;
	public static int white = 0xFFFFFF;

	public static void Render(PoseStack stack, MultiBufferSource.BufferSource bufferSource) {
		if (FMLEnvironment.production)
			return;
		Player player = Minecraft.getInstance().player;
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		stack.pushPose();
		stack.translate(5, 5, 0);

		Minecraft.getInstance().font.drawInBatch("Through Time and Space: A Development", 0, 0, white, false,
				stack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, light);

		Minecraft.getInstance().font.drawInBatch("Mod Version: " + TriggerAPI.getModVersion(), 0, 10, white, false,
				stack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, light);

		Minecraft.getInstance().font.drawInBatch(
				Minecraft.getInstance().getFps() + " FPS"
						+ (Minecraft.getInstance().getFps() < 20
								? " - This FPS is slower than the beamer your father used to run away"
								: ""),
				0, 20, white, false, stack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, light);

		stack.popPose();

		stack.pushPose();

		stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);

		BlockHitResult hit = RayTraceUtils.getLookingAtBlock(25);
		assert Minecraft.getInstance().level != null;
		if (hit != null)
			if (Minecraft.getInstance().level.getBlockState(hit.getBlockPos()) != null) {
				BlockEntity ent = Minecraft.getInstance().level.getBlockEntity(hit.getBlockPos());
				if (ent != null && ent.getCapability(ForgeCapabilities.ENERGY).isPresent())
					Minecraft.getInstance().font
							.drawInBatch(
									Component.literal(String.format("FE: %s",
											ent.getCapability(ForgeCapabilities.ENERGY).orElseGet(null)
													.getEnergyStored()))
											.withStyle(ChatFormatting.WHITE),
									0, -15, white, false, stack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0,
									light);

				if (ent != null && ent instanceof IWeldable weldable)
					Minecraft.getInstance().font.drawInBatch(
							Component.literal(String.format("Weld: %s", weldable.getWeldProgress()))
									.withStyle(ChatFormatting.WHITE),
							0, -15, white, false, stack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, light);

				// stack.translate(0, 5, 0);

				Minecraft.getInstance().getItemRenderer().renderStatic(
						Minecraft.getInstance().level.getBlockState(hit.getBlockPos()).getBlock().asItem()
								.getDefaultInstance(),
						ItemDisplayContext.GUI, 0xf000f0, OverlayTexture.NO_OVERLAY, stack, bufferSource,
						Minecraft.getInstance().level, 0);
			}

		stack.popPose();

		RenderSystem.disableBlend();
	}
}
