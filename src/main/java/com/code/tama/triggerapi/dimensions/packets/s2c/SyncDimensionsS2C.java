/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.packets.s2c;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.dimensions.SyncDimensionsClientHandler;

public class SyncDimensionsS2C {

	public boolean add;

	public ResourceKey<Level> level;

	public SyncDimensionsS2C(ResourceKey<Level> level, boolean add) {
		this.level = level;
		this.add = add;
	}

	public static SyncDimensionsS2C decode(FriendlyByteBuf buf) {
		return new SyncDimensionsS2C(buf.readResourceKey(Registries.DIMENSION), buf.readBoolean());
	}

	public static void encode(SyncDimensionsS2C mes, FriendlyByteBuf buf) {
		buf.writeResourceKey(mes.level);
		buf.writeBoolean(mes.add);
	}

	public static void handle(SyncDimensionsS2C mes, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> SyncDimensionsClientHandler.handleDimSyncPacket(mes));
		context.get().setPacketHandled(true);
	}
}
