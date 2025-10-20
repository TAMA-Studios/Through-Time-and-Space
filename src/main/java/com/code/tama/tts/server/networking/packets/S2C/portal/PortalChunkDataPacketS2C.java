/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.BotiChunkContainer;
import com.code.tama.tts.server.tileentities.AbstractPortalTile;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class PortalChunkDataPacketS2C {
	private final BlockPos portalPos;

	int index;
	int totalPackets;
	public List<BotiChunkContainer> containersL = new ArrayList<>();

	public PortalChunkDataPacketS2C(BlockPos portalPos, List<BotiChunkContainer> containers, int index,
			int totalPackets) {
		this.portalPos = portalPos;
		this.containersL = containers;
		this.totalPackets = totalPackets;
		this.index = index;
	}

	@OnlyIn(Dist.CLIENT)
	public static Supplier<Runnable> Data(PortalChunkDataPacketS2C msg) {
		return () -> () -> {
			Level level = Minecraft.getInstance().level;
			if (level == null)
				return;

			if (level.getBlockEntity(msg.portalPos) instanceof AbstractPortalTile portal)
				portal.updateChunkDataFromServer(msg.containersL, msg.index, msg.totalPackets);
			else
				TTSMod.LOGGER.warn("No portal holder at {}", msg.portalPos);
		};
	}

	public static PortalChunkDataPacketS2C decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		List<BotiChunkContainer> data = BotiChunkContainer.decodeList(buf);
		return new PortalChunkDataPacketS2C(pos, data, buf.readInt(), buf.readInt());
	}

	public static void encode(PortalChunkDataPacketS2C msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.portalPos);
		BotiChunkContainer.encodeList(msg.containersL, buf);
		buf.writeInt(msg.index);
		buf.writeInt(msg.totalPackets);
	}

	public static void handle(PortalChunkDataPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, PortalChunkDataPacketS2C.Data(msg)));
		ctx.get().setPacketHandled(true);
	}
}
