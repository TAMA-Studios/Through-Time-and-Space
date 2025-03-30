package com.code.tama.mtm.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.MTMMod.MODID;

public class MTMSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    // Register sound events using a helper method
    public static final RegistryObject<SoundEvent> THROTTLE_ON = registerSound("throttle_on");
    public static final RegistryObject<SoundEvent> THROTTLE_OFF = registerSound("throttle_off");
    public static final RegistryObject<SoundEvent> BUTTON_CLICK_01 = registerSound("button_click_01");
    public static final RegistryObject<SoundEvent> KEYBOARD_PRESS_01 = registerSound("keyboard_press_01");
    public static final RegistryObject<SoundEvent> TARDIS_TAKEOFF = registerSound("tardis_takeoff");
    public static final RegistryObject<SoundEvent> TARDIS_LANDING = registerSound("tardis_landing");
    public static final RegistryObject<SoundEvent> TARDIS_FLIGHT_LOOP = registerSound("tardis_flight_loop");

    private static RegistryObject<SoundEvent> registerSound(String soundName) {
        return SOUNDS.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, soundName)));
    }

    public static void register(IEventBus modEventBus) {
        SOUNDS.register(modEventBus);
    }
}
