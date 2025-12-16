package com.code.tama.triggerapi.boti.packets;

import com.code.tama.triggerapi.boti.packets.C2S.PortalChunkRequestPacketC2S;
import com.code.tama.triggerapi.boti.packets.S2C.PortalChunkDataPacketS2C;
import com.code.tama.triggerapi.boti.packets.S2C.PortalSyncPacketS2C;
import com.code.tama.tts.server.networking.Networking;
import net.minecraftforge.network.NetworkDirection;

import java.util.Optional;

public class BOTIPackets {
    public static void registerPackets() {
        Networking.INSTANCE.registerMessage(Networking.id(), PortalSyncPacketS2C.class, PortalSyncPacketS2C::encode,
                PortalSyncPacketS2C::decode, PortalSyncPacketS2C::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        Networking.INSTANCE.registerMessage(Networking.id(), PortalChunkRequestPacketC2S.class, PortalChunkRequestPacketC2S::encode,
                PortalChunkRequestPacketC2S::decode, PortalChunkRequestPacketC2S::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        Networking.INSTANCE.registerMessage(Networking.id(), PortalChunkDataPacketS2C.class, PortalChunkDataPacketS2C::encode,
                PortalChunkDataPacketS2C::decode, PortalChunkDataPacketS2C::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
