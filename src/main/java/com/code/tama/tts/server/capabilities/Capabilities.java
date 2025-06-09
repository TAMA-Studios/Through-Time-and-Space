/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.capabilities.providers.SerializableLevelCapabilityProvider;
import com.code.tama.tts.server.worlds.dimension.MDimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.code.tama.tts.TTSMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Capabilities {

    public static final ResourceLocation TARDIS_LEVEL_KEY = new ResourceLocation(MODID, "tardis");

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(ITARDISLevel.class);
    }

    public static <T, O extends ICapabilityProvider> LazyOptional<T> getCap(Capability<T> cap, O object) {
        return object == null ? LazyOptional.empty() : object.getCapability(cap);
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class AttachCapabilities {
        @SubscribeEvent
        public static void AttachWorldCapabilities(AttachCapabilitiesEvent<Level> event) {

            // For unregistered worlds, because some mods either aren't that bright or just as hacky and gimmicky as a nintendo console
            if (event.getObject().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).getKey(event.getObject().dimensionType()) == null)
                return;

            if (!event.getObject().dimensionTypeId().location().equals(MDimensions.TARDIS_DIM_TYPE.location())) return;

            event.addCapability(Capabilities.TARDIS_LEVEL_KEY, new SerializableLevelCapabilityProvider<>(CapabilityConstants.TARDIS_LEVEL_CAPABILITY, new TARDISLevelCapability(event.getObject())));
        }
    }
}

