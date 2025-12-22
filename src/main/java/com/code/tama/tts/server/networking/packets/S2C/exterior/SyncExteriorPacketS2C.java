/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.exterior;

import java.util.function.Supplier;

import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class SyncExteriorPacketS2C {
	private final boolean artificial;

	private final ExteriorState state;

	private final int blockX, blockY, blockZ; // Block position

	private final ResourceKey<Level> level;

	private final ResourceLocation model;

	private final BlockPos targetPos;

	private final float targetY;

	private final int variant;

	public SyncExteriorPacketS2C(ResourceLocation model, ExteriorState state, boolean artificial, int variant,
			@NotNull ResourceKey<Level> level, float targetY, BlockPos targetPos, int x, int y, int z) {
		this.state = state;
		this.artificial = artificial;
		this.variant = variant;
		this.model = model;
		this.level = level;
		this.targetY = targetY;
		this.targetPos = targetPos;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
	}

	public static SyncExteriorPacketS2C decode(FriendlyByteBuf buffer) {
		return new SyncExteriorPacketS2C(buffer.readResourceLocation(), buffer.readEnum(ExteriorState.class),
				buffer.readBoolean(), buffer.readInt(), buffer.readResourceKey(Registries.DIMENSION),
				buffer.readFloat(), buffer.readJsonWithCodec(BlockPos.CODEC), buffer.readInt(), buffer.readInt(),
				buffer.readInt());
	}

	public static void encode(SyncExteriorPacketS2C packet, FriendlyByteBuf buffer) {
		buffer.writeResourceLocation(packet.model);
		buffer.writeEnum(packet.state);
		buffer.writeBoolean(packet.artificial);
		buffer.writeInt(packet.variant);
		buffer.writeResourceKey(packet.level);
		buffer.writeFloat(packet.targetY);
		buffer.writeJsonWithCodec(BlockPos.CODEC, packet.targetPos);
		buffer.writeInt(packet.blockX);
		buffer.writeInt(packet.blockY);
		buffer.writeInt(packet.blockZ);
	}

	public static void handle(SyncExteriorPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			// This code runs on the client side
			BlockPos pos = new BlockPos(packet.blockX, packet.blockY, packet.blockZ);
			if (Minecraft.getInstance().level != null) {
				// Get the block entity at the given position.
				if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ExteriorTile exteriorTile) {
					exteriorTile.targetY = packet.targetY;
					exteriorTile.targetPos = packet.targetPos;
					exteriorTile.targetLevel = packet.level;
					exteriorTile.setModel(packet.variant);
					exteriorTile.setModelIndex(packet.model);
					exteriorTile.state = packet.state;
					exteriorTile.isArtificial = packet.artificial;
				}
			}
		});
		context.setPacketHandled(true);
	}
}
