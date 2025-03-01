package com.code.tama.mtm.DataGen;

import com.code.tama.mtm.Blocks.MBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.code.tama.mtm.mtm.MODID;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
//        this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES)
//                .add(MBlocks.SAPPHIRE_ORE.get()).addTag(Tags.Blocks.ORES);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MBlocks.ZEITON_BLOCK.get(),
                        MBlocks.RAW_ZEITON_BLOCK.get(),
                        MBlocks.ZEITON_ORE.get(),
                        MBlocks.DEEPSLATE_ZEITON_ORE.get(),
                        MBlocks.NETHER_ZEITON_ORE.get(),
                        MBlocks.END_STONE_ZEITON_ORE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(MBlocks.ZEITON_BLOCK.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(MBlocks.RAW_ZEITON_BLOCK.get());

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(MBlocks.NETHER_ZEITON_ORE.get());

        this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(MBlocks.END_STONE_ZEITON_ORE.get());

        this.tag(BlockTags.FENCES)
                .add(MBlocks.GALLIFREYAN_OAK_FENCE.get());
        this.tag(BlockTags.FENCE_GATES)
                .add(MBlocks.GALLIFREYAN_OAK_FENCE_GATE.get());
        this.tag(BlockTags.WALLS)
                .add(MBlocks.GALLIFREYAN_OAK_WALL.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(MBlocks.GALLIFREYAN_OAK_LOG.get())
                .add(MBlocks.GALLIFREYAN_OAK_WOOD.get())
                .add(MBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get())
                .add(MBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(MBlocks.GALLIFREYAN_OAK_PLANKS.get());
    }
}