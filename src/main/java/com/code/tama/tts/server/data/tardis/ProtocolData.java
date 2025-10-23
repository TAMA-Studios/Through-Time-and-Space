/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProtocolData {
	public static Codec<ProtocolData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(SpaceTimeCoordinate.CODEC.fieldOf("ep1_destination").forGetter(ProtocolData::getEP1Destination))
			.apply(instance, ProtocolData::new));

	SpaceTimeCoordinate EP1Destination = new SpaceTimeCoordinate();

	public void EP1(Player player, ITARDISLevel tardis) {
		if (tardis.GetData().getOwnerUUID().equals(player.getUUID())) {
			ServerLevel tardisLevel = ServerLifecycleHooks.getCurrentServer().getLevel(tardis.GetLevel().dimension());
			if (tardisLevel == null)
				return;
			tardisLevel.getEntities().getAll().forEach(ent -> {
				if (ent instanceof ServerPlayer serverPlayer) {
					serverPlayer.sendSystemMessage(Component.translatable(
							"tts.protocol.emergency_protocol_one.activated", player.getName(), player.getName()));
				}
			});

			if (tardis.GetExteriorTile() != null) {
				tardis.GetData().getControlData().setHelmicRegulator(0.6f);
				tardis.GetData().getControlData().setSimpleMode(true);
				tardis.GetNavigationalData().setDestination(
						new SpaceTimeCoordinate(tardis.GetExteriorTile().getLevel().getSharedSpawnPos()));
			}

			tardis.Dematerialize();
		}
	}
}
