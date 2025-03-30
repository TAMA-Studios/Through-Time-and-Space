package com.code.tama.mtm.server.networking.packets.entities;


import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.entities.controls.ModularControl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/** Blows up the creeper supplied by the packet **/
public class ControlClickedPacket {

    public UUID control;

    public ControlClickedPacket(UUID control) {
        this.control = control;
    }

    public static void encode(ControlClickedPacket mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.control);
    }

    public static ControlClickedPacket decode(FriendlyByteBuf buf) {
        return new ControlClickedPacket(buf.readUUID());
    }

    public static void handle(ControlClickedPacket mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ModularControl control = (ModularControl) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.control);
            control.OnControlClicked(context.get().getSender().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null), context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}