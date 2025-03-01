package com.code.tama.mtm.DataGen.loot;

import com.code.tama.mtm.Blocks.MBlocks;
import com.code.tama.mtm.Items.MItems;
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
        this.dropSelf(MBlocks.ZEITON_BLOCK.get());
        this.dropSelf(MBlocks.RAW_ZEITON_BLOCK.get());

        this.add(MBlocks.ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MBlocks.ZEITON_ORE.get(), MItems.RAW_ZEITON.get()));
        this.add(MBlocks.DEEPSLATE_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MBlocks.DEEPSLATE_ZEITON_ORE.get(), MItems.RAW_ZEITON.get()));
        this.add(MBlocks.NETHER_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MBlocks.NETHER_ZEITON_ORE.get(), MItems.RAW_ZEITON.get()));
        this.add(MBlocks.END_STONE_ZEITON_ORE.get(),
                block -> createCopperLikeOreDrops(MBlocks.END_STONE_ZEITON_ORE.get(), MItems.RAW_ZEITON.get()));

        this.dropSelf(MBlocks.GALLIFREYAN_OAK_STAIRS.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_BUTTON.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_PRESSURE_PLATE.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_TRAPDOOR.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_FENCE.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_FENCE_GATE.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_WALL.get());
        this.dropSelf(MBlocks.GALLIFREYAN_SAND.get());

        this.add(MBlocks.GALLIFREYAN_OAK_SLAB.get(),
                block -> createSlabItemTable(MBlocks.GALLIFREYAN_OAK_SLAB.get()));
        this.add(MBlocks.GALLIFREYAN_OAK_DOOR.get(),
                block -> createDoorTable(MBlocks.GALLIFREYAN_OAK_DOOR.get()));
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

        this.dropSelf(MBlocks.GALLIFREYAN_OAK_LOG.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_WOOD.get());
        this.dropSelf(MBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get());
        this.dropSelf(MBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get());
        this.dropSelf(MBlocks.GALLIFREYAN_OAK_PLANKS.get());

        this.add(MBlocks.GALLIFREYAN_OAK_LEAVES.get(), block ->
                createLeavesDrops(block, MBlocks.GALLIFREYAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(MBlocks.GALLIFREYAN_SAPLING.get());
        this.dropOther(MBlocks.EXTERIOR_BLOCK.get(), Blocks.AIR);

        this.dropOther(MBlocks.HUDOLIN_CONSOLE_BLOCK.get(), Blocks.AIR);

        this.dropSelf(MBlocks.CHAMELEON_CIRCUIT_BLOCK.get());
        this.dropSelf(MBlocks.COORDINATE_PANEL.get());
        this.dropSelf(MBlocks.DESTINATION_INFO_PANEL.get());
        this.dropSelf(MBlocks.LIGHT_PANEL.get());
        this.dropSelf(MBlocks.DOOR_BLOCK.get());
        this.dropOther(MBlocks.HARTNELL_DOOR_PLACEHOLDER.get(), MBlocks.HARTNELL_DOOR.get());
        this.dropSelf(MBlocks.HARTNELL_DOOR.get());
        this.dropSelf(MBlocks.MONITOR_BLOCK.get());
        this.dropSelf(MBlocks.THROTTLE.get());
        this.dropSelf(MBlocks.TOYOTA_THROTTLE.get());
        this.dropSelf(MBlocks.POWER_LEVER.get());

        this.dropSelf(MBlocks.PORTAL_BLOCK.get());

        this.dropSelf(MBlocks.AMETHYST_ROTOR.get());
        this.dropSelf(MBlocks.BLUE_ROTOR.get());
        this.dropSelf(MBlocks.COPPER_ROTOR.get());
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
        return MBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}