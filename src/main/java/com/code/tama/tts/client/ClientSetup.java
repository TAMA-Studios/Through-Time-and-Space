/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.renderers.worlds.effects.GallifreyEffects;
import com.code.tama.tts.client.renderers.worlds.effects.TardisSkyEffects;
import com.code.tama.tts.server.worlds.dimension.MDimensions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerSkyRenderers(RegisterDimensionSpecialEffectsEvent event) {
        // Register your custom sky renderer for the TARDIS dimension
        event.register(
                MDimensions.DimensionEffects.TARDIS_DIM_TYPE.location(),
                new TardisSkyEffects(MDimensions.TARDIS_DIM_TYPE));
        event.register(
                MDimensions.DimensionEffects.GALLIFREY_EFFECTS.location(),
                new GallifreyEffects(MDimensions.GALLIFREY_DIM_TYPE));
    }
}
