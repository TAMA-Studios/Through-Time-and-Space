/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.packets;

import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;
import com.code.tama.triggerapi.networking.ImAPacket;

/**
 * Two modes, distinguished by whether a UUID is present:
 *
 * PREPARE — new SeamlessPreparePacket(uuid) Sent when a gather starts. Opens
 * the staging buffer and sets expectingSeamlessRespawn so the mixin knows to
 * HOLD the next ClientboundRespawnPacket until the COMMIT arrives.
 *
 * COMMIT — new SeamlessPreparePacket() Sent immediately before changeDimension
 * on the server. Sets pending=true and replays the held respawn packet if it
 * already arrived.
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
	// Handler — runs on the main client thread via enqueueWork
	// -------------------------------------------------------------------------

	public static void handle(SeamlessPreparePacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
		NetworkEvent.Context ctx = ctxSupplier.get();
		ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			if (msg.teleportId != null) {
				// PREPARE: open staging buffer and arm the hold.
				ClientSeamlessTeleportState.openStagingBuffer(msg.teleportId);
				ClientSeamlessTeleportState.setExpectingSeamlessRespawn();
			} else {
				// COMMIT: arm the pending flag and replay held packet if needed.
				// Guard against double-fire.
				if (!ClientSeamlessTeleportState.isSeamlessPending()) {
					ClientSeamlessTeleportState.setPending();
					ClientSeamlessTeleportState.clearExpectingSeamlessRespawn();
					// If the respawn packet already arrived and was held, replay it now.
					// replayHeldRespawnIfAny calls pushSuppression itself before replaying.
					ClientSeamlessTeleportState.replayHeldRespawnIfAny();
				}
			}
		}));
		ctx.setPacketHandled(true);
	}
}