package com.code.tama.mtm.Items;

import com.code.tama.mtm.Blocks.MBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.mtm.MODID;

public class MItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<Item> DIMENSIONAL_ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

    public static final RegistryObject<Item> SMITH_EXTERIOR;

    public static final RegistryObject<Item> CONSOLE_TILE;

    public static final RegistryObject<SonicItem> SONIC_SCREWDRIVER;

    public static final RegistryObject<Item> ZEITON;

    public static final RegistryObject<Item> RAW_ZEITON;

    static {
        SMITH_EXTERIOR = ITEMS.register("smith_exterior",
                () -> new ExteriorItem(MBlocks.EXTERIOR_BLOCK.get(), new Item.Properties()));

        ZEITON = ITEMS.register("purified_zeiton_7", () -> new Item(new Item.Properties()));

        RAW_ZEITON = ITEMS.register("zeiton_7", () -> new Item(new Item.Properties()));

        CONSOLE_TILE = ITEMS.register("console_block",
                () -> new ConsoleItem(MBlocks.HUDOLIN_CONSOLE_BLOCK.get(), new Item.Properties()));

        SONIC_SCREWDRIVER = ITEMS.register("sonic_screwdriver",
                () -> new SonicItem(new Item.Properties()));
    }
}
