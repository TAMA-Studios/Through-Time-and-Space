/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.server.misc.sonic.SonicBlockMode;
import com.code.tama.tts.server.misc.sonic.SonicBuilderMode;
import com.code.tama.tts.server.misc.sonic.SonicEntityMode;
import com.code.tama.tts.server.misc.sonic.SonicMode;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public class SonicModeRegistry {
    public static final ResourceKey<Registry<SonicMode>> SONIC_MODE_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MODID, "sonic_mode"));

    public static final DeferredRegister<SonicMode> SONIC_MODE =
            DeferredRegister.create(SONIC_MODE_REGISTRY_KEY, MODID);

    private static int ID = 0;

    public static final RegistryObject<SonicBlockMode> BLOCKS = SONIC_MODE.register("blocks", SonicBlockMode::new);
    public static final RegistryObject<SonicEntityMode> ENTITY = SONIC_MODE.register("entity", SonicEntityMode::new);
    public static final RegistryObject<SonicBuilderMode> BUILDER =
            SONIC_MODE.register("builder", SonicBuilderMode::new);

    public static void register(IEventBus modEventBus) {
        SONIC_MODE.makeRegistry(
                () -> new RegistryBuilder<SonicMode>().hasTags().disableSaving().disableSync());
        SONIC_MODE.register(modEventBus);
    }
}
