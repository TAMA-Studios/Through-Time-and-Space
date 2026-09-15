/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.core.blocks.Panels.ChameleonCircuitActions;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ChameleonCircuitActionC2SPacket {

	public enum Action {
		NEXT_VARIANT, PREV_VARIANT, NEXT_GROUP, PREV_GROUP, NEXT_COLLECTION
	}

	private final Action action;

	public ChameleonCircuitActionC2SPacket(Action action) {
		this.action = action;
	}

	public static ChameleonCircuitActionC2SPacket decode(FriendlyByteBuf buf) {
		return new ChameleonCircuitActionC2SPacket(buf.readEnum(Action.class));
	}

	public static void encode(ChameleonCircuitActionC2SPacket packet, FriendlyByteBuf buf) {
		buf.writeEnum(packet.action);
	}

	public static void handle(ChameleonCircuitActionC2SPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> {
			ServerPlayer sender = ctx.getSender();
			if (sender == null)
				return;

			TARDISLevelCapability.GetTARDISCapSupplier(sender.level()).ifPresent(cap -> {
				switch (packet.action) {
					case NEXT_VARIANT -> ChameleonCircuitActions.nextVariant(sender.level(), cap);
					case PREV_VARIANT -> ChameleonCircuitActions.prevVariant(sender.level(), cap);
					case NEXT_GROUP -> ChameleonCircuitActions.nextGroup(sender.level(), cap);
					case PREV_GROUP -> ChameleonCircuitActions.prevGroup(sender.level(), cap);
					case NEXT_COLLECTION -> ChameleonCircuitActions.nextCollection(sender.level(), cap);
				}
			});
		});
		ctx.setPacketHandled(true);
	}
}