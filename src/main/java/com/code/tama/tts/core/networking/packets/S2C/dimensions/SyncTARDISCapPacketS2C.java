/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.S2C.dimensions;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetClientTARDISCapSupplier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.code.tama.tts.client.gui.ARSGrid;
import com.code.tama.tts.client.gui.ARSPos;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.data.tardis.PowerHandler;
import com.code.tama.tts.server.data.tardis.data.TARDISData;
import com.code.tama.tts.server.data.tardis.data.TARDISFlightData;
import com.code.tama.tts.server.data.tardis.data.TARDISNavigationalData;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;

/** Used to sync the TARDIS Cap data between the server and the client */
public class SyncTARDISCapPacketS2C {
	PowerHandler Energy;
	TARDISData data;
	TARDISFlightData flightData;
	Map<ARSPos, ARSGrid> ARS;
	TARDISNavigationalData navigationalData;

	String flightEvent;

	int toUpdate;

	public SyncTARDISCapPacketS2C(TARDISLevelCapability tardis, int toUpdate) {
		this.data = tardis.GetData();
		this.navigationalData = tardis.GetNavigationalData();
		this.flightData = tardis.GetFlightData();
		this.Energy = tardis.getEnergy();
		this.toUpdate = toUpdate;
		this.ARS = tardis.getARS_GRIDS();
	}

	public SyncTARDISCapPacketS2C(PowerHandler Energy, TARDISData data, TARDISNavigationalData navigationalData,
			TARDISFlightData flightData, Map<ARSPos, ARSGrid> ars, int toUpdate) {
		this.Energy = Energy;
		this.data = data;
		this.navigationalData = navigationalData;
		this.flightData = flightData;
		this.ARS = ars;
		this.toUpdate = toUpdate;
	}
	public static SyncTARDISCapPacketS2C decode(FriendlyByteBuf buffer) {
		int toUpdate = buffer.readInt();
		PowerHandler Energy = FriendlyByteBufOps.Helper.readWithCodec(buffer, PowerHandler.CODEC);

		switch (toUpdate) {
			case DataUpdateValues.DATA, DataUpdateValues.RENDERING : {
				return new SyncTARDISCapPacketS2C(Energy,
						FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISData.CODEC), null, null, null, toUpdate);
			}
			case DataUpdateValues.FLIGHT : {
				return new SyncTARDISCapPacketS2C(Energy, null, null,
						FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISFlightData.CODEC), null, toUpdate);
			}
			case DataUpdateValues.NAVIGATIONAL : {
				return new SyncTARDISCapPacketS2C(Energy, null,
						FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISNavigationalData.CODEC), null, null,
						toUpdate);
			}

			case DataUpdateValues.ARS : {
				return new SyncTARDISCapPacketS2C(Energy, null, null, null, getARS(buffer.readNbt()), toUpdate);
			}

			default : {
				TARDISData data = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISData.CODEC);
				TARDISNavigationalData nav = FriendlyByteBufOps.Helper.readWithCodec(buffer,
						TARDISNavigationalData.CODEC);
				TARDISFlightData flight = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISFlightData.CODEC);

				return new SyncTARDISCapPacketS2C(Energy, data, nav, flight, getARS(buffer.readNbt()), toUpdate);
			}
		}
	}

	public static Map<ARSPos, ARSGrid> getARS(CompoundTag nbt) {
		Map<ARSPos, ARSGrid> ARS_GRIDS = new HashMap<>();
		for (int i = 0; i < nbt.getInt("rooms"); i++) {
			// grids.add(ARSGrid.deserialize(nbt.getCompound("ars_" + i)));
			// gridPoss.add(ARSPos.deserialize(nbt.getCompound("ars_pos_" + i)));
			ARS_GRIDS.put(ARSPos.deserialize(nbt.getCompound("ars_pos_" + i)),
					ARSGrid.deserialize(nbt.getCompound("ars_" + i)));
		}
		return ARS_GRIDS;
	}

	public static CompoundTag ARSTag(Map<ARSPos, ARSGrid> ars) {
		if (ars == null)
			return null;
		CompoundTag tag = new CompoundTag();
		List<ARSGrid> grids = new ArrayList<>();
		List<ARSPos> gridPoss = new ArrayList<>();
		ars.forEach((g, x) -> {
			grids.add(x);
			gridPoss.add(g);
		});
		for (int i = 0; i < ars.size(); i++) {
			tag.put("ars_" + i, grids.get(i).serialize());
			tag.put("ars_pos_" + i, gridPoss.get(i).serialize());
		}

		tag.putInt("rooms", ars.size());

		return tag;
	}

	public static void encode(SyncTARDISCapPacketS2C packet, FriendlyByteBuf buffer) {
		buffer.writeInt(packet.toUpdate);
		FriendlyByteBufOps.Helper.writeWithCodec(buffer, PowerHandler.CODEC, packet.Energy);

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
			case DataUpdateValues.ARS : {
				buffer.writeNbt(ARSTag(packet.ARS));
				break;
			}
			default : {
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISData.CODEC, packet.data);
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISNavigationalData.CODEC, packet.navigationalData);
				FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISFlightData.CODEC, packet.flightData);
				buffer.writeNbt(ARSTag(packet.ARS));
			}
		}
	}

	public static void handle(SyncTARDISCapPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context context = contextSupplier.get();
		context.enqueueWork(() -> {
			if (Minecraft.getInstance().level != null) {
				GetClientTARDISCapSupplier().ifPresent(cap -> {
					cap.getEnergy().artron = packet.Energy.artron;
					cap.getEnergy().getEnergyCap().setEnergy(packet.Energy.getEnergyCap().getEnergyStored());
					cap.getEnergy().potential = packet.Energy.potential;

					switch (packet.toUpdate) {
						case DataUpdateValues.DATA, DataUpdateValues.RENDERING : {
							cap.setData(packet.data);
							if (cap.GetExteriorTile() != null) {
								cap.GetExteriorTile().Model = cap.GetData().getExteriorModel();
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
						case DataUpdateValues.ARS : {
							((TARDISLevelCapability) cap).setARS_GRIDS(packet.ARS);
							break;
						}
						default : {
							cap.setData(packet.data);
							cap.setNavigationalData(packet.navigationalData);
							cap.setFlightData(packet.flightData);
							((TARDISLevelCapability) cap).setARS_GRIDS(packet.ARS);
						}
					}
				});
			}
		});
		context.setPacketHandled(true);
	}
}
