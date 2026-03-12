/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets.S2C;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;
import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Carries a batch of pre-gathered destination geometry for a seamless teleport.
 * Unlike {@link PortalChunkDataPacketS2C} (which targets a portal tile entity
 * for BOTI rendering), this packet targets
 * {@link ClientSeamlessTeleportState}'s staging buffer.
 *
 * Multiple packets are sent (one per batch of <= maxBlocks containers). Each
 * packet carries: - the teleport UUID (so stale packets from a prior teleport
 * are dropped) - index / total (so the client knows when all batches have
 * arrived) - the container batch
 *
 * The server sends these packets BEFORE calling changeDimension, so by the time
 * the ClientboundRespawnPacket arrives the geometry is already staged.
 */
public class SeamlessChunkPreloadPacketS2C implements ImAPacket {

	private final UUID teleportId;
	private final int index;
	private final int total;
	private final List<BotiBlockContainer> containers;

	public SeamlessChunkPreloadPacketS2C(UUID teleportId, List<BotiBlockContainer> containers, int index, int total) {
		this.teleportId = teleportId;
		this.index = index;
		this.total = total;
		this.containers = containers;
	}

	// -------------------------------------------------------------------------
	// Codec
	// -------------------------------------------------------------------------

	public static void encode(SeamlessChunkPreloadPacketS2C msg, FriendlyByteBuf buf) {
		buf.writeLong(msg.teleportId.getMostSignificantBits());
		buf.writeLong(msg.teleportId.getLeastSignificantBits());
		buf.writeInt(msg.index);
		buf.writeInt(msg.total);
		BotiBlockContainer.encodeList(msg.containers, buf);
	}

	public static SeamlessChunkPreloadPacketS2C decode(FriendlyByteBuf buf) {
		UUID teleportId = new UUID(buf.readLong(), buf.readLong());
		int index = buf.readInt();
		int total = buf.readInt();
		List<BotiBlockContainer> containers = BotiBlockContainer.decodeList(buf);
		return new SeamlessChunkPreloadPacketS2C(teleportId, containers, index, total);
	}

	// -------------------------------------------------------------------------
	// Handler
	// -------------------------------------------------------------------------

	public static void handle(SeamlessChunkPreloadPacketS2C msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		// enqueueWork runs on the main client thread; but stageContainers is also
		// safe to call from the Netty thread (it is synchronized internally).
		// We use enqueueWork so the completed-staging check and the respawn flush
		// both happen on the same thread, eliminating any ordering races.
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientSeamlessTeleportState
				.stageContainers(msg.teleportId, msg.containers, msg.index, msg.total)));
		ctx.setPacketHandled(true);
	}
}