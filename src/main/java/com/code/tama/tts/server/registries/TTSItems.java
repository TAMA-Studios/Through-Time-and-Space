/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.items.ConsoleItem;
import com.code.tama.tts.server.items.ExteriorItem;
import com.code.tama.tts.server.items.SonicItem;
import com.code.tama.tts.server.items.core.NozzleItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("deprecation")
public class TTSItems {
    public static final RegistryObject<Item> CONSOLE_TILE;

    public static final DeferredRegister<Item> DIMENSIONAL_ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

    public static final RegistryObject<Item> EXTERIOR;

    public static final RegistryObject<Item> HUON_BOTTLE;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

    public static final RegistryObject<Item> RAW_ZEITON;

    public static final RegistryObject<Item> BASIC_CONTROL_CIRCUIT;

    public static final RegistryObject<Item> ADVANCED_CONTROL_CIRCUIT;

    public static final RegistryObject<SonicItem> SONIC_SCREWDRIVER;

    public static final RegistryObject<NozzleItem> BASIC_NOZZLE;

    public static final RegistryObject<Item> ZEITON;

    static {
        EXTERIOR = ITEMS.register(
                "exterior", () -> new ExteriorItem(TTSBlocks.EXTERIOR_BLOCK.get(), new Item.Properties()));

        ZEITON = ITEMS.register("purified_zeiton_7", () -> new Item(new Item.Properties()));

        RAW_ZEITON = ITEMS.register("zeiton_7", () -> new Item(new Item.Properties()));

        HUON_BOTTLE = ITEMS.register(
                "huon_bottle",
                () -> new Item(new Item.Properties()
                        .food(new FoodProperties.Builder()
                                .alwaysEat()
                                .effect(new MobEffectInstance(MobEffects.CONFUSION, 1, 1), 1f)
                                .build())));

        CONSOLE_TILE = ITEMS.register(
                "console_block", () -> new ConsoleItem(TTSBlocks.HUDOLIN_CONSOLE_BLOCK.get(), new Item.Properties()));

        SONIC_SCREWDRIVER = ITEMS.register("sonic_screwdriver", () -> new SonicItem(new Item.Properties()));

        BASIC_NOZZLE = ITEMS.register("basic_nozzle", NozzleItem::new);

        BASIC_CONTROL_CIRCUIT = ITEMS.register("basic_control_circuit", () -> new Item(new Item.Properties()));

        ADVANCED_CONTROL_CIRCUIT = ITEMS.register("advanced_control_circuit", () -> new Item(new Item.Properties()));
    }
}
