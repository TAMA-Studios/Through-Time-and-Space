/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.UI.category.PlaceholderCategory;
import com.code.tama.tts.client.UI.category.UICategory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

public class ControlRegistry {
    public static final ResourceKey<Registry<UICategory>> CONTROL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MODID, "controls"));

    public static final DeferredRegister<UICategory> CONTROLS = DeferredRegister.create(CONTROL_REGISTRY_KEY, MODID);

    private static int ID = 0;

    public static final RegistryObject<UICategory> ALL = CONTROLS.register("all", PlaceholderCategory::new);

    /**
     * THIS IS ONLY TO BE USED DURING REGISTRATION OF AN ID DO <i>NOT</i> USE IT! USE {@link
     * ControlRegistry#getMaxID()}}
     */
    public static int getID() {
        return ID++;
    }

    public static int getMaxID() {
        return ID - 1;
    }

    public static void register(IEventBus modEventBus) {
        CONTROLS.makeRegistry(() ->
                new RegistryBuilder<UICategory>().hasTags().disableSaving().disableSync());
        CONTROLS.register(modEventBus);
    }
}
