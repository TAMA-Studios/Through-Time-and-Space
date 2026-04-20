/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.S2C.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.LevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;
import com.code.tama.triggerapi.networking.ImAPacket;

/** Used to sync the TARDIS Cap data between the server and the client */
public class SyncLevelCapPacketS2C implements ImAPacket {
	ILevelCap cap;

	public SyncLevelCapPacketS2C(ILevelCap cap) {
		this.cap = cap;
	}

	public static void encode(SyncLevelCapPacketS2C packet, FriendlyByteBuf buffer) {
		TTSMod.LOGGER.info("[SYNC] Encoding {} rifts, {} TIR blocks", packet.cap.GetRiftData().size(),
				packet.cap.GetTIRBlocks().size());
		FriendlyByteBufOps.Helper.writeWithCodec(buffer, LevelCapability.CODEC, packet.cap);
		TTSMod.LOGGER.info("[SYNC] Encoded successfully, {} bytes", buffer.writerIndex());
	}

	public static SyncLevelCapPacketS2C decode(FriendlyByteBuf buffer) {
		TTSMod.LOGGER.info("[SYNC] Decoding packet, {} readable bytes", buffer.readableBytes());
		ILevelCap fallback = new LevelCapability((Level) null);
		ILevelCap result = FriendlyByteBufOps.Helper.readWithCodec(buffer, LevelCapability.CODEC);
		TTSMod.LOGGER.info("[SYNC] Decoded {} rifts, {} TIR blocks", result.GetRiftData().size(),
				result.GetTIRBlocks().size());
		return new SyncLevelCapPacketS2C(result);
	}

	public static void handle(SyncLevelCapPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().level != null) {
				ILevelCap cap = Minecraft.getInstance().level.getCapability(Capabilities.LEVEL_CAPABILITY)
						.orElse(packet.cap);

				cap.SetRiftData(packet.cap.GetRiftData());
				cap.SetTIRBlocks(packet.cap.GetTIRBlocks());
			}
		});
		context.setPacketHandled(true);
	}
}
