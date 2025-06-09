/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.client.UI.category.PlaceholderCategory;
import com.code.tama.tts.client.UI.category.UICategory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.tts.TTSMod.MODID;

public class ControlRegistry {
    public static final ResourceKey<Registry<UICategory>> UI_CATEGORY_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MODID, "controls"));

    public static final DeferredRegister<UICategory> UI_CATEGORIES =
            DeferredRegister.create(UI_CATEGORY_REGISTRY_KEY, MODID);

    public static final RegistryObject<UICategory> ALL = UI_CATEGORIES.register("all", PlaceholderCategory::new);

    public static void register(IEventBus modEventBus) {
        UI_CATEGORIES.makeRegistry(() -> new RegistryBuilder<UICategory>()
                .hasTags()
                .disableSaving()
                .disableSync()
        );
        UI_CATEGORIES.register(modEventBus);
    }

    private static int ID = 0;

    /** THIS IS ONLY TO BE USED DURING REGISTRATION OF AN ID DO <i>NOT</i> USE IT! USE {@link ControlRegistry#getMaxID()}}**/
    public static int getID() {
        return ID++;
    }
    public static int getMaxID() {
        return ID - 1;
    }
}