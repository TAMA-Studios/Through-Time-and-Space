/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.gadgets;

import com.code.tama.tts.server.tileentities.CompressedMultiblockTile;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;

public class CompressedMultiblockRenderer<T extends CompressedMultiblockTile> implements BlockEntityRenderer<T> {
	public static final int fullBright = 0xf000f0;
	public final BlockEntityRendererProvider.Context context;

	public CompressedMultiblockRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(@NotNull T tile, // Make sure this is the Tile Entity class
			float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
			int combinedOverlay, int combinedLight) {
		poseStack.pushPose();
		poseStack.translate(0.5f, 0.5f, 0.5f); // Shift center of structure to origin (accounts for -1 to 2 span)
		poseStack.scale(1 / 3f, 1 / 3f, 1 / 3f); // Shrink to fit inside 1x1x1
		// poseStack.translate(0.5f, 0.5f, 0.5f); // Move shrunk structure to block
		// center
		tile.stateMap.forEach((pos, state) -> {
			poseStack.pushPose();
			poseStack.translate(pos.getX() - 0.5, pos.getY() - 0.5, pos.getZ() - 0.5); // Position this mini block
																						// relatively

			BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);
			// Get the BlockColors instance
			BlockColors blockColors = Minecraft.getInstance().getBlockColors();

			// Get the grass color for the block state at the given position
			int color = blockColors.getColor(state, Minecraft.getInstance().level, pos, 0); // 'level' is the Level,
																							// 'pos' is the BlockPos

			// Extract RGB components (normalize to 0-1 range)
			float r = ((color >> 16) & 0xFF) / 255.0f;
			float g = ((color >> 8) & 0xFF) / 255.0f;
			float b = (color & 0xFF) / 255.0f;
			Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(),
					bufferSource.getBuffer(RenderType.translucent()), state, model, r, g, b, combinedLight,
					combinedOverlay);

			poseStack.popPose();
		});
		poseStack.popPose();
	}
}
