/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.tags;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataBlockTagGenerator extends BlockTagsProvider {
	public DataBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, MODID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		// this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
		// .add(MBlocks.SAPPHIRE_ORE.get()).addTag(Tags.Blocks.ORES);

		// this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TTSBlocks.ZEITON_BLOCK.get(),
		// TTSBlocks.RAW_ZEITON_BLOCK.get(),
		// TTSBlocks.ZEITON_ORE.get(), TTSBlocks.DEEPSLATE_ZEITON_ORE.get(),
		// TTSBlocks.NETHER_ZEITON_ORE.get(),
		// TTSBlocks.END_STONE_ZEITON_ORE.get());
		//
		// this.tag(BlockTags.NEEDS_IRON_TOOL).add(TTSBlocks.ZEITON_BLOCK.get());
		//
		// this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(TTSBlocks.RAW_ZEITON_BLOCK.get());
		//
		// this.tag(BlockTags.NEEDS_STONE_TOOL).add(TTSBlocks.NETHER_ZEITON_ORE.get());
		//
		// this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(TTSBlocks.END_STONE_ZEITON_ORE.get());
		//
		// this.tag(BlockTags.FENCES).add(TTSBlocks.GALLIFREYAN_OAK_FENCE.get());
		// this.tag(BlockTags.FENCE_GATES).add(TTSBlocks.GALLIFREYAN_OAK_FENCE_GATE.get());
		// this.tag(BlockTags.WALLS).add(TTSBlocks.GALLIFREYAN_OAK_WALL.get());
		//
		// this.tag(BlockTags.LOGS_THAT_BURN).add(TTSBlocks.GALLIFREYAN_OAK_LOG.get())
		// .add(TTSBlocks.GALLIFREYAN_OAK_WOOD.get()).add(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get())
		// .add(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get());
		//
		// this.tag(BlockTags.PLANKS).add(TTSBlocks.GALLIFREYAN_OAK_PLANKS.get());
	}
}
