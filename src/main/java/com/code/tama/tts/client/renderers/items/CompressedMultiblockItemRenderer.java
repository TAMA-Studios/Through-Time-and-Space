/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.items;

import com.code.tama.tts.server.items.blocks.CompressedMultiblockItem;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.CompressedMultiblockTile;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Renderer for the ConsoleTile BlockItem * */
public class CompressedMultiblockItemRenderer extends BlockEntityWithoutLevelRenderer {
	private final CompressedMultiblockTile CompressedMultiblockTile;

	public CompressedMultiblockItemRenderer(BlockState state) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.CompressedMultiblockTile = (CompressedMultiblockTile) TTSTileEntities.COMPRESSED_MULTIBLOCK_TILE.get()
				.create(BlockPos.ZERO, state);
	}

	@Override
	public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context,
			@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {

		if (stack.getItem() instanceof CompressedMultiblockItem) {
			this.CompressedMultiblockTile.load(BlockItem.getBlockEntityData(stack));

			BlockEntityRenderer<CompressedMultiblockTile> renderer = Minecraft.getInstance()
					.getBlockEntityRenderDispatcher().getRenderer(this.CompressedMultiblockTile);

			poseStack.pushPose();

			poseStack.scale(0.5f, 0.5f, 0.5f);
			poseStack.translate(0.5, 0.5, 0.5);

			if (renderer != null)
				renderer.render(this.CompressedMultiblockTile, 0, poseStack, buffer, packedLight, packedOverlay);

			poseStack.popPose();
		}
	}
}
