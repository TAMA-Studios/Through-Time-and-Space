package com.code.tama.mtm.Client;

import com.code.tama.mtm.Client.Renderer.World.Effects.GallifreyEffects;
import com.code.tama.mtm.Client.Renderer.World.Effects.TardisSkyEffects;
import com.code.tama.mtm.World.dimension.MDimensions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.code.tama.mtm.mtm.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerSkyRenderers(RegisterDimensionSpecialEffectsEvent event) {
        // Register your custom sky renderer for the TARDIS dimension
        event.register(MDimensions.TARDIS.location(), new TardisSkyEffects(MDimensions.TARDIS));
//        event.register(MDimensions.GALLIFREY_EFFECTS, new GallifreyEffects());
        event.register(MDimensions.GALLIFREY_DIM_TYPE.location(), new GallifreyEffects());
        }
}
