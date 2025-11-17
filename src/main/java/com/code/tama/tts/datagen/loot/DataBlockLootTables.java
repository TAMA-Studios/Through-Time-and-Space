/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen.loot;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class DataBlockLootTables extends BlockLootSubProvider {
	public DataBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
		return createSilkTouchDispatchTable(pBlock,
				this.applyExplosionDecay(pBlock,
						LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
								.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	@Override
	protected void generate() {
//		this.dropSelf(TTSBlocks.ZEITON_BLOCK.get());
//		this.dropSelf(TTSBlocks.RAW_ZEITON_BLOCK.get());
//		this.dropSelf(TTSBlocks.VAROS_ROCKS.get());
//		this.dropSelf(TTSBlocks.ZEITON_BLOCK.get());
//		this.dropSelf(TTSBlocks.RAW_ZEITON_BLOCK.get());
//
//		this.add(TTSBlocks.ZEITON_ORE.get(),
//				block -> createCopperLikeOreDrops(TTSBlocks.ZEITON_ORE.get(), TTSItems.RAW_ZEITON.get()));
//		this.add(TTSBlocks.DEEPSLATE_ZEITON_ORE.get(),
//				block -> createCopperLikeOreDrops(TTSBlocks.DEEPSLATE_ZEITON_ORE.get(), TTSItems.RAW_ZEITON.get()));
//		this.add(TTSBlocks.NETHER_ZEITON_ORE.get(),
//				block -> createCopperLikeOreDrops(TTSBlocks.NETHER_ZEITON_ORE.get(), TTSItems.RAW_ZEITON.get()));
//		this.add(TTSBlocks.END_STONE_ZEITON_ORE.get(),
//				block -> createCopperLikeOreDrops(TTSBlocks.END_STONE_ZEITON_ORE.get(), TTSItems.RAW_ZEITON.get()));
//
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_STAIRS.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_BUTTON.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_PRESSURE_PLATE.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_TRAPDOOR.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_FENCE.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_FENCE_GATE.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_WALL.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_SAND.get());
//
//		this.add(TTSBlocks.GALLIFREYAN_OAK_SLAB.get(),
//				block -> createSlabItemTable(TTSBlocks.GALLIFREYAN_OAK_SLAB.get()));
//		this.add(TTSBlocks.GALLIFREYAN_OAK_DOOR.get(), block -> createDoorTable(TTSBlocks.GALLIFREYAN_OAK_DOOR.get()));
//
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_LOG.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_WOOD.get());
//		this.dropSelf(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_LOG.get());
//		this.dropSelf(TTSBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD.get());
//		this.dropSelf(TTSBlocks.GALLIFREYAN_OAK_PLANKS.get());
//
//		this.add(TTSBlocks.GALLIFREYAN_OAK_LEAVES.get(),
//				block -> createLeavesDrops(block, TTSBlocks.GALLIFREYAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
//
//		this.dropSelf(TTSBlocks.GALLIFREYAN_SAPLING.get());
//		this.dropOther(TTSBlocks.EXTERIOR_BLOCK.get(), Blocks.AIR);
//
//		this.dropSelf(TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get());
//		this.dropSelf(TTSBlocks.NESS_CONSOLE_BLOCK.get());
//
//		this.dropSelf(TTSBlocks.CHAMELEON_CIRCUIT_BLOCK.get());
//		this.dropSelf(TTSBlocks.COORDINATE_PANEL.get());
//		this.dropSelf(TTSBlocks.DESTINATION_INFO_PANEL.get());
//		this.dropSelf(TTSBlocks.LIGHT_PANEL.get());
//		this.dropSelf(TTSBlocks.DOOR_BLOCK.get());
//		this.dropOther(TTSBlocks.HARTNELL_DOOR_PLACEHOLDER.get(), TTSBlocks.HARTNELL_DOOR.get());
//		this.dropSelf(TTSBlocks.HARTNELL_DOOR.get());
//		this.dropSelf(TTSBlocks.MONITOR_BLOCK.get());
//		this.dropSelf(TTSBlocks.THROTTLE.get());
//		this.dropSelf(TTSBlocks.TOYOTA_THROTTLE.get());
//		this.dropSelf(TTSBlocks.POWER_LEVER.get());
//
//		this.dropSelf(TTSBlocks.PORTAL_BLOCK.get());
//
//		this.dropSelf(TTSBlocks.AMETHYST_ROTOR.get());
//		this.dropSelf(TTSBlocks.BLUE_ROTOR.get());
//		this.dropSelf(TTSBlocks.COPPER_ROTOR.get());
//
//		this.dropSelf(TTSBlocks.EXAMPLE_TILE_BLOCK.get());
//		this.dropSelf(TTSBlocks.SONIC_CONFIGURATOR_BLOCK.get());
//
//		this.dropSelf(TTSBlocks.MONITOR_PANEL.get());
//
//		this.dropSelf(TTSBlocks.FRAGMENT_LINKS.get());
//		this.dropSelf(TTSBlocks.DEMATERIALIZATION_CIRCUIT_CORE.get());
//		this.dropSelf(TTSBlocks.NETHER_REACTOR_CORE.get());

		// If a block isn't added, make it drop itself
		for (Block block : this.getKnownBlocks()) {
			if (!this.map.containsKey(block.getLootTable()))
				this.dropSelf(block);
		}
	}

	@Override
	protected @NotNull Iterable<Block> getKnownBlocks() {
		return TTSBlocks.AllValues().stream().map(RegistryEntry::get)::iterator;
	}
}
