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
 * This packet has two modes, distinguished by whether a UUID is present:
 *
 *  PREPARE  new SeamlessPreparePacket(uuid)
 *    Sent at the start of a gather. Opens the client staging buffer under the
 *    given UUID so incoming SeamlessChunkPreloadPacketS2C batches are accepted.
 *
 *  COMMIT   new SeamlessPreparePacket()
 *    Sent immediately before changeDimension. Arms the handleRespawn mixin.
 *    In the no-preload path this arrives before any geometry, so the client
 *    tick mixin holds the respawn packet until geometry lands (or 5 second timeout).
 *    In the preloaded path geometry is already staged and the switch is instant.
 */
public class SeamlessPreparePacket implements ImAPacket {

    public static final ResourceLocation ID = new ResourceLocation("tts", "seamless_prepare");

    /** null = COMMIT, non-null = PREPARE */
    private final UUID teleportId;

    /** PREPARE constructor — opens staging buffer. */
    public SeamlessPreparePacket(UUID teleportId) {
        this.teleportId = teleportId;
    }

    /** COMMIT constructor — arms the pending flag. */
    public SeamlessPreparePacket() {
        this.teleportId = null;
    }

    // -------------------------------------------------------------------------
    // Codec
    // -------------------------------------------------------------------------

    public static void encode(SeamlessPreparePacket msg, FriendlyByteBuf buf) {
        boolean hasId = msg.teleportId != null;
        buf.writeBoolean(hasId);
        if (hasId) {
            buf.writeLong(msg.teleportId.getMostSignificantBits());
            buf.writeLong(msg.teleportId.getLeastSignificantBits());
        }
    }

    public static SeamlessPreparePacket decode(FriendlyByteBuf buf) {
        boolean hasId = buf.readBoolean();
        if (hasId) {
            return new SeamlessPreparePacket(new UUID(buf.readLong(), buf.readLong()));
        }
        return new SeamlessPreparePacket();
    }

    // -------------------------------------------------------------------------
    // Handler
    // -------------------------------------------------------------------------

    public static void handle(SeamlessPreparePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() ->
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if (msg.teleportId != null) {
                    // PREPARE: open the staging buffer under this UUID.
                    ClientSeamlessTeleportState.openStagingBuffer(msg.teleportId);
                } else {
                    // COMMIT: arm the mixin. Guard against double-fire.
                    if (!ClientSeamlessTeleportState.isSeamlessPending()) {
                        ClientSeamlessTeleportState.setPending();
                    }
                }
            })
        );
        ctx.setPacketHandled(true);
    }
}