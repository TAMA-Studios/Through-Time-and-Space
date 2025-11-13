package com.code.tama.tts.server.registries;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

public class TTSRegistrate extends AbstractRegistrate<TTSRegistrate> {
    private static final Map<RegistryEntry<?>, RegistryObject<CreativeModeTab>> TAB_LOOKUP = Collections.synchronizedMap(new IdentityHashMap<>());

    @Nullable
    protected Function<Item, TooltipFlag> currentTooltipModifierFactory;

    @Nullable
    protected RegistryObject<CreativeModeTab> currentTab;

    protected TTSRegistrate(String modid) {
        super(modid);
    }

    public static TTSRegistrate create(String modid) {
        TTSRegistrate registrate = new TTSRegistrate(modid);
        // The registrate is registered here instead of in the constructor so that if a subclass
        // overrides the addRegisterCallback to be dependent on some sort of state initialized in the constructor,
        // it won't explode. The consequence is that subclasses must manually provide their registrate to the callback API
        TTSRegistrateRegistrationCallback.provideRegistrate(registrate);
        return registrate;
    }
}