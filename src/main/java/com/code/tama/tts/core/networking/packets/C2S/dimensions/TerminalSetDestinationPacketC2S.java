/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

/**
 * Sets the TARDIS destination to an exact block position + dimension, as typed
 * into the terminal's Navigation tab. Uses {@code forceSetDestination},
 * bypassing the coordinate lock, since the terminal itself is the trusted
 * control surface (the coordinate lock is meant to protect against non-terminal
 * interference).
 */
public class TerminalSetDestinationPacketC2S {

	private final BlockPos pos;
	private final ResourceLocation dimension;

	public TerminalSetDestinationPacketC2S(BlockPos pos, ResourceLocation dimension) {
		this.pos = pos;
		this.dimension = dimension;
	}

	public TerminalSetDestinationPacketC2S(FriendlyByteBuf buf) {
		this.pos = buf.readBlockPos();
		this.dimension = buf.readResourceLocation();
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(pos);
		buf.writeResourceLocation(dimension);
	}

	public static void handle(TerminalSetDestinationPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null)
				return;

			ITARDISLevel tardis = TARDISLevelCapability.GetTARDISCap(player.level());
			if (tardis == null)
				return;

			ResourceKey<Level> levelKey = ResourceKey.create(Registries.DIMENSION, msg.dimension);
			SpaceTimeCoordinate coordinate = new SpaceTimeCoordinate(msg.pos, levelKey);

			tardis.GetNavigationalData().forceSetDestination(coordinate);

			tardis.UpdateClient(DataUpdateValues.ALL);
		});
		ctx.get().setPacketHandled(true);
	}
}
