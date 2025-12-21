/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.entities;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCap;

import java.util.UUID;
import java.util.function.Supplier;

import com.code.tama.tts.server.entities.controls.ModularControl;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.universal.UniversalServerOnly;

public class ControlClickedPacketC2S {

	public UUID control;

	public ControlClickedPacketC2S(UUID control) {
		this.control = control;
	}

	public static ControlClickedPacketC2S decode(FriendlyByteBuf buf) {
		return new ControlClickedPacketC2S(buf.readUUID());
	}

	public static void encode(ControlClickedPacketC2S mes, FriendlyByteBuf buf) {
		buf.writeUUID(mes.control);
	}

	public static void handle(ControlClickedPacketC2S mes, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ModularControl control = (ModularControl) UniversalServerOnly.getServer()
					.getLevel(context.get().getSender().level().dimension()).getEntity(mes.control);
			assert control != null;
			control.OnControlClicked(GetTARDISCap(context.get().getSender().level()), context.get().getSender());
		});
		context.get().setPacketHandled(true);
	}
}
