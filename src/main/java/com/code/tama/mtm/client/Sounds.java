package com.code.tama.mtm.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class Sounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    // Register a custom sound event
    public static final RegistryObject<SoundEvent> THROTTLE_ON = SOUNDS.register("throttle_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "throttle_on")));
    public static final RegistryObject<SoundEvent> THROTTLE_OFF = SOUNDS.register("throttle_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "throttle_off")));
    public static final RegistryObject<SoundEvent> BUTTON_CLICK_01 = SOUNDS.register("button_click_01", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "button_click_01")));
    public static final RegistryObject<SoundEvent> KEYBOARD_PRESS_01 = SOUNDS.register("keyboard_press_01", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "keyboard_press_01")));
    public static final RegistryObject<SoundEvent> TARDIS_TAKEOFF = SOUNDS.register("tardis_takeoff", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "tardis_takeoff")));
    public static final RegistryObject<SoundEvent> TARDIS_LANDING = SOUNDS.register("tardis_landing", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "tardis_landing")));
    public static final RegistryObject<SoundEvent> TARDIS_FLIGHT_LOOP = SOUNDS.register("tardis_flight_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "tardis_flight_loop")));

    public static void register(IEventBus modEventBus) {
        SOUNDS.register(modEventBus);
    }
}
