/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.C2S.entities;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.entities.controls.ModularControl;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ControlHitPacketC2S {

    public static ControlHitPacketC2S decode(FriendlyByteBuf buf) {
        return new ControlHitPacketC2S(buf.readUUID());
    }

    public static void encode(ControlHitPacketC2S mes, FriendlyByteBuf buf) {
        buf.writeUUID(mes.control);
    }

    public static void handle(ControlHitPacketC2S mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ModularControl control = (ModularControl) context.get()
                    .getSender()
                    .level()
                    .getServer()
                    .getLevel(context.get().getSender().level().dimension())
                    .getEntity(mes.control);
            assert control != null;
            context.get()
                    .getSender()
                    .level()
                    .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                    .ifPresent(cap -> control.OnControlHit(cap, context.get().getSender()));
        });
        context.get().setPacketHandled(true);
    }

    public UUID control;

    public ControlHitPacketC2S(UUID control) {
        this.control = control;
    }
}
