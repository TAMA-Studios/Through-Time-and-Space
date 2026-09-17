/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.networking.packets.S2C;

import java.util.function.Supplier;

import com.code.tama.tts.server.tardis.flightsoundschemes.FlightLoopSoundManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Server -> client. Tells whichever clients are present in a TARDIS interior to
 * start or stop the flight loop ambient sound.
 */
public record FlightLoopSoundPacketS2C(Boolean start) implements ImAPacket {
	public static void encode(FlightLoopSoundPacketS2C packet, FriendlyByteBuf buf) {
		buf.writeBoolean(packet.start);
	}

	public static FlightLoopSoundPacketS2C decode(FriendlyByteBuf buffer) {
		return new FlightLoopSoundPacketS2C(buffer.readBoolean());
	}

	public static void handle(FlightLoopSoundPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handle(packet)));
		context.setPacketHandled(true);
	}

	@OnlyIn(Dist.CLIENT)
	private static class ClientHandler {
		private static void handle(FlightLoopSoundPacketS2C packet) {
			var level = net.minecraft.client.Minecraft.getInstance().level;
			if (level == null)
				return;

			com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier(level)
					.ifPresent(cap -> {
						if (packet.start()) {
							var loop = cap.GetFlightData().getFlightSoundScheme().GetFlightLoop();
							FlightLoopSoundManager.start(loop.GetSound());
						} else {
							FlightLoopSoundManager.stop();
						}
					});
		}
	}
}