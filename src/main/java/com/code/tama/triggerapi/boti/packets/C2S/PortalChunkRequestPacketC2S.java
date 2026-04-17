/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.boti.packets.C2S;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;

public class PortalChunkRequestPacketC2S {

	private static final int FAKE_LEVEL_CHUNK_RADIUS = 2; // 5×5 area

	private final int chunks;
	private final BlockPos portalPos;
	private final ResourceKey<Level> targetLevel;
	private final BlockPos targetPos;

	public PortalChunkRequestPacketC2S(BlockPos portalPos, ResourceKey<Level> targetLevel, BlockPos targetPos,
									   int chunks) {
		this.portalPos = portalPos;
		this.targetLevel = targetLevel;
		this.targetPos = targetPos;
		this.chunks = chunks;
	}

	public static PortalChunkRequestPacketC2S decode(FriendlyByteBuf buf) {
		return new PortalChunkRequestPacketC2S(buf.readBlockPos(),
				ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation()), buf.readBlockPos(),
				buf.readInt());
	}

	public static void encode(PortalChunkRequestPacketC2S msg, FriendlyByteBuf buf) {
		buf.writeBlockPos(msg.portalPos);
		buf.writeResourceLocation(msg.targetLevel.location());
		buf.writeBlockPos(msg.targetPos);
		buf.writeInt(msg.chunks);
	}

	public static void handle(PortalChunkRequestPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null) return;

			ServerLevel targetLevel = player.server.getLevel(msg.targetLevel);
			if (targetLevel == null) {
				System.out.println("Target level not loaded: " + msg.targetLevel.location());
				return;
			}

			// TODO: Add config option for VBO and remove this
			BOTIUtils.GatherChunkData(
					(AbstractPortalTile) player.level().getBlockEntity(msg.portalPos),
					targetLevel,
					msg.chunks);

			// Send vanilla chunk packets so the client's fakeLevel gets real block data.
			// The client mixin (MixinClientPacketListener) intercepts these and routes
			// them into the portal's fakeLevel instead of the real ClientLevel.
			sendFakeLevelChunks(player, targetLevel, msg.targetPos, msg.portalPos);
		});
		ctx.get().setPacketHandled(true);
	}

	/**
	 * Sends vanilla {@link ClientboundLevelChunkWithLightPacket}s for a small
	 * region around {@code targetPos} directly to {@code player}.
	 * <p>
	 * The client mixin arms the correct portal before these arrive via
	 * {@link com.code.tama.triggerapi.boti.FakePortalLevelRegistry#armForIncomingChunks}.
	 * We piggyback the arm signal on the existing S2C channel by sending it
	 * immediately before the chunk stream — both are enqueued on the same Netty
	 * channel so ordering is guaranteed.
	 */
	private static void sendFakeLevelChunks(ServerPlayer player, ServerLevel targetLevel,
											BlockPos targetPos, BlockPos portalPos) {

		ChunkPos center = new ChunkPos(targetPos);
		int r = FAKE_LEVEL_CHUNK_RADIUS;
		int total = (r * 2 + 1) * (r * 2 + 1);

		// Arm the client registry FIRST so the mixin knows where to route chunks.
		com.code.tama.tts.core.networking.Networking.INSTANCE.send(
				net.minecraftforge.network.PacketDistributor.PLAYER.with(() -> player),
				new com.code.tama.triggerapi.boti.packets.S2C.ArmPortalChunksPacketS2C(portalPos, total));

		// Send vanilla chunk packets. These are sent on player.connection directly
		//    (same Netty pipeline) so they arrive after the arm packet above.
		for (int cx = -r; cx <= r; cx++) {
			for (int cz = -r; cz <= r; cz++) {
				ChunkPos pos = new ChunkPos(center.x + cx, center.z + cz);

				// Force-load if not already loaded
				targetLevel.getChunkSource().addRegionTicket(
						net.minecraft.server.level.TicketType.FORCED,
						pos, 0, pos);

				LevelChunk chunk = targetLevel.getChunk(pos.x, pos.z);

				// Send vanilla chunk + light packet
				player.connection.send(new ClientboundLevelChunkWithLightPacket(
						chunk,
						targetLevel.getLightEngine(),
						null,
						null));

				// Also send block entity data for anything inside (chests, signs, etc.)
				chunk.getBlockEntities().forEach((bePos, be) -> {
					net.minecraft.network.protocol.Packet<?> bePacket = be.getUpdatePacket();
					if (bePacket != null) {
						player.connection.send(bePacket);
					}
				});
			}
		}
	}
}