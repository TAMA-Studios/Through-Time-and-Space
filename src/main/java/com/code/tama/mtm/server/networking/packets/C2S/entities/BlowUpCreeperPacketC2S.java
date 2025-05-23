package com.code.tama.mtm.server.networking.packets.C2S.entities;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Blows up the creeper supplied by the packet **/
public class BlowUpCreeperPacketC2S {

    public UUID creeper;

    public BlowUpCreeperPacketC2S(UUID creeper) {
        this.creeper = creeper;
    }

    public static void encode(BlowUpCreeperPacketC2S mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.creeper);
    }

    public static BlowUpCreeperPacketC2S decode(FriendlyByteBuf buf) {
        return new BlowUpCreeperPacketC2S(buf.readUUID());
    }

    public static void handle(BlowUpCreeperPacketC2S mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Creeper creeper1 = (Creeper) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.creeper);
            creeper1.ignite();
        });
        context.get().setPacketHandled(true);
    }
}