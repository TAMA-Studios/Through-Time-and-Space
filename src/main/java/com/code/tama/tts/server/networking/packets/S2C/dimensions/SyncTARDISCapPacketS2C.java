/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.data.tardis.data.TARDISData;
import com.code.tama.tts.server.data.tardis.data.TARDISFlightData;
import com.code.tama.tts.server.data.tardis.data.TARDISNavigationalData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetClientTARDISCapSupplier;

/** Used to sync the TARDIS Cap data between the server and the client */
public class SyncTARDISCapPacketS2C {
	TARDISData data;

	TARDISFlightData flightData;

	TARDISNavigationalData navigationalData;

	String flightEvent;

	int toUpdate;
	public SyncTARDISCapPacketS2C(TARDISData data, TARDISNavigationalData navigationalData, TARDISFlightData flightData,
			int toUpdate) {
		this.data = data;
		this.navigationalData = navigationalData;
		this.flightData = flightData;
		this.toUpdate = toUpdate;
	}
	public static SyncTARDISCapPacketS2C decode(FriendlyByteBuf buffer) {
		int toUpdate = buffer.readInt();

		switch (toUpdate) {
			case DataUpdateValues.DATA, DataUpdateValues.RENDERING : {
				return new SyncTARDISCapPacketS2C(FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISData.CODEC),
						null, null, toUpdate);
			}
			case DataUpdateValues.FLIGHT : {
				return new SyncTARDISCapPacketS2C(null, null,
						FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISFlightData.CODEC), toUpdate);
			}
			case DataUpdateValues.NAVIGATIONAL : {
				return new SyncTARDISCapPacketS2C(null,
						FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISNavigationalData.CODEC), null, toUpdate);
			}
			default : {
				TARDISData data = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISData.CODEC);
				TARDISNavigationalData nav = FriendlyByteBufOps.Helper.readWithCodec(buffer,
						TARDISNavigationalData.CODEC);
				TARDISFlightData flight = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISFlightData.CODEC);
				return new SyncTARDISCapPacketS2C(data, nav, flight, toUpdate);
			}
		}
	}
	public static void encode(SyncTARDISCapPacketS2C packet, FriendlyByteBuf buffer) {
		buffer.writeInt(packet.toUpdate);

		switch (packet.toUpdate) {
			case DataUpdateValues.DATA, DataUpdateValues.RENDERING : {
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISData.CODEC, packet.data);
				break;
			}
			case DataUpdateValues.FLIGHT : {
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISFlightData.CODEC, packet.flightData);
				break;
			}
			case DataUpdateValues.NAVIGATIONAL : {
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISNavigationalData.CODEC, packet.navigationalData);
				break;
			}
			default : {
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISData.CODEC, packet.data);
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISNavigationalData.CODEC, packet.navigationalData);
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISFlightData.CODEC, packet.flightData);
			}
		}
	}

	public static void handle(SyncTARDISCapPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().level != null) {
				GetClientTARDISCapSupplier().ifPresent(cap -> {
					switch (packet.toUpdate) {
						case DataUpdateValues.DATA, DataUpdateValues.RENDERING : {
							cap.setData(packet.data);
							if (cap.GetExteriorTile() != null) {
								cap.GetExteriorTile().Model = cap.GetData().getExteriorModel();
								cap.GetExteriorTile().setModelIndex(cap.GetData().getExteriorModel().getModel());
							}
							break;
						}
						case DataUpdateValues.FLIGHT : {
							cap.setFlightData(packet.flightData);
							break;
						}
						case DataUpdateValues.NAVIGATIONAL : {
							cap.setNavigationalData(packet.navigationalData);
							break;
						}
						default : {
							cap.setData(packet.data);
							cap.setNavigationalData(packet.navigationalData);
							cap.setFlightData(packet.flightData);
						}
					}
				});
			}
		});
		context.setPacketHandled(true);
	}
}
