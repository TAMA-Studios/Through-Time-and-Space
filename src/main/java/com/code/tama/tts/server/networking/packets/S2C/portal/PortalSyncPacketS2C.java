/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking.packets.S2C.portal;

import com.code.tama.tts.server.tileentities.AbstractPortalTile;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class PortalSyncPacketS2C {
    @OnlyIn(Dist.CLIENT)
    public static void SyncPortal(PortalSyncPacketS2C msg) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            BlockEntity be = level.getBlockEntity(msg.pos);
            if (be instanceof AbstractPortalTile portal) {
                portal.setTargetLevel(msg.targetLevel, msg.targetPos, false);
            }
        }
    }

    public static PortalSyncPacketS2C decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        ResourceKey<Level> level = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
        BlockPos targetPos = buf.readBlockPos();
        return new PortalSyncPacketS2C(pos, level, targetPos);
    }

    public static void encode(PortalSyncPacketS2C msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeResourceLocation(msg.targetLevel.location());
        buf.writeBlockPos(msg.targetPos);
    }

    public static void handle(PortalSyncPacketS2C msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SyncPortal(msg)));
        ctx.get().setPacketHandled(true);
    }

    private final BlockPos pos;

    private final ResourceKey<Level> targetLevel;

    private final BlockPos targetPos;

    public PortalSyncPacketS2C(BlockPos pos, ResourceKey<Level> targetLevel, BlockPos targetPos) {
        this.pos = pos;
        this.targetLevel = targetLevel;
        this.targetPos = targetPos;
    }
}
