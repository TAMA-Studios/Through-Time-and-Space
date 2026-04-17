/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.packets.S2C;

import java.util.function.Supplier;

import com.code.tama.triggerapi.boti.FakePortalLevelRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Sent by the server immediately before a stream of vanilla
 * {@link net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket}s
 * for a portal's target dimension.
 *
 * On arrival the client calls
 * {@link FakePortalLevelRegistry#armForIncomingChunks(BlockPos, int)} so that
 * {@link com.code.tama.tts.mixin.seamlessTele.MixinClientPacketListener}
 * knows to redirect the upcoming chunk packets into the correct portal's
 * {@code fakeLevel} instead of the real {@code ClientLevel}.
 *
 * Because this packet travels on the same Netty channel as the chunk packets
 * that follow, ordering is guaranteed — the arm is always set before the first
 * chunk packet is processed.
 */
public class ArmPortalChunksPacketS2C implements ImAPacket {

	private final BlockPos portalPos;
	private final int chunkCount;

	public ArmPortalChunksPacketS2C(BlockPos portalPos, int chunkCount) {
		this.portalPos = portalPos;
		this.chunkCount = chunkCount;
	}

	// -------------------------------------------------------------------------
	// Codec
	// -------------------------------------------------------------------------

	public static void encode(ArmPortalChunksPacketS2C msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.portalPos);
		buf.writeInt(msg.chunkCount);
	}

	public static ArmPortalChunksPacketS2C decode(FriendlyByteBuf buf) {
		return new ArmPortalChunksPacketS2C(buf.readBlockPos(), buf.readInt());
	}

	// -------------------------------------------------------------------------
	// Handler
	// -------------------------------------------------------------------------

	public static void handle(ArmPortalChunksPacketS2C msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
				() -> () -> FakePortalLevelRegistry.armForIncomingChunks(msg.portalPos, msg.chunkCount)));
		ctx.setPacketHandled(true);
	}
}