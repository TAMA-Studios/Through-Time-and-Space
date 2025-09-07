/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.json.ARSDataLoader;
import com.code.tama.tts.server.data.json.ExteriorDataLoader;
import com.code.tama.tts.server.data.json.RecipeDataLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        TTSMod.LOGGER.info(
                "Loaded namespaces: {}",
                Minecraft.getInstance().getResourceManager().getNamespaces());
        event.addListener(new ExteriorDataLoader());
        event.addListener(new ARSDataLoader());
        event.addListener(new RecipeDataLoader());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        event.level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(ITARDISLevel::Tick);
    }

    @SubscribeEvent
    public static void TARDISDemat(TardisEvent.TakeOff event) {
        switch (event.state) {
            case START: {
                System.out.printf("Taking off with destination: %s", event.level.GetDestination());
                CameraShakeHandler.startShake(
                        event.level.GetFlightTerminationPolicy().GetTakeoffShakeAmount(), 9000);
                break;
            }
            case END: {
                CameraShakeHandler.endShake();
                System.out.println("Finished Taking off, now in flight");
                break;
            }
        }
    }

    @SubscribeEvent
    public static void TARDISRemat(TardisEvent.Land event) {
        switch (event.state) {
            case START: {
                CameraShakeHandler.startShake(
                        event.level.GetFlightTerminationPolicy().GetTakeoffShakeAmount(), 9000);
                System.out.printf("Landing at: %s", event.level.GetExteriorLocation());
                break;
            }
            case END: {
                CameraShakeHandler.endShake();
                CameraShakeHandler.startShake(1, 1); // Thud, TODO: Thud noise
                System.out.println("Finished Landing");
                break;
            }
        }
    }

    @SubscribeEvent
    public static void EntityLeaveTARDIS(TardisEvent.EntityExitTARDIS event) {
        if (event.entity instanceof Player player) {
            if (player.level().isClientSide) CameraShakeHandler.endShake();
        }
    }
}
