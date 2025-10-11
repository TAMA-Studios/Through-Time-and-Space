/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.dimensions;

import com.code.tama.triggerapi.codec.FriendlyByteBufOps;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tardis.data.TARDISData;
import com.code.tama.tts.server.tardis.data.TARDISFlightData;
import com.code.tama.tts.server.tardis.data.TARDISNavigationalData;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/**
 * Used to sync the TARDIS Cap data between the server and the client
 */
public class SyncTARDISCapPacketS2C {
    public static SyncTARDISCapPacketS2C decode(FriendlyByteBuf buffer) {
        TARDISData data = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISData.CODEC);
        TARDISNavigationalData nav = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISNavigationalData.CODEC);
        TARDISFlightData flight = FriendlyByteBufOps.Helper.readWithCodec(buffer, TARDISFlightData.CODEC);

        return new SyncTARDISCapPacketS2C(data, nav, flight);
    }

    public static void encode(SyncTARDISCapPacketS2C packet, FriendlyByteBuf buffer) {
        FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISData.CODEC, packet.data);
        FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISNavigationalData.CODEC, packet.navigationalData);
        FriendlyByteBufOps.Helper.writeWithCodec(buffer, TARDISFlightData.CODEC, packet.flightData);

        // encodeStart() returns a DataResult<FriendlyByteBuf>
        //        TARDISData.CODEC.encodeStart(FriendlyByteBufOps.INSTANCE, packet.data)
        //                .resultOrPartial(TTSMod.LOGGER::error)
        //                .ifPresent(buffer::writeBytes);
        //
        //        TARDISFlightData.CODEC.encodeStart(FriendlyByteBufOps.INSTANCE, packet.flightData)
        //                .resultOrPartial(TTSMod.LOGGER::error)
        //                .ifPresent(buffer::writeBytes);
        //
        //        TARDISNavigationalData.CODEC.encodeStart(FriendlyByteBufOps.INSTANCE, packet.navigationalData)
        //                .resultOrPartial(TTSMod.LOGGER::error)
        //                .ifPresent(buffer::writeBytes);
    }

    public static void handle(SyncTARDISCapPacketS2C packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                Minecraft.getInstance()
                        .level
                        .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                        .ifPresent(cap -> {
                            cap.setData(packet.data);
                            cap.setNavigationalData(packet.navigationalData);
                            cap.setFlightData(packet.flightData);

                            if (cap.GetExteriorTile() != null) {
                                cap.GetExteriorTile().Variant = cap.GetData().GetExteriorVariant();
                                cap.GetExteriorTile()
                                        .setModelIndex(
                                                cap.GetData().getExteriorModel().getModel());
                            }
                        });
            }
        });
        context.setPacketHandled(true);
    }

    TARDISData data;
    TARDISNavigationalData navigationalData;
    TARDISFlightData flightData;

    public SyncTARDISCapPacketS2C(
            TARDISData data, TARDISNavigationalData navigationalData, TARDISFlightData flightData) {
        this.data = data;
        this.navigationalData = navigationalData;
        this.flightData = flightData;
    }
}
