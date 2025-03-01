package com.code.tama.mtm.Networking.Packets.Entities;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Blows up the creeper supplied by the packet **/
public class BlowUpCreeperPacket {

    public UUID creeper;

    public BlowUpCreeperPacket(UUID creeper) {
        this.creeper = creeper;
    }

    public static void encode(BlowUpCreeperPacket mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.creeper);
    }

    public static BlowUpCreeperPacket decode(FriendlyByteBuf buf) {
        return new BlowUpCreeperPacket(buf.readUUID());
    }

    public static void handle(BlowUpCreeperPacket mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Creeper creeper1 = (Creeper) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.creeper);
            creeper1.ignite();
        });
        context.get().setPacketHandled(true);
    }
}