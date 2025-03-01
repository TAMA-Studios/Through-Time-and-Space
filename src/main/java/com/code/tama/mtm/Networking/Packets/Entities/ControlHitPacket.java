package com.code.tama.mtm.Networking.Packets.Entities;


import com.code.tama.mtm.Capabilities.CapabilityConstants;
import com.code.tama.mtm.Entities.Controls.ModularControl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Blows up the creeper supplied by the packet **/
public class ControlHitPacket {

    public UUID control;

    public ControlHitPacket(UUID control) {
        this.control = control;
    }

    public static void encode(ControlHitPacket mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.control);
    }

    public static ControlHitPacket decode(FriendlyByteBuf buf) {
        return new ControlHitPacket(buf.readUUID());
    }

    public static void handle(ControlHitPacket mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ModularControl control = (ModularControl) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.control);
            control.OnControlHit(context.get().getSender().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null), context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}