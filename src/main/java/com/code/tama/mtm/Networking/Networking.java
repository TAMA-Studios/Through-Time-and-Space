package com.code.tama.mtm.Networking;

import com.code.tama.mtm.Networking.Packets.Dimensions.*;
import com.code.tama.mtm.Networking.Packets.Entities.BlowUpCreeperPacket;
import com.code.tama.mtm.Networking.Packets.Entities.ControlClickedPacket;
import com.code.tama.mtm.Networking.Packets.Entities.ControlHitPacket;
import com.code.tama.mtm.Networking.Packets.Entities.SyncButtonAnimationSetPacket;
import com.code.tama.mtm.Networking.Packets.Exterior.SetExteriorAlpha;
import com.code.tama.mtm.Networking.Packets.Exterior.SyncExteriorVariantPacket;
import com.code.tama.mtm.Networking.Packets.Exterior.SyncTransparencyPacket;
import com.code.tama.mtm.Networking.Packets.Portal.PortalChunkDataPacket;
import com.code.tama.mtm.Networking.Packets.Portal.PortalChunkRequestPacket;
import com.code.tama.mtm.Networking.Packets.Portal.PortalSyncPacket;
import com.code.tama.mtm.TriggerAPI.Dimension.UpdateDimensionsPacket;
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

import static com.code.tama.mtm.mtm.MODID;

public class Networking {

    public static final String NET_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "main"), () -> NET_VERSION, NET_VERSION::equals, NET_VERSION::equals);

    public static int ID = 0;

    public static void registerPackets(){
        // Entity Packets
        register(ControlClickedPacket.class, ControlClickedPacket::encode, ControlClickedPacket::decode, ControlClickedPacket::handle);
        register(ControlHitPacket.class, ControlHitPacket::encode, ControlHitPacket::decode, ControlHitPacket::handle);
        register(BlowUpCreeperPacket.class, BlowUpCreeperPacket::encode, BlowUpCreeperPacket::decode, BlowUpCreeperPacket::handle);
        register(SyncButtonAnimationSetPacket.class, SyncButtonAnimationSetPacket::encode, SyncButtonAnimationSetPacket::decode, SyncButtonAnimationSetPacket::handle);


        // Exterior Data
        register(SyncExteriorVariantPacket.class, SyncExteriorVariantPacket::encode, SyncExteriorVariantPacket::decode, SyncExteriorVariantPacket::handle);
        register(SetExteriorAlpha.class, SetExteriorAlpha::encode, SetExteriorAlpha::decode, SetExteriorAlpha::handle);
        register(SyncTransparencyPacket.class, SyncTransparencyPacket::encode, SyncTransparencyPacket::decode, SyncTransparencyPacket::handle);


        // Cap Data
        register(SyncDimensions.class, SyncDimensions::encode, SyncDimensions::decode, SyncDimensions::handle);
        register(UpdateDimensionsPacket.class, UpdateDimensionsPacket::encode, UpdateDimensionsPacket::decode, UpdateDimensionsPacket::handle);
        register(SyncCapLightLevelPacket.class, SyncCapLightLevelPacket::encode, SyncCapLightLevelPacket::decode, SyncCapLightLevelPacket::handle);
        register(SyncCapVariantPacket.class, SyncCapVariantPacket::encode, SyncCapVariantPacket::decode, SyncCapVariantPacket::handle);
        register(TriggerSyncCapVariantPacket.class, TriggerSyncCapVariantPacket::encode, TriggerSyncCapVariantPacket::decode, TriggerSyncCapVariantPacket::handle);
        register(TriggerSyncCapLightPacket.class, TriggerSyncCapLightPacket::encode, TriggerSyncCapLightPacket::decode, TriggerSyncCapLightPacket::handle);
        register(SyncTARDISCapPacket.class, SyncTARDISCapPacket::encode, SyncTARDISCapPacket::decode, SyncTARDISCapPacket::handle);
        register(TriggerSyncCapVariantPacket.class, TriggerSyncCapVariantPacket::encode, TriggerSyncCapVariantPacket::decode, TriggerSyncCapVariantPacket::handle);

        // BOTI

//        register(PortalSyncPacket.class, PortalSyncPacket::encode, PortalSyncPacket::decode, PortalSyncPacket::handle);
//        register(PortalChunkRequestPacket.class, PortalChunkRequestPacket::encode, PortalChunkRequestPacket::decode, PortalChunkRequestPacket::handle);
//        register(PortalChunkDataPacket.class, PortalChunkDataPacket::encode, PortalChunkDataPacket::decode, PortalChunkDataPacket::handle);

        INSTANCE.registerMessage(id(), PortalSyncPacket.class,
                PortalSyncPacket::encode,
                PortalSyncPacket::decode,
                PortalSyncPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id(), PortalChunkRequestPacket.class,
                PortalChunkRequestPacket::encode,
                PortalChunkRequestPacket::decode,
                PortalChunkRequestPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        INSTANCE.registerMessage(id(), PortalChunkDataPacket.class,
                PortalChunkDataPacket::encode,
                PortalChunkDataPacket::decode,
                PortalChunkDataPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        System.out.println("Network packets registered: Sync=0, Request=1, Data=2");
    }

    public static <T> void register(Class<T> clazz, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> handler){
        INSTANCE.registerMessage(id(), clazz, encoder, decoder, handler);
    }

    public static void sendNear(ResourceKey<Level> level, Vec3i pos, double range, Object message){
        INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                pos.getX(), pos.getY(), pos.getZ(), range,  level
        )), message);
    }

    public static void sendPacketToAll(Object message){
        Networking.INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendPacketToDimension(ResourceKey<Level> level, Object mes){
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> level), mes);
    }

    public static void sendToTracking(Entity e, Object mes) {
        if(e.level() instanceof ServerLevel)
            INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> e), mes);
    }

    public static void sendToTracking(BlockEntity tile, Object mes){
        if(tile.getLevel() instanceof ServerLevel)
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> tile.getLevel().getChunkAt(tile.getBlockPos())), mes);
    }

    public static void sendToTracking(LevelChunk chunk, Object mes){
        if(chunk.getLevel() instanceof ServerLevel)
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), mes);
    }

    public static void sendTo(ServerPlayer player, Object mes){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), mes);
    }

    public static void sendToServer(Object mes) {
        INSTANCE.sendToServer(mes);
    }

    public static void sendToClient(Player player, Object message) {
        INSTANCE.sendTo(message, ((ServerPlayer) player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static int id(){
        return ID++;
    }
}
