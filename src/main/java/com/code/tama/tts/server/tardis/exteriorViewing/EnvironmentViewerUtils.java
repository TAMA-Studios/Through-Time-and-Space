/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.exteriorViewing;

import java.util.Set;

import com.code.tama.tts.client.ClientSetup;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.PlayerPosition;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncViewedTARDISS2C;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

public class EnvironmentViewerUtils {

	public static void endShellView(ServerPlayer serverPlayer) {
		Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, serverPlayer).ifPresent(playerCap -> {
			ServerLevel tardisLevel = ServerLifecycleHooks.getCurrentServer().getLevel(
					ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(playerCap.GetViewingTARDIS())));
			Capabilities.getCap(Capabilities.TARDIS_LEVEL_CAPABILITY, tardisLevel).ifPresent(tardis -> {
				if (playerCap.GetViewingTARDIS().isEmpty())
					return; // Not viewing a TARDIS
				PlayerPosition targetPosition = tardis.GetData().getViewingPlayerMap().get(serverPlayer.getUUID());

				if (targetPosition == null) { // If target position is for SOME REASON null, tp the player
					// to the TARDIS
					// interior door
					targetPosition = PlayerPosition.builder().levelKey(tardis.GetLevel().dimension())
							.pos(tardis.GetData().getDoorData().getLocation().GetBlockPos()
									.relative(Direction.fromYRot(tardis.GetData().getDoorData().getYRot()), 1)
									.getCenter())
							.build();
				}

				serverPlayer.teleportTo(tardisLevel, targetPosition.pos.x, targetPosition.pos.y, targetPosition.pos.z,
						targetPosition.YRot, targetPosition.Xrot);

				updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), false);
				serverPlayer.onUpdateAbilities();

				tardis.GetData().getViewingPlayerMap().remove(serverPlayer.getUUID());
				serverPlayer.setInvisible(false);
				Networking.sendToPlayer(serverPlayer, new SyncViewedTARDISS2C(""));
				playerCap.SetViewingTARDIS(""); // Isn't viewing a TARDIS anymore
			});
		});
	}

	public static void startSpectateExt(ServerPlayer player, ITARDISLevel tardis, SpaceTimeCoordinate target) {
		if (target != null) {

			if (!tardis.GetData().IsViewingTARDIS(player.getUUID())) {
				tardis.GetData().SetViewing(player.getUUID(),
						PlayerPosition.builder().pos(player.position()).YRot(player.getYHeadRot())
								.Xrot(player.getXRot()).levelKey(tardis.GetLevel().dimension()).build());
			}

			Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, player)
					.ifPresent(cap -> cap.SetViewingTARDIS(tardis.GetLevel().dimension().location().toString()));

			BlockPos spectatePos = target.GetBlockPos();

			if (target.GetBlockPos().distManhattan(
					new Vec3i((int) player.position().x, (int) player.position().y, (int) player.position().z)) > 3
					|| !player.level().dimension().location().toString()
							.equals(target.getLevel().dimension().location().toString())) {
				player.teleportTo(target.getLevel(), spectatePos.getX() + 0.5, spectatePos.getY() + 0.5,
						spectatePos.getZ() + 0.5, Set.of(), 45, 22.5f);
			}
			Networking.sendToPlayer(player,
					new SyncViewedTARDISS2C(tardis.GetLevel().dimension().location().toString()));
			player.setInvisible(true);
			player.sendSystemMessage(Component.translatable("tts.notification.key_to_exit",
					ClientSetup.EXTERIOR_VIEW.get().getKey().getDisplayName()));
			updatePlayerAbilities(player, player.getAbilities(), true);
			player.onUpdateAbilities();
		}
	}

	public static void updatePlayerAbilities(ServerPlayer player, Abilities abilities, boolean spectator) {
		if (spectator) {
			abilities.flying = false;
			abilities.mayfly = false;
			abilities.invulnerable = true;
			player.onUpdateAbilities();
			player.setNoGravity(true);
			player.setDeltaMovement(Vec3.ZERO);
			player.hurtMarked = true; // flush movement to client
		} else {
			player.gameMode.getGameModeForPlayer().updatePlayerAbilities(abilities);
			player.setNoGravity(false);
		}
	}
}
