/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import static com.code.tama.tts.TTSMod.MODID;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.Objects;

import com.code.tama.tts.client.util.CameraShakeHandler;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.entities.StopViewingExteriorC2S;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
	@SubscribeEvent
	public static void PlayerJoin(EntityJoinLevelEvent event) {
		GetTARDISCapSupplier(event.getLevel()).ifPresent(cap -> cap.UpdateClient(DataUpdateValues.ALL));

		CameraShakeHandler.endShake();
	}

	@SubscribeEvent
	public static void Render(RenderLivingEvent<Player, PlayerModel<Player>> event) {
		if (event.getEntity() instanceof Player player) {
			player.getCapability(Capabilities.PLAYER_CAPABILITY).ifPresent(cap -> {
				if (cap.GetViewingTARDIS().isEmpty())
					return;
				else {
					event.getRenderer().getModel().body.visible = false;
					event.getRenderer().getModel().jacket.visible = false;
					event.getRenderer().getModel().head.visible = false;
					event.getRenderer().getModel().hat.visible = false;
					event.getRenderer().getModel().leftArm.visible = false;
					event.getRenderer().getModel().leftSleeve.visible = false;
					event.getRenderer().getModel().leftLeg.visible = false;
					event.getRenderer().getModel().leftPants.visible = false;
					event.getRenderer().getModel().rightArm.visible = false;
					event.getRenderer().getModel().rightSleeve.visible = false;
					event.getRenderer().getModel().rightLeg.visible = false;
					event.getRenderer().getModel().rightPants.visible = false;
				}
			});
		}
	}

	@SubscribeEvent
	public static void onInputUpdate(MovementInputUpdateEvent event) {
		Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, event.getEntity()).ifPresent(cap -> {
			if (!cap.GetViewingTARDIS().isEmpty()) {
				event.getInput().forwardImpulse = 0;
				event.getInput().leftImpulse = 0;
				event.getInput().up = false;
				event.getInput().down = false;
				event.getInput().jumping = false;
			}
		});
	}

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.player.level().isClientSide) {
			event.player.getCapability(Capabilities.PLAYER_CAPABILITY).ifPresent(cap -> {
				while (ClientSetup.EXTERIOR_VIEW.get().consumeClick()) {
					cap.SetViewingTARDIS("");
					Networking.sendToServer(new StopViewingExteriorC2S(event.player.getUUID()));
				}
			});
		}
	}

	@SubscribeEvent
	public static void onRenderGameOverlay(RenderGuiOverlayEvent.Pre event) {
		// Hide specific overlays
		Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, Minecraft.getInstance().player).ifPresent(cap -> {
			if (!cap.GetViewingTARDIS().isEmpty()) {
				event.setCanceled(true);
			}
		});
	}

	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		// Hide specific overlays
		Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, Minecraft.getInstance().player).ifPresent(cap -> {
			if (!cap.GetViewingTARDIS().isEmpty())
				event.setCanceled(true);
		});
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		Player player = event.getPlayer();
		Level level = (Level) event.getLevel();

		player.getCapability(Capabilities.PLAYER_CAPABILITY).ifPresent(cap -> {
			if (!Objects.equals(cap.GetViewingTARDIS(), ""))
				event.setCanceled(true);
		});
	}

	@SubscribeEvent
	public void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		if (event.getEntity() instanceof Player player) {
			Level level = (Level) event.getLevel();

			player.getCapability(Capabilities.PLAYER_CAPABILITY).ifPresent(cap -> {
				if (!Objects.equals(cap.GetViewingTARDIS(), ""))
					event.setCanceled(true);
			});
		}
	}
}
