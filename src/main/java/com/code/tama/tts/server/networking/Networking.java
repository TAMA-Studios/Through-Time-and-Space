/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.networking;

import com.code.tama.triggerapi.dimensions.packets.s2c.SyncDimensionsS2C;
import com.code.tama.triggerapi.dimensions.packets.s2c.UpdateDimensionsS2C;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapLightPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.dimensions.TriggerSyncCapVariantPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.entities.BlowUpCreeperPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.entities.ControlClickedPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.entities.ControlHitPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.entities.StopViewingExteriorC2S;
import com.code.tama.tts.server.networking.packets.C2S.exterior.TriggerSyncExteriorVariantPacketC2S;
import com.code.tama.tts.server.networking.packets.C2S.portal.PortalChunkRequestPacketC2S;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncCapLightLevelPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncCapVariantPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.dimensions.SyncTARDISCapPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncViewedTARDISS2C;
import com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncExteriorVariantPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.exterior.SyncTransparencyPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalChunkDataPacketS2C;
import com.code.tama.tts.server.networking.packets.S2C.portal.PortalSyncPacketS2C;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.code.tama.tts.TTSMod.MODID;

public class Networking {
    public static final String NET_VERSION = "1.0";

    public static int ID = 0;
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"), () -> NET_VERSION, NET_VERSION::equals, NET_VERSION::equals);

    public static int id() {
        return ID++;
    }

    public static <T> void register(
            Class<T> clazz,
            BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, Supplier<NetworkEvent.Context>> handler) {
        INSTANCE.registerMessage(id(), clazz, encoder, decoder, handler);
    }

    public static void registerPackets() {
        // Entity Packets
        register(
                ControlClickedPacketC2S.class,
                ControlClickedPacketC2S::encode,
                ControlClickedPacketC2S::decode,
                ControlClickedPacketC2S::handle);
        register(
                ControlHitPacketC2S.class,
                ControlHitPacketC2S::encode,
                ControlHitPacketC2S::decode,
                ControlHitPacketC2S::handle);
        register(
                BlowUpCreeperPacketC2S.class,
                BlowUpCreeperPacketC2S::encode,
                BlowUpCreeperPacketC2S::decode,
                BlowUpCreeperPacketC2S::handle);
        register(
                SyncButtonAnimationSetPacketS2C.class,
                SyncButtonAnimationSetPacketS2C::encode,
                SyncButtonAnimationSetPacketS2C::decode,
                SyncButtonAnimationSetPacketS2C::handle);

        // Exterior Data
        register(
                SyncExteriorVariantPacketS2C.class,
                SyncExteriorVariantPacketS2C::encode,
                SyncExteriorVariantPacketS2C::decode,
                SyncExteriorVariantPacketS2C::handle);
        register(
                SyncTransparencyPacketS2C.class,
                SyncTransparencyPacketS2C::encode,
                SyncTransparencyPacketS2C::decode,
                SyncTransparencyPacketS2C::handle);

        // Cap Data
        register(
                SyncDimensionsS2C.class,
                SyncDimensionsS2C::encode,
                SyncDimensionsS2C::decode,
                SyncDimensionsS2C::handle);
        register(
                UpdateDimensionsS2C.class,
                UpdateDimensionsS2C::encode,
                UpdateDimensionsS2C::decode,
                UpdateDimensionsS2C::handle);
        register(
                SyncCapLightLevelPacketS2C.class,
                SyncCapLightLevelPacketS2C::encode,
                SyncCapLightLevelPacketS2C::decode,
                SyncCapLightLevelPacketS2C::handle);
        register(
                SyncCapVariantPacketS2C.class,
                SyncCapVariantPacketS2C::encode,
                SyncCapVariantPacketS2C::decode,
                SyncCapVariantPacketS2C::handle);
        register(
                TriggerSyncCapVariantPacketC2S.class,
                TriggerSyncCapVariantPacketC2S::encode,
                TriggerSyncCapVariantPacketC2S::decode,
                TriggerSyncCapVariantPacketC2S::handle);
        register(
                TriggerSyncCapLightPacketC2S.class,
                TriggerSyncCapLightPacketC2S::encode,
                TriggerSyncCapLightPacketC2S::decode,
                TriggerSyncCapLightPacketC2S::handle);
        register(
                SyncTARDISCapPacketS2C.class,
                SyncTARDISCapPacketS2C::encode,
                SyncTARDISCapPacketS2C::decode,
                SyncTARDISCapPacketS2C::handle);
        register(
                TriggerSyncCapVariantPacketC2S.class,
                TriggerSyncCapVariantPacketC2S::encode,
                TriggerSyncCapVariantPacketC2S::decode,
                TriggerSyncCapVariantPacketC2S::handle);
        register(
                TriggerSyncExteriorVariantPacketC2S.class,
                TriggerSyncExteriorVariantPacketC2S::encode,
                TriggerSyncExteriorVariantPacketC2S::decode,
                TriggerSyncExteriorVariantPacketC2S::handle);
        register(
                TriggerSyncCapPacketC2S.class,
                TriggerSyncCapPacketC2S::encode,
                TriggerSyncCapPacketC2S::decode,
                TriggerSyncCapPacketC2S::handle);

        register(
                SyncViewedTARDISS2C.class,
                SyncViewedTARDISS2C::encode,
                SyncViewedTARDISS2C::decode,
                SyncViewedTARDISS2C::handle);

        register(
                StopViewingExteriorC2S.class,
                StopViewingExteriorC2S::encode,
                StopViewingExteriorC2S::decode,
                StopViewingExteriorC2S::handle);

        register(
                ExteriorStatePacket.class,
                ExteriorStatePacket::encode,
                ExteriorStatePacket::decode,
                ExteriorStatePacket::handle);
        // BOTI

        // register(PortalSyncPacket.class, PortalSyncPacket::encode,
        // PortalSyncPacket::decode, PortalSyncPacket::handle);
        // register(PortalChunkRequestPacket.class, PortalChunkRequestPacket::encode,
        // PortalChunkRequestPacket::decode, PortalChunkRequestPacket::handle);
        // register(PortalChunkDataPacket.class, PortalChunkDataPacket::encode,
        // PortalChunkDataPacket::decode, PortalChunkDataPacket::handle);

        INSTANCE.registerMessage(
                id(),
                PortalSyncPacketS2C.class,
                PortalSyncPacketS2C::encode,
                PortalSyncPacketS2C::decode,
                PortalSyncPacketS2C::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(
                id(),
                PortalChunkRequestPacketC2S.class,
                PortalChunkRequestPacketC2S::encode,
                PortalChunkRequestPacketC2S::decode,
                PortalChunkRequestPacketC2S::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        INSTANCE.registerMessage(
                id(),
                PortalChunkDataPacketS2C.class,
                PortalChunkDataPacketS2C::encode,
                PortalChunkDataPacketS2C::decode,
                PortalChunkDataPacketS2C::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        System.out.println("Network packets registered: Sync=0, Request=1, Data=2");
    }

    public static void sendNear(ResourceKey<Level> level, Vec3i pos, double range, Object message) {
        INSTANCE.send(
                PacketDistributor.NEAR.with(
                        () -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), range, level)),
                message);
    }

    public static void sendPacketToAll(Object message) {
        Networking.INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendPacketToDimension(ResourceKey<Level> level, Object mes) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> level), mes);
    }

    public static void sendPacketToDimension(Object msg, Level level) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(level::dimension), msg);
    }

    public static void sendTo(ServerPlayer player, Object mes) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), mes);
    }

    public static void sendToClient(Player player, Object message) {
        INSTANCE.sendTo(message, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToServer(Object mes) {
        if (net.minecraft.client.Minecraft.getInstance().getConnection() != null) INSTANCE.sendToServer(mes);
    }

    public static void sendToTracking(BlockEntity tile, Object mes) {
        if (tile.getLevel() instanceof ServerLevel)
            INSTANCE.send(
                    PacketDistributor.TRACKING_CHUNK.with(() -> tile.getLevel().getChunkAt(tile.getBlockPos())), mes);
    }

    public static void sendToTracking(Entity e, Object mes) {
        if (e.level() instanceof ServerLevel)
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), mes);
    }

    public static void sendToTracking(LevelChunk chunk, Object mes) {
        if (chunk.getLevel() instanceof ServerLevel)
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), mes);
    }
}
