/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.client.UI.component.all.UIComponentNext;
import com.code.tama.tts.client.UI.component.all.UIComponentPower;
import com.code.tama.tts.client.UI.component.all.UIComponentPrevious;
import com.code.tama.tts.client.UI.component.core.ComponentTypes;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.client.UI.component.destination.UIComponentXCoord;
import com.code.tama.tts.client.UI.component.destination.UIComponentYCoord;
import com.code.tama.tts.client.UI.component.destination.UIComponentZCoord;
import com.code.tama.tts.client.UI.component.int_props.UIComponentLightDown;
import com.code.tama.tts.client.UI.component.int_props.UIComponentLightUp;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.tts.TTSMod.MODID;

public class UIComponentRegistry {
    public static final ResourceKey<Registry<UIComponent>> UI_COMPONENT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MODID, "ui_components"));

    public static final DeferredRegister<UIComponent> UI_COMPONENTS =
            DeferredRegister.create(UI_COMPONENT_REGISTRY_KEY, MODID);

    public static final RegistryObject<UIComponent> LIGHT_DOWN = UI_COMPONENTS.register(
            "light_down",
            () -> new UIComponentLightDown(new Float[] {1f, 2f}, new Float[] {9f, 10f}, ComponentTypes.BUTTON));

    public static final RegistryObject<UIComponent> LIGHT_UP = UI_COMPONENTS.register(
            "light_up",
            () -> new UIComponentLightUp(new Float[] {1f, 2f}, new Float[] {7f, 8f}, ComponentTypes.BUTTON));

    public static final RegistryObject<UIComponent> NEXT = UI_COMPONENTS.register(
            "next", () -> new UIComponentNext(new Float[] {14f, 15f}, new Float[] {14f, 15f}, ComponentTypes.BUTTON));

    public static final RegistryObject<UIComponent> POWER = UI_COMPONENTS.register(
            "power",
            () -> new UIComponentPower(new Float[] {14.5f, 15.5f}, new Float[] {0.5f, 1.5f}, ComponentTypes.BUTTON));
    public static final RegistryObject<UIComponent> PREVIOUS = UI_COMPONENTS.register(
            "previous",
            () -> new UIComponentPrevious(new Float[] {1f, 2f}, new Float[] {14f, 15f}, ComponentTypes.BUTTON));
    public static final RegistryObject<UIComponent> X_COORD = UI_COMPONENTS.register(
            "x_coord", () -> new UIComponentXCoord(new Float[] {1f, 2f}, new Float[] {7f, 8f}, ComponentTypes.BUTTON));
    public static final RegistryObject<UIComponent> Y_COORD = UI_COMPONENTS.register(
            "y_coord", () -> new UIComponentYCoord(new Float[] {5f, 6f}, new Float[] {7f, 8f}, ComponentTypes.BUTTON));
    public static final RegistryObject<UIComponent> Z_COORD = UI_COMPONENTS.register(
            "z_coord", () -> new UIComponentZCoord(new Float[] {9f, 10f}, new Float[] {7f, 8f}, ComponentTypes.BUTTON));

    public static void register(IEventBus modEventBus) {
        UI_COMPONENTS.makeRegistry(() ->
                new RegistryBuilder<UIComponent>().hasTags().disableSaving().disableSync());
        UI_COMPONENTS.register(modEventBus);
    }
}
