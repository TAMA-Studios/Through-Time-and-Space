/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.data.json.ARSDataLoader;
import com.code.tama.tts.server.data.json.ExteriorDataLoader;
import com.code.tama.tts.server.data.json.RecipeDataLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

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
        event.level.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(level -> {
            if (level.GetFlightData().isInFlight()
                    || !level.GetLevel().players().isEmpty()) // Only tick if it's in flight or has players in it
            level.Tick();
        });
    }

    @SubscribeEvent
    public static void TARDISDemat(TardisEvent.TakeOff event) {
        switch (event.state) {
            case START: {
                System.out.printf(
                        "Taking off with destination: %s",
                        event.level.GetNavigationalData().getDestination());
                CameraShakeHandler.startShake(
                        event.level.GetFlightData()
                                .getFlightTerminationProtocol()
                                .getTakeoffShakeAmount(),
                        999);
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
                if (event.level.GetData().getControlData().isBrakes())
                    CameraShakeHandler.startShake(
                            event.level.GetFlightData()
                                    .getFlightTerminationProtocol()
                                    .getTakeoffShakeAmount(),
                            9000);
                System.out.printf(
                        "Landing at: %s", event.level.GetNavigationalData().GetExteriorLocation());
                break;
            }
            case END: {
                CameraShakeHandler.endShake();
                if (event.level.GetData().getControlData().isBrakes()) {
                    CameraShakeHandler.startShake(1, 1); // Thud, TODO: Make sure Thud noise werks
                    event.level.GetExteriorTile()
                            .getLevel()
                            .playSound(
                                    null,
                                    event.level.GetExteriorTile().getBlockPos(),
                                    TTSSounds.THUD.get(),
                                    SoundSource.BLOCKS,
                                    1,
                                    1); // Play at exterior
                    event.level.GetLevel()
                            .playSound(
                                    null,
                                    new BlockPos(0, 128, 0),
                                    TTSSounds.THUD.get(),
                                    SoundSource.BLOCKS,
                                    1,
                                    1); // Play at interior
                }
                if (event.level.GetLevel() != null)
                    event.level.GetLevel()
                            .playSound(
                                    null,
                                    event.level.GetNavigationalData()
                                            .GetExteriorLocation()
                                            .GetBlockPos(),
                                    TTSSounds.THUD.get(),
                                    SoundSource.BLOCKS);
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

    @SubscribeEvent
    public static void EntityDie(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
            minecraftServer.getAllLevels().forEach(level -> {
                Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY, level)
                        .ifPresent(iTardisLevel ->
                                iTardisLevel.GetData().getProtocolsData().EP1(player, iTardisLevel));
            });
            //            player.level().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
            //                    .ifPresent(iTardisLevel -> iTardisLevel.HandlePlayerDeath(player));
        }
    }
}
