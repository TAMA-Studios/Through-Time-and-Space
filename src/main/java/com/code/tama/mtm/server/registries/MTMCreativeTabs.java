package com.code.tama.mtm.server.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;
import static com.code.tama.mtm.server.registries.MTMItems.*;

public class MTMCreativeTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() ->
            SMITH_EXTERIOR.get().getDefaultInstance()).displayItems((parameters, output) ->
            ITEMS.getEntries().forEach((reg) -> output.accept(reg.get()))).title(Component.translatable("mtm.tab.main")).build());

    public static final RegistryObject<CreativeModeTab> DIMENSIONAL_TAB = CREATIVE_MODE_TABS.register("dimensional_tab", () -> CreativeModeTab.builder().withTabsBefore(MTMCreativeTabs.MAIN_TAB.getId()).icon(() ->
            SMITH_EXTERIOR.get().getDefaultInstance()).displayItems((parameters, output) ->
            DIMENSIONAL_ITEMS.getEntries().forEach((reg) -> output.accept(reg.get()))).title(Component.translatable("mtm.tab.dimensional")).build());
}
