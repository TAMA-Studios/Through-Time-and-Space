/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.exterior;

import java.util.function.Supplier;

import com.code.tama.tts.server.misc.containers.PhysicalStateManager;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import lombok.AllArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record ExteriorStatePacket(BlockPos pos,
		com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket.State state, long startTick) {

	public static ExteriorStatePacket decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		State state = buf.readEnum(State.class);
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
			net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
			if (mc.level == null)
				return;
			var be = mc.level.getBlockEntity(msg.pos);
			if (be instanceof ExteriorTile exterior) {
				// Create a client-side PhysicalStateManager
				new StateThread(msg.startTick, exterior, msg.state);
			}
		});
		ctx.get().setPacketHandled(true);
	}

	public enum State {
		LAND, TAKEOFF
	}

	@AllArgsConstructor
	public static class StateThread extends Thread {
		long StartTick;
		ExteriorTile exteriorTile;
		ExteriorStatePacket.State state;

		@Override
		public void run() {
			PhysicalStateManager manager = new PhysicalStateManager(null, exteriorTile);
			if (state == ExteriorStatePacket.State.TAKEOFF) {
				manager.clientTakeOff(StartTick);
			} else {
				manager.clientLand(StartTick);
			}
			super.run();
		}
	}
}
