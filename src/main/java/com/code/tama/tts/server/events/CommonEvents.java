/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import com.code.tama.triggerapi.gui.GuiLoader;
import com.code.tama.triggerapi.helpers.OxygenHelper;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.exceptions.GrammarException;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.data.json.loaders.*;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncViewedTARDISS2C;
import com.code.tama.tts.server.registries.forge.TTSDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;

import static com.code.tama.triggerapi.GrammarNazi.checkAllTranslations;
import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

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
	public static void EntityDie(LivingDeathEvent event) {
		if (event.getEntity() instanceof Player player) {
			MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
			minecraftServer.getAllLevels().forEach(level -> {
				Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY, level)
						.ifPresent(iTardisLevel -> iTardisLevel.GetData().getProtocolsData().EP1(player, iTardisLevel));
			});
			// GetTARDISCapSupplier(player.level)
			// .ifPresent(iTardisLevel -> iTardisLevel.HandlePlayerDeath(player));
		}
	}

	@SubscribeEvent
	public static void EntityLeaveTARDIS(TardisEvent.EntityExitTARDIS event) {
		if (event.entity instanceof Player player) {
			if (player.level().isClientSide)
				CameraShakeHandler.endShake();
		}
	}

	@SubscribeEvent
	public static void TARDISDemat(TardisEvent.TakeOff event) {
		switch (event.state) {
			case START : {
				System.out.printf("Taking off with destination: %s",
						event.level.GetNavigationalData().getDestination());
				CameraShakeHandler.startShake(
						event.level.GetFlightData().getFlightTerminationProtocol().getTakeoffShakeAmount(), 999);
				break;
			}
			case END : {
				CameraShakeHandler.endShake();
				System.out.println("Finished Taking off, now in flight");
				break;
			}
		}
	}

	@SubscribeEvent
	public static void TARDISRemat(TardisEvent.Land event) {
		switch (event.state) {
			case START : {
				if (event.level.GetData().getControlData().isBrakes())
					CameraShakeHandler.startShake(
							event.level.GetFlightData().getFlightTerminationProtocol().getTakeoffShakeAmount(), 9000);
				System.out.printf("Landing at: %s", event.level.GetNavigationalData().GetExteriorLocation());
				break;
			}
			case END : {
				CameraShakeHandler.endShake();
				if (event.level.GetData().getControlData().isBrakes()) {
					CameraShakeHandler.startShake(1, 1); // Thud, TODO: Make sure Thud noise werks
					event.level.GetExteriorTile().getLevel().playLocalSound(event.level.GetExteriorTile().getBlockPos(),
							TTSSounds.THUD.get(), SoundSource.BLOCKS, 1, 1, true); // Play at exterior
					event.level.GetLevel().playSound(null, new BlockPos(0, 128, 0), TTSSounds.THUD.get(),
							SoundSource.BLOCKS, 1, 1); // Play at interior
				}
				if (event.level.GetLevel() != null)
					event.level.GetLevel().playSound(null,
							event.level.GetNavigationalData().GetExteriorLocation().GetBlockPos(), TTSSounds.THUD.get(),
							SoundSource.BLOCKS);
				System.out.println("Finished Landing");
				break;
			}
		}
	}

	@SubscribeEvent
	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(new GuiLoader());
		event.addListener(new ExteriorDataLoader());
		event.addListener(new ARSDataLoader());
		event.addListener(new DataFlightEventLoader());
		event.addListener(new DataDimGravityLoader());
		event.addListener(new RecipeDataLoader());
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.LevelTickEvent event) {
		if (event.phase != TickEvent.Phase.END)
			return;

		GetTARDISCapSupplier(event.level).ifPresent(level -> {
			if (level.GetFlightData().isInFlight() || level.GetFlightData().IsTakingOff()
					|| !level.GetLevel().players().isEmpty()) // Only tick if it's in flight or has players in it
				level.Tick();
		});

		if (event.level.isClientSide)
			return;
		if (event.level.getServer() == null)
			return;
		if (event.level.getServer().getLevel(event.level.dimension()) == null)
			return;

		event.level.getServer().getLevel(event.level.dimension()).getAllEntities().forEach((entity -> {

			if (entity instanceof LivingEntity livingEntity) {
				// TODO: REAL Oxygen implementation
				float O2 = OxygenHelper.getO2(event.level) * 10;

				if (O2 != 10 && event.level.getGameTime() % O2 == 0) {
					entity.hurt(new DamageSource(Holder.direct(TTSDamageSources.SUFFOCATION)), 1);
				}
			}
		}));
	}

	@SubscribeEvent
	public static void PlayerJoin(EntityJoinLevelEvent event) throws GrammarException {
		if (event.getEntity() instanceof ServerPlayer player) {
			player.getCapability(Capabilities.PLAYER_CAPABILITY)
					.ifPresent(cap -> Networking.sendToPlayer(player, new SyncViewedTARDISS2C(cap.GetViewingTARDIS())));

			// event.getLevel().getCapability(Capabilities.LEVEL_CAPABILITY).ifPresent(cap
			// -> cap.OnLoad(player));
		}

		if (!FMLEnvironment.production && false) { // Recent datagenney BS has slightly maken this go cattywonkers so
													// I've disabled its sorry ass
			checkAllTranslations();
		}
	}
}
