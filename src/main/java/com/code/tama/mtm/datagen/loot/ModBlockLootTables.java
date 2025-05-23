package com.code.tama.mtm.datagen.loot;

import com.code.tama.mtm.server.registries.MTMBlocks;
import com.code.tama.mtm.server.registries.MTMItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(MTMBlocks.ZEITON_BLOCK.get());
        this.dropSelf(MTMBlocks.RAW_ZEITON_BLOCK.get());
        this.dropSelf(MTMBlocks.VAROS_ROCKS.get());
        this.dropSelf(MTMBlocks.ZEITON_BLOCK.get());
        this.dropSelf(MTMBlocks.RAW_ZEITON_BLOCK.get());

        this.add(MTMBlocks.ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MTMBlocks.ZEITON_ORE.get(), MTMItems.RAW_ZEITON.get()));
        this.add(MTMBlocks.DEEPSLATE_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MTMBlocks.DEEPSLATE_ZEITON_ORE.get(), MTMItems.RAW_ZEITON.get()));
        this.add(MTMBlocks.NETHER_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MTMBlocks.NETHER_ZEITON_ORE.get(), MTMItems.RAW_ZEITON.get()));
        this.add(MTMBlocks.END_STONE_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MTMBlocks.END_STONE_ZEITON_ORE.get(), MTMItems.RAW_ZEITON.get()));

        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_STAIRS.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_BUTTON.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_PRESSURE_PLATE.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_TRAPDOOR.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_FENCE.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_FENCE_GATE.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_WALL.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_SAND.get());

        this.add(MTMBlocks.GALLIFREYAN_OAK_SLAB.get(),
                block -> createSlabItemTable(MTMBlocks.GALLIFREYAN_OAK_SLAB.get()));
        this.add(MTMBlocks.GALLIFREYAN_OAK_DOOR.get(),
                block -> createDoorTable(MTMBlocks.GALLIFREYAN_OAK_DOOR.get()));
//        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
//                .hasBlockStateProperties(MBlocks.STRAWBERRY_CROP.get())
//                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StrawberryCropBlock.AGE, 5));
//
//        this.add(MBlocks.STRAWBERRY_CROP.get(), createCropDrops(MBlocks.STRAWBERRY_CROP.get(), MItems.STRAWBERRY.get(),
//                MItems.STRAWBERRY_SEEDS.get(), lootitemcondition$builder));


//        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
//                .hasBlockStateProperties(MBlocks.CORN_CROP.get())
//                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 7))
//                .or(LootItemBlockStatePropertyCondition
//                        .hasBlockStateProperties(MBlocks.CORN_CROP.get())
//                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 8)));
//
//        // LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
//        //         .hasBlockStateProperties(MBlocks.CORN_CROP.get())
//        //         .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CornCropBlock.AGE, 8));
//
//        this.add(MBlocks.CORN_CROP.get(), createCropDrops(MBlocks.CORN_CROP.get(), MItems.CORN.get(),
//                MItems.CORN_SEEDS.get(), lootitemcondition$builder2));

//        this.dropSelf(MBlocks.CATMINT.get());
//        this.add(MBlocks.POTTED_CATMINT.get(), createPotFlowerItemTable(MBlocks.CATMINT.get()));
//
//        this.dropSelf(MBlocks.GEM_POLISHING_STATION.get());

        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_LOG.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_WOOD.get());
        this.dropSelf(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get());
        this.dropSelf(MTMBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get());
        this.dropSelf(MTMBlocks.GALLIFREYAN_OAK_PLANKS.get());

        this.add(MTMBlocks.GALLIFREYAN_OAK_LEAVES.get(), block ->
                createLeavesDrops(block, MTMBlocks.GALLIFREYAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(MTMBlocks.GALLIFREYAN_SAPLING.get());
        this.dropOther(MTMBlocks.EXTERIOR_BLOCK.get(), Blocks.AIR);

        this.dropOther(MTMBlocks.HUDOLIN_CONSOLE_BLOCK.get(), Blocks.AIR);

        this.dropSelf(MTMBlocks.CHAMELEON_CIRCUIT_BLOCK.get());
        this.dropSelf(MTMBlocks.COORDINATE_PANEL.get());
        this.dropSelf(MTMBlocks.DESTINATION_INFO_PANEL.get());
        this.dropSelf(MTMBlocks.LIGHT_PANEL.get());
        this.dropSelf(MTMBlocks.DOOR_BLOCK.get());
        this.dropOther(MTMBlocks.HARTNELL_DOOR_PLACEHOLDER.get(), MTMBlocks.HARTNELL_DOOR.get());
        this.dropSelf(MTMBlocks.HARTNELL_DOOR.get());
        this.dropSelf(MTMBlocks.MONITOR_BLOCK.get());
        this.dropSelf(MTMBlocks.THROTTLE.get());
        this.dropSelf(MTMBlocks.TOYOTA_THROTTLE.get());
        this.dropSelf(MTMBlocks.POWER_LEVER.get());

        this.dropSelf(MTMBlocks.PORTAL_BLOCK.get());

        this.dropSelf(MTMBlocks.AMETHYST_ROTOR.get());
        this.dropSelf(MTMBlocks.BLUE_ROTOR.get());
        this.dropSelf(MTMBlocks.COPPER_ROTOR.get());
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MTMBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}