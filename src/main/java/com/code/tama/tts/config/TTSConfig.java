/* (C) TAMA Studios 2025 */
package com.code.tama.tts.config;

import com.code.tama.tts.TTSMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TTSMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TTSConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static final ForgeConfigSpec.IntValue EXAMPLE_BUILDER = BUILDER.comment("example comment").defineInRange("example",
            10, // Default
            0, // Minimum
            Integer.MAX_VALUE // Maximum
    );

    public static int example;

    private static boolean validateItemName(final Object obj) {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
//        example = EXAMPLE_BUILDER.get();

//        // convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream().map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName))).collect(Collectors.toSet());
    }
}
