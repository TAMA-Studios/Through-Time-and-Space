/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.entities;

import com.code.tama.tts.server.tardis.exteriorViewing.EnvironmentViewerUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Stops viewing the exterior */
public class StopViewingExteriorC2S {

	public UUID player;

	public StopViewingExteriorC2S(UUID player) {
		this.player = player;
	}

	public static StopViewingExteriorC2S decode(FriendlyByteBuf buf) {
		return new StopViewingExteriorC2S(buf.readUUID());
	}

	public static void encode(StopViewingExteriorC2S mes, FriendlyByteBuf buf) {
		buf.writeUUID(mes.player);
	}

	public static void handle(StopViewingExteriorC2S mes, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayer player1 = (ServerPlayer) context.get().getSender().level().getServer()
					.getLevel(context.get().getSender().level().dimension()).getEntity(mes.player);
			EnvironmentViewerUtils.endShellView(player1);
		});
		context.get().setPacketHandled(true);
	}
}
