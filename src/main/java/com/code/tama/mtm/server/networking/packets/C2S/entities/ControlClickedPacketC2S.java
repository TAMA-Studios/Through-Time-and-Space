package com.code.tama.mtm.server.networking.packets.C2S.entities;


import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.entities.controls.ModularControl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ControlClickedPacketC2S {

    public UUID control;

    public ControlClickedPacketC2S(UUID control) {
        this.control = control;
    }

    public static void encode(ControlClickedPacketC2S mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.control);
    }

    public static ControlClickedPacketC2S decode(FriendlyByteBuf buf) {
        return new ControlClickedPacketC2S(buf.readUUID());
    }

    public static void handle(ControlClickedPacketC2S mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ModularControl control = (ModularControl) context.get().getSender().level().getServer().getLevel(context.get().getSender().level().dimension()).getEntity(mes.control);
            control.OnControlClicked(context.get().getSender().level().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).orElse(null), context.get().getSender());
        });
        context.get().setPacketHandled(true);
    }
}