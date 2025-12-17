/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.exterior;

import com.code.tama.tts.server.misc.PhysicalStateManager;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import lombok.AllArgsConstructor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ExteriorStatePacket(BlockPos pos, ExteriorState state, long startTick) {

	public static ExteriorStatePacket decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		ExteriorState state = buf.readEnum(ExteriorState.class);
		long tick = buf.readLong();
		return new ExteriorStatePacket(pos, state, tick);
	}

	public static void encode(ExteriorStatePacket msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.pos);
		buf.writeEnum(msg.state);
		buf.writeLong(msg.startTick);
	}

	public static void handle(ExteriorStatePacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Minecraft mc = net.minecraft.client.Minecraft.getInstance();
			if (mc.level == null)
				return;
			var be = mc.level.getBlockEntity(msg.pos);
			if (be instanceof ExteriorTile exterior) {
				exterior.state = msg.state;
				// Create a client-side PhysicalStateManager
				new StateThread(msg.startTick, exterior, msg.state).start();
			}
		});
		ctx.get().setPacketHandled(true);
	}

	@AllArgsConstructor
	public static class StateThread extends Thread {
		long StartTick;
		ExteriorTile exteriorTile;
		ExteriorState state;

		@Override
		public void run() {
			if (state == ExteriorState.TAKINGOFF) {
				PhysicalStateManager manager = new PhysicalStateManager(exteriorTile);
				manager.clientTakeOff(StartTick);
			} if(state == ExteriorState.LANDING) {
				PhysicalStateManager manager = new PhysicalStateManager(exteriorTile);
				manager.clientLand(StartTick);
			}
			super.run();
		}
	}
}
