/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.exterior;

import com.code.tama.tts.server.misc.containers.PhysicalStateManager;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import lombok.AllArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExteriorStatePacket {
    public enum State {
        TAKEOFF,
        LAND
    }

    private final BlockPos pos;
    private final State state;
    private final long startTick;

    public ExteriorStatePacket(BlockPos pos, State state, long startTick) {
        this.pos = pos;
        this.state = state;
        this.startTick = startTick;
    }

    public static void encode(ExteriorStatePacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeEnum(msg.state);
        buf.writeLong(msg.startTick);
    }

    public static ExteriorStatePacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        State state = buf.readEnum(State.class);
        long tick = buf.readLong();
        return new ExteriorStatePacket(pos, state, tick);
    }

    public static void handle(ExteriorStatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.level == null) return;
            var be = mc.level.getBlockEntity(msg.pos);
            if (be instanceof com.code.tama.tts.server.tileentities.ExteriorTile exterior) {
                // Create a client-side PhysicalStateManager
                new Thread(exterior, msg.state, msg.startTick);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public BlockPos getPos() {
        return pos;
    }

    public State getState() {
        return state;
    }

    public long getStartTick() {
        return startTick;
    }

    @AllArgsConstructor
    public static class Thread extends java.lang.Thread {
        ExteriorTile exteriorTile;
        ExteriorStatePacket.State state;
        long StartTick;

        @Override
        public void run() {
            PhysicalStateManager manager = new PhysicalStateManager(null, exteriorTile);
            if (state == ExteriorStatePacket.State.TAKEOFF) {
                manager.clientTakeOff(StartTick);
            } else {
                manager.clientLand(StartTick);
            }
            super.run();
        }
    }
}
