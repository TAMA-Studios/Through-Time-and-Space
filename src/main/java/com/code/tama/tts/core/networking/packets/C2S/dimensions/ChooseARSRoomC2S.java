/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import java.util.function.Supplier;

import com.code.tama.tts.client.gui.ARSGrid;
import com.code.tama.tts.client.gui.ARSPos;
import com.code.tama.tts.core.registries.tardis.ARSRegistry;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;
import com.code.tama.triggerapi.helpers.world.WorldHelper;
import com.code.tama.triggerapi.networking.ImAPacket;
import com.code.tama.triggerapi.universal.UniversalCommon;

/** Tells the server to set an ARS room */
public class ChooseARSRoomC2S implements ImAPacket {
	ARSPos pos;
	String roomID;
	int color;

	public ChooseARSRoomC2S(ARSPos pos, String roomID, int color) {
		this.pos = pos;
		this.roomID = roomID;
		this.color = color;
	}

	public static ChooseARSRoomC2S decode(FriendlyByteBuf buffer) {
		return new ChooseARSRoomC2S(FriendlyByteBufOps.Helper.readWithCodec(buffer, ARSPos.CODEC), buffer.readUtf(),
				buffer.readInt());
	}

	public static void encode(ChooseARSRoomC2S packet, FriendlyByteBuf buffer) {
		FriendlyByteBufOps.Helper.writeWithCodec(buffer, ARSPos.CODEC, packet.pos);
		buffer.writeUtf(packet.roomID);
		buffer.writeInt(packet.color);
	}

	public static void handle(ChooseARSRoomC2S packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			GetTARDISCapSupplier(context.getSender().level()).ifPresent(cap -> {
				WorldHelper.PlaceStructure((ServerLevel) context.getSender().level(),
						packet.pos.getOrigin().above(ARSRegistry.GetByPath(packet.roomID).HeightOffs()),
						UniversalCommon.parse(packet.roomID));
				cap.addARSGrid(new ARSGrid(packet.pos, packet.roomID, packet.color));
			});
		});
		context.setPacketHandled(true);
	}
}
