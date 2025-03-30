package com.code.tama.mtm.server.networking.packets.dimensions;


import com.code.tama.mtm.server.misc.ClientHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncDimensions {

    public ResourceKey<Level> level;
    public boolean add = true;

    public SyncDimensions(ResourceKey<Level> level, boolean add) {
        this.level = level;
        this.add = add;
    }

    public static void encode(SyncDimensions mes, FriendlyByteBuf buf) {
        buf.writeResourceKey(mes.level);
        buf.writeBoolean(mes.add);
    }

    public static SyncDimensions decode(FriendlyByteBuf buf) {
        return new SyncDimensions(buf.readResourceKey(Registries.DIMENSION), buf.readBoolean());
    }

    public static void handle(SyncDimensions mes, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> ClientHelper.handleDimSyncPacket(mes));
        context.get().setPacketHandled(true);
    }
}