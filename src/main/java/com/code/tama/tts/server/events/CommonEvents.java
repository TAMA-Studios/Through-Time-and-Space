/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.json.ARSDataLoader;
import com.code.tama.tts.server.data.json.ExteriorDataLoader;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.code.tama.tts.TTSMod.MODID;

@Mod.EventBusSubscriber(modid = MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void Chat(ServerChatEvent event) {
        // if (!event.getPlayer().level().isClientSide)
        // DimensionAPI.get().getOrCreateLevel(event.getPlayer().level().getServer(),
        // ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID,
        // "tardis_" + UUID.randomUUID())), () ->
        // DimensionManager.CreateTARDISLevelStem(event.getPlayer().level().getServer()));//DimensionManager.createLevel(p_60567_.getServer()));
        // if(event.getPlayer().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).isPresent())
        // {
        // event.getPlayer().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElseGet(null).SetLightLevel((float)
        // Integer.parseInt(event.getRawText()) / 10);
        // Networking.sendPacketToDimension(event.getPlayer().level().dimension(), new
        // SyncCapLightLevelPacket((float) Integer.parseInt(event.getRawText()) / 10));
        // }
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ExteriorDataLoader());
        event.addListener(new ARSDataLoader());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        event.level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::Tick);
    }
}
