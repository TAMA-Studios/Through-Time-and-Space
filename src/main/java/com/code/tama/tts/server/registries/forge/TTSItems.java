/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.forge;

import com.code.tama.tts.server.items.blocks.CompressedMultiblockItem;
import com.code.tama.tts.server.items.blocks.ConsoleItem;
import com.code.tama.tts.server.items.blocks.ExteriorItem;
import com.code.tama.tts.server.items.core.NozzleItem;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.tts.TTSMod.MODID;

@SuppressWarnings("deprecation")
public class TTSItems {
	public static final RegistryObject<Item> HUDOLIN_CONSOLE_TILE;

	public static final RegistryObject<Item> COMPRESSED_MULTIBLOCK_ITEM;

	public static final RegistryObject<Item> NESS_CONSOLE_TILE;

	public static final DeferredRegister<Item> DIMENSIONAL_ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

	public static final RegistryObject<Item> EXTERIOR;

	public static final RegistryObject<Item> HUON_BOTTLE;

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

	public static final RegistryObject<Item> RAW_ZEITON;

	public static final RegistryObject<Item> BASIC_CONTROL_CIRCUIT;

	public static final RegistryObject<Item> ADVANCED_CONTROL_CIRCUIT;

	public static final RegistryObject<SonicItem> CORAL_SONIC;

	public static final RegistryObject<SonicItem> COPPER_SONIC;

	public static final RegistryObject<NozzleItem> BASIC_NOZZLE;

	public static final RegistryObject<Item> ZEITON;

	public static final RegistryObject<Item> PLASMIC_SHELL_PLATING;

	public static final RegistryObject<Item> STRUCTURAL_BEAMS;

	public static final RegistryObject<Item> GROWTH_CAKE;

	static {
		EXTERIOR = ITEMS.register("exterior",
				() -> new ExteriorItem(TTSBlocks.EXTERIOR_BLOCK.get(), new Item.Properties()));

		COMPRESSED_MULTIBLOCK_ITEM = ITEMS.register("compressed_multiblock_item",
				() -> new CompressedMultiblockItem(new Item.Properties()));

		ZEITON = ITEMS.register("purified_zeiton_7", () -> new Item(new Item.Properties()));

		RAW_ZEITON = ITEMS.register("zeiton_7", () -> new Item(new Item.Properties()));

		HUON_BOTTLE = ITEMS.register("huon_bottle",
				() -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat()
						.effect(new MobEffectInstance(MobEffects.CONFUSION, 1, 1), 1f).build())));

		HUDOLIN_CONSOLE_TILE = ITEMS.register("hudolin_console_block",
				() -> new ConsoleItem<>(TTSTileEntities.HUDOLIN_CONSOLE_TILE, TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(),
						new Item.Properties()));

		NESS_CONSOLE_TILE = ITEMS.register("ness_console_block",
				() -> new ConsoleItem<>(TTSTileEntities.NESS_CONSOLE_TILE, TTSBlocks.NESS_CONSOLE_BLOCK.get(),
						new Item.Properties()));

		CORAL_SONIC = ITEMS.register("sonic/coral", () -> new SonicItem(new Item.Properties(), 5));

		COPPER_SONIC = ITEMS.register("sonic/copper", () -> new SonicItem(new Item.Properties(), 5));

		BASIC_NOZZLE = ITEMS.register("basic_nozzle", NozzleItem::new);

		BASIC_CONTROL_CIRCUIT = ITEMS.register("basic_control_circuit", () -> new Item(new Item.Properties()));

		ADVANCED_CONTROL_CIRCUIT = ITEMS.register("advanced_control_circuit", () -> new Item(new Item.Properties()));

		PLASMIC_SHELL_PLATING = ITEMS.register("plasmic_shell_plating",
				() -> new Item(new Item.Properties().fireResistant()));

		STRUCTURAL_BEAMS = ITEMS.register("structural_beams", () -> new Item(new Item.Properties()));

		GROWTH_CAKE = ITEMS.register("growth_cake", () -> new Item(new Item.Properties()));
	}
}
