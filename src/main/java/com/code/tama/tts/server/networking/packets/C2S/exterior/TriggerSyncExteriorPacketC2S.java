/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.exterior;

import java.util.function.Supplier;

import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncExteriorPacketS2C;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.tileentities.ExteriorTile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

public class TriggerSyncExteriorPacketC2S {
	private final int blockX, blockY, blockZ; // Block position

	private final ResourceKey<Level> level;

	public TriggerSyncExteriorPacketC2S(ResourceKey<Level> level, int x, int y, int z) {
		this.level = level;
		this.blockX = x;
		this.blockY = y;
		this.blockZ = z;
	}

	public static TriggerSyncExteriorPacketC2S decode(FriendlyByteBuf buffer) {
		return new TriggerSyncExteriorPacketC2S(buffer.readResourceKey(Registries.DIMENSION), buffer.readInt(),
				buffer.readInt(), buffer.readInt());
	}

	public static void encode(TriggerSyncExteriorPacketC2S packet, FriendlyByteBuf buffer) {
		buffer.writeResourceKey(packet.level);
		buffer.writeInt(packet.blockX);
		buffer.writeInt(packet.blockY);
		buffer.writeInt(packet.blockZ);
	}

	public static void handle(TriggerSyncExteriorPacketC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			BlockEntity be = ServerLifecycleHooks.getCurrentServer().getLevel(packet.level)
					.getBlockEntity(new BlockPos(packet.blockX, packet.blockY, packet.blockZ));
			if (be instanceof ExteriorTile exteriorTile) {
				Networking.sendPacketToDimension(packet.level,
						new SyncExteriorPacketS2C(exteriorTile.getModelIndex(), exteriorTile.state,
								exteriorTile.isArtificial, ExteriorsRegistry.GetOrdinal(exteriorTile.GetVariant()),
								exteriorTile.targetLevel, exteriorTile.targetY, exteriorTile.targetPos, packet.blockX,
								packet.blockY, packet.blockZ));
			}
		});
		context.setPacketHandled(true);
	}
}
