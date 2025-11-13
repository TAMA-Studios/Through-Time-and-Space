/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.tts.server.items.blocks.CompressedMultiblockItem;
import com.code.tama.tts.server.items.blocks.ConsoleItem;
import com.code.tama.tts.server.items.blocks.ExteriorItem;
import com.code.tama.tts.server.items.core.NozzleItem;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.code.tama.tts.server.items.gadgets.TemporalImprintReaderItem;
import com.code.tama.tts.server.registries.TTSRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

import static com.code.tama.tts.TTSMod.registrate;

@SuppressWarnings("deprecation")
public class TTSItems {
	public static final RegistryEntry<Item> HUDOLIN_CONSOLE_TILE;

	public static final RegistryEntry<Item> COMPRESSED_MULTIBLOCK_ITEM;

	public static final RegistryEntry<Item> NESS_CONSOLE_TILE;

	public static final ItemEntry<ExteriorItem> EXTERIOR;

	public static final RegistryEntry<Item> HUON_BOTTLE;

	public static final RegistryEntry<Item> RAW_ZEITON;

	public static final RegistryEntry<Item> BASIC_CONTROL_CIRCUIT;

	public static final RegistryEntry<Item> ADVANCED_CONTROL_CIRCUIT;

	public static final RegistryEntry<SonicItem> CORAL_SONIC;

	public static final RegistryEntry<SonicItem> COPPER_SONIC;

	public static final RegistryEntry<NozzleItem> BASIC_NOZZLE;

	public static final RegistryEntry<Item> ZEITON;

	public static final RegistryEntry<Item> PLASMIC_SHELL_PLATING;

	public static final RegistryEntry<Item> STRUCTURAL_BEAMS;

	public static final RegistryEntry<Item> GROWTH_CAKE;

	public static final RegistryEntry<TemporalImprintReaderItem> TEMPORAL_IMPRINT_READER;

	static {
		EXTERIOR = registrate()
				.item("exterior", (prop) -> new ExteriorItem(TTSBlocks.EXTERIOR_BLOCK.get(), new Item.Properties()))
				.register();

		COMPRESSED_MULTIBLOCK_ITEM = register("compressed_multiblock_item",
				new CompressedMultiblockItem(new Item.Properties()));

		ZEITON = register("purified_zeiton_7", new Item(new Item.Properties()));

		RAW_ZEITON = register("zeiton_7", new Item(new Item.Properties()));

		HUON_BOTTLE = register("huon_bottle", new Item(new Item.Properties().food(new FoodProperties.Builder()
				.alwaysEat().effect(new MobEffectInstance(MobEffects.CONFUSION, 1, 1), 1f).build())));

		HUDOLIN_CONSOLE_TILE = register("hudolin_console_block", new ConsoleItem<>(TTSTileEntities.HUDOLIN_CONSOLE_TILE,
				TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(), new Item.Properties()));

		NESS_CONSOLE_TILE = register("ness_console_block", new ConsoleItem<>(TTSTileEntities.NESS_CONSOLE_TILE,
				TTSBlocks.NESS_CONSOLE_BLOCK.get(), new Item.Properties()));

		CORAL_SONIC = register("sonic/coral", new SonicItem(new Item.Properties(), 5));

		COPPER_SONIC = register("sonic/copper", new SonicItem(new Item.Properties(), 5));

		BASIC_NOZZLE = register("basic_nozzle", new NozzleItem());

		BASIC_CONTROL_CIRCUIT = register("basic_control_circuit", new Item(new Item.Properties()));

		ADVANCED_CONTROL_CIRCUIT = register("advanced_control_circuit", new Item(new Item.Properties()));

		PLASMIC_SHELL_PLATING = register("plasmic_shell_plating",
				new Item(new Item.Properties().fireResistant().stacksTo(16)));

		STRUCTURAL_BEAMS = register("structural_beams", new Item(new Item.Properties().stacksTo(16)));

		GROWTH_CAKE = register("growth_cake", new Item(new Item.Properties()));

		TEMPORAL_IMPRINT_READER = register("temporal_imprint_reader", new TemporalImprintReaderItem());
	}

	public static <T extends Item> RegistryEntry<T> register(String name, T item) {
		return registrate().item(name, (prop) -> item).register();
	}

	public static <T extends Item> RegistryEntry<T> register(String name, NonNullFunction<Item.Properties, T> item) {
		return registrate().item(name, item).register();
	}

	public static <T extends Item> ItemBuilder<T, TTSRegistrate> Builder(String name, T item) {
		return registrate().item(name, (prop) -> item);
	}

	public static List<RegistryEntry<Item>> AllValues() {
		return registrate().getAll(Registries.ITEM).stream().toList();
	}

	public static void register() {

	}
}
