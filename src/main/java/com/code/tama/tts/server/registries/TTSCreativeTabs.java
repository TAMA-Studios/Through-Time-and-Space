/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.registries.TTSItems.*;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TTSCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> DIMENSIONAL_TAB =
            CREATIVE_MODE_TABS.register("dimensional_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(TTSCreativeTabs.MAIN_TAB.getId())
                    .icon(() -> EXTERIOR.get().getDefaultInstance())
                    .displayItems((parameters, output) ->
                            DIMENSIONAL_ITEMS.getEntries().forEach((reg) -> output.accept(reg.get())))
                    .title(Component.translatable("tts.tab.dimensional"))
                    .build());

    public static final RegistryObject<CreativeModeTab> MAIN_TAB =
            CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> EXTERIOR.get().getDefaultInstance())
                    .displayItems((parameters, output) -> ITEMS.getEntries().forEach((reg) -> output.accept(reg.get())))
                    .title(Component.translatable("tts.tab.main"))
                    .build());
}
