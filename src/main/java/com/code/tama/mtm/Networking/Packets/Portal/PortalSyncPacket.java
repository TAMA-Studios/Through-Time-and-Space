package com.code.tama.mtm.Networking.Packets.Portal;

import com.code.tama.mtm.TileEntities.PortalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;
public class PortalSyncPacket {
    private final BlockPos pos;
    private final ResourceKey<Level> targetLevel;
    private final BlockPos targetPos;

    public PortalSyncPacket(BlockPos pos, ResourceKey<Level> targetLevel, BlockPos targetPos) {
        this.pos = pos;
        this.targetLevel = targetLevel;
        this.targetPos = targetPos;
    }

    public static void encode(PortalSyncPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeResourceLocation(msg.targetLevel.location());
        buf.writeBlockPos(msg.targetPos);
    }

    public static PortalSyncPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        ResourceKey<Level> level = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
        BlockPos targetPos = buf.readBlockPos();
        return new PortalSyncPacket(pos, level, targetPos);
    }

    public static void handle(PortalSyncPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                BlockEntity be = level.getBlockEntity(msg.pos);
                if (be instanceof PortalTileEntity portal) {
                    portal.setTargetLevel(msg.targetLevel, msg.targetPos, false);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}