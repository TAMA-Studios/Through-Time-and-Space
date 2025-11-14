/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.items.blocks;

import java.util.function.Consumer;

import com.code.tama.tts.client.renderers.items.CompressedMultiblockItemRenderer;
import com.code.tama.tts.server.registries.forge.TTSBlocks;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CompressedMultiblockItem extends BlockItem {
	public CompressedMultiblockItem(Block block, Properties properties) {
		super(TTSBlocks.COMPRESSED_MULTIBLOCK.get(), properties);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new CompressedMultiblockItemRenderer(getBlock().defaultBlockState());
			}
		});
	}
}
