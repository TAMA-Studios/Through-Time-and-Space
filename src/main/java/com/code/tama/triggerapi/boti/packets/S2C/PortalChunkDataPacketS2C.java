/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.packets.S2C;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.code.tama.tts.TTSMod;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.client.OccupancyGrid;

public class PortalChunkDataPacketS2C {
	private final BlockPos portalPos;

	int index;
	int totalPackets;
	public List<BotiBlockContainer> containersL = new ArrayList<>();

	/**
	 * Full pre-culling occupancy for AO purposes. Only non-null on ONE packet
	 * per gather (index 0) to avoid re-sending the same volume data once per
	 * batch -- see ChunkGatheringThread, which only attaches it to the first
	 * packet it sends.
	 */
	@Nullable
	private final OccupancyGrid occupancyGrid;

	public PortalChunkDataPacketS2C(BlockPos portalPos, List<BotiBlockContainer> containers, int index,
	                                int totalPackets) {
		this(portalPos, containers, index, totalPackets, null);
	}

	public PortalChunkDataPacketS2C(BlockPos portalPos, List<BotiBlockContainer> containers, int index,
	                                int totalPackets, @Nullable OccupancyGrid occupancyGrid) {
		this.portalPos = portalPos;
		this.containersL = containers;
		this.totalPackets = totalPackets;
		this.index = index;
		this.occupancyGrid = occupancyGrid;
	}

	@OnlyIn(Dist.CLIENT)
	public static Supplier<Runnable> Data(PortalChunkDataPacketS2C msg) {
		return () -> () -> {
			Level level = Minecraft.getInstance().level;
			if (level == null)
				return;

			if (level.getBlockEntity(msg.portalPos) instanceof AbstractPortalTile portal) {
				if (msg.occupancyGrid != null)
					BOTIUtils.occupancyGrids.put(portal, msg.occupancyGrid);

				portal.updateChunkDataFromServer(msg.containersL, msg.index, msg.totalPackets);
			} else
				TTSMod.LOGGER.warn("No portal holder at {}", msg.portalPos);
		};
	}

	public static PortalChunkDataPacketS2C decode(FriendlyByteBuf buf) {
		BlockPos pos = buf.readBlockPos();
		List<BotiBlockContainer> data = BotiBlockContainer.decodeList(buf);
		int index = buf.readInt();
		int totalPackets = buf.readInt();

		OccupancyGrid grid = null;
		if (buf.readBoolean()) {
			byte[] solidBytes = buf.readByteArray();
			int sizeX = buf.readInt();
			int sizeY = buf.readInt();
			int sizeZ = buf.readInt();
			int originX = buf.readInt();
			int originY = buf.readInt();
			int originZ = buf.readInt();
			grid = OccupancyGrid.fromBytes(solidBytes, sizeX, sizeY, sizeZ, originX, originY, originZ);
		}

		return new PortalChunkDataPacketS2C(pos, data, index, totalPackets, grid);
	}

	public static void encode(PortalChunkDataPacketS2C msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.portalPos);
		BotiBlockContainer.encodeList(msg.containersL, buf);
		buf.writeInt(msg.index);
		buf.writeInt(msg.totalPackets);

		boolean hasOccupancy = msg.occupancyGrid != null;
		buf.writeBoolean(hasOccupancy);
		if (hasOccupancy) {
			OccupancyGrid grid = msg.occupancyGrid;
			buf.writeByteArray(grid.getSolidBytes());
			buf.writeInt(grid.getSizeX());
			buf.writeInt(grid.getSizeY());
			buf.writeInt(grid.getSizeZ());
			buf.writeInt(grid.getOriginX());
			buf.writeInt(grid.getOriginY());
			buf.writeInt(grid.getOriginZ());
		}
	}

	public static void handle(PortalChunkDataPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, PortalChunkDataPacketS2C.Data(msg)));
		ctx.get().setPacketHandled(true);
	}
}