/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import static com.code.tama.tts.TTSMod.registrate;

import java.util.List;

import com.code.tama.tts.manual.ManualItem;
import com.code.tama.tts.server.items.TwineItem;
import com.code.tama.tts.server.items.core.NozzleItem;
import com.code.tama.tts.server.items.gadgets.HoloGlasses;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.code.tama.tts.server.items.gadgets.TemporalImprintReaderItem;
import com.code.tama.tts.server.items.gadgets.VortexManipulatorItem;
import com.code.tama.tts.server.items.tabs.MainTab;
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

@SuppressWarnings("deprecation")
public class TTSItems {
	// public static final ItemEntry<Item> HUDOLIN_CONSOLE_TILE;

	// public static final ItemEntry<Item> NESS_CONSOLE_TILE;

	@MainTab
	public static final ItemEntry<Item> HUON_BOTTLE;

	@MainTab
	public static final ItemEntry<Item> RAW_ZEITON;

	@MainTab
	public static final ItemEntry<Item> BASIC_CONTROL_CIRCUIT;

	@MainTab
	public static final ItemEntry<Item> ADVANCED_CONTROL_CIRCUIT;

	@MainTab
	public static final ItemEntry<SonicItem> CORAL_SONIC;

	@MainTab
	public static final ItemEntry<SonicItem> COPPER_SONIC;

	@MainTab
	public static final ItemEntry<HoloGlasses> HOLO_GLASSES;

	@MainTab
	public static final ItemEntry<NozzleItem> BASIC_NOZZLE;

	@MainTab
	public static final ItemEntry<Item> ZEITON = registrate().item("purified_zeiton_7", Item::new).register();

	@MainTab
	public static final ItemEntry<TwineItem> TWINE_SPOOL = registrate().item("gadgets/twine_spool", TwineItem::new)
			.defaultModel().defaultLang().register();

	@MainTab
	public static final ItemEntry<Item> PLASMIC_SHELL_PLATING;

	@MainTab
	public static final ItemEntry<Item> STRUCTURAL_BEAMS;

	@MainTab
	public static final ItemEntry<ManualItem> MANUAL;

	@MainTab
	public static final ItemEntry<VortexManipulatorItem> VORTEX_MANIPULATOR;

	@MainTab
	public static final ItemEntry<Item> GROWTH_CAKE;

	@MainTab
	public static final ItemEntry<TemporalImprintReaderItem> TEMPORAL_IMPRINT_READER;

	static {

		RAW_ZEITON = register("zeiton_7", (NonNullFunction<Item.Properties, Item>) Item::new);

		HOLO_GLASSES = registrate().item("holo_glasses", HoloGlasses::new).register();

		VORTEX_MANIPULATOR = registrate().item("gadgets/vortex_manipulator", VortexManipulatorItem::new).register();

		HUON_BOTTLE = registrate().item("huon_bottle", prop -> new Item(prop.food(new FoodProperties.Builder()
				.alwaysEat().effect(new MobEffectInstance(MobEffects.CONFUSION, 1, 1), 1f).build()))).register();

		// HUDOLIN_CONSOLE_TILE = register("hudolin_console_block", new
		// ConsoleItem<>(TTSTileEntities.HUDOLIN_CONSOLE_TILE,
		// TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(), new Item.Properties()));
		//
		// NESS_CONSOLE_TILE = register("ness_console_block", new
		// ConsoleItem<>(TTSTileEntities.NESS_CONSOLE_TILE,
		// TTSBlocks.NESS_CONSOLE_BLOCK.get(), new Item.Properties()));

		CORAL_SONIC = registrate().item("sonic/coral", prop -> new SonicItem(prop, 5)).register();

		COPPER_SONIC = registrate().item("sonic/copper", prop -> new SonicItem(prop, 5)).register();

		BASIC_NOZZLE = registrate().item("basic_nozzle", NozzleItem::new).register();

		BASIC_CONTROL_CIRCUIT = registrate().item("basic_control_circuit", Item::new).register();

		ADVANCED_CONTROL_CIRCUIT = registrate().item("advanced_control_circuit", Item::new).register();

		PLASMIC_SHELL_PLATING = registrate().item("plasmic_shell_plating", Item::new)
				.properties(p -> p.fireResistant().stacksTo(16)).register();

		STRUCTURAL_BEAMS = registrate().item("structural_beams", Item::new).properties(prop -> prop.stacksTo(16))
				.register();

		MANUAL = registrate().item("manual", ManualItem::new).register();

		GROWTH_CAKE = registrate().item("growth_cake", Item::new).register();

		TEMPORAL_IMPRINT_READER = registrate().item("temporal_imprint_reader", TemporalImprintReaderItem::new)
				.properties(prop -> prop.stacksTo(1)).register();
	}

	public static <T extends Item> ItemEntry<T> register(String name, T item) {
		return registrate().item(name, (prop) -> item).register();
	}

	public static <T extends Item> ItemEntry<T> register(String name, NonNullFunction<Item.Properties, T> item) {
		return registrate().item(name, item).register();
	}

	public static <T extends Item> ItemBuilder<T, TTSRegistrate> Builder(String name, T item) {
		return registrate().item(name, (NonNullFunction<Item.Properties, T>) (prop) -> item);
	}

	public static List<RegistryEntry<Item>> AllValues() {
		return registrate().getAll(Registries.ITEM).stream().toList();
	}

	public static void register() {

	}
}
