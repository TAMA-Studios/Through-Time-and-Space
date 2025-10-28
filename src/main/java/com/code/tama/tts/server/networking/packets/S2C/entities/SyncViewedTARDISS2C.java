/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.entities;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.PlayerCapability;
import com.code.tama.tts.server.capabilities.interfaces.IPlayerCap;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record SyncViewedTARDISS2C(String tardis) {

	public static SyncViewedTARDISS2C decode(FriendlyByteBuf buffer) {
		return new SyncViewedTARDISS2C(buffer.readUtf());
	}

	public static void encode(SyncViewedTARDISS2C packet, FriendlyByteBuf buffer) {
		buffer.writeUtf(packet.tardis);
	}

	public static void handle(SyncViewedTARDISS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			Minecraft mc = Minecraft.getInstance();
			IPlayerCap cap = Capabilities.getCap(Capabilities.PLAYER_CAPABILITY, mc.player)
					.orElseGet(() -> new PlayerCapability(mc.player));
			cap.SetViewingTARDIS(packet.tardis);
			if (packet.tardis.isEmpty()) {
				mc.options.setCameraType(CameraType.FIRST_PERSON);
                assert mc.player != null;
                mc.player.setInvisible(false);
			} else {
                assert mc.player != null;
                mc.player.setInvisible(true);
				mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
			}
		});
		context.setPacketHandled(true);
	}
}
