/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class HoloOverlayRenderer {
	public static int light = 0xf00f0;
	public static int white = 0xFFFFFF;

	public static void Render(PoseStack stack, MultiBufferSource.BufferSource bufferSource) {
		Player player = Minecraft.getInstance().player;
		if (player.getMainHandItem().getItem() instanceof SonicItem sonicItem) {
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			BufferBuilder builder = Tesselator.getInstance().getBuilder();
			builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/holo/overlay.png"));

			Window window = Minecraft.getInstance().getWindow();
			stack.pushPose();
			stack.translate(window.getGuiScaledWidth() / 2, window.getScreenHeight() / 2, 0);

			// Main square
			builder.vertex(stack.last().pose(), 0, window.getScreenHeight(), 0).uv(0, 1).endVertex();
			builder.vertex(stack.last().pose(), window.getGuiScaledWidth(), window.getScreenHeight(), 0).uv(1, 1)
					.endVertex();
			builder.vertex(stack.last().pose(), window.getGuiScaledWidth(), 0, 0).uv(1, 0).endVertex();
			builder.vertex(stack.last().pose(), 0, 0, 0).uv(0, 0).endVertex();

			stack.popPose();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			BufferUploader.drawWithShader(builder.end());

			RenderSystem.disableBlend();
		}
	}
}
