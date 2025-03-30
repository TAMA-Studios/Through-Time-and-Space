package com.code.tama.mtm.server.networking.packets.entities;


import com.code.tama.mtm.server.entities.controls.ModularControl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Blows up the creeper supplied by the packet **/
public class ModularControlCyclePacket {

    public UUID control;

    public ModularControlCyclePacket(UUID control) {
        this.control = control;
    }

    public static void encode(ModularControlCyclePacket mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.control);
    }

    public static ModularControlCyclePacket decode(FriendlyByteBuf buf) {
        return new ModularControlCyclePacket(buf.readUUID());
    }

    public static void handle(ModularControlCyclePacket mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ModularControl control = (ModularControl) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.control);

        });
        context.get().setPacketHandled(true);
    }
}