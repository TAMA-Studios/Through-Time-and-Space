/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;
import com.code.tama.triggerapi.networking.ImAPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Sent by {@link com.code.tama.triggerapi.boti.teleporting.SeamlessTeleport#prepare}
 * (and by the inline fallback gather) before any geometry batches go out.
 *
 * Tells the client to reset its staging buffer and record the given
 * {@code teleportId} so that incoming {@link com.code.tama.triggerapi.boti.packets.S2C.SeamlessChunkPreloadPacketS2C}
 * packets can be matched against the correct in-flight teleport.
 *
 * This is intentionally separate from {@link SeamlessPreparePacket}:
 *   - {@code SeamlessTeleportPrepareIdPacket} — "here comes geometry for UUID X"
 *     (sent early, before geometry, during prepare())
 *   - {@code SeamlessPreparePacket}            — "the respawn packet is coming NOW,
 *     suppress the loading screen" (sent just before changeDimension)
 */
public class SeamlessTeleportPrepareIdPacket implements ImAPacket {

    public static final ResourceLocation ID = new ResourceLocation("tts", "seamless_prepare_id");

    private final UUID teleportId;

    public SeamlessTeleportPrepareIdPacket(UUID teleportId) {
        this.teleportId = teleportId;
    }

    public static void encode(SeamlessTeleportPrepareIdPacket msg, FriendlyByteBuf buf) {
        buf.writeLong(msg.teleportId.getMostSignificantBits());
        buf.writeLong(msg.teleportId.getLeastSignificantBits());
    }

    public static SeamlessTeleportPrepareIdPacket decode(FriendlyByteBuf buf) {
        return new SeamlessTeleportPrepareIdPacket(new UUID(buf.readLong(), buf.readLong()));
    }

    public static void handle(SeamlessTeleportPrepareIdPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() ->
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                ClientSeamlessTeleportState.openStagingBuffer(msg.teleportId)
            )
        );
        ctx.setPacketHandled(true);
    }
}