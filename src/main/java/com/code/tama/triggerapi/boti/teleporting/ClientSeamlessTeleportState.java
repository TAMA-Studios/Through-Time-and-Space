/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;
import lombok.Getter;
import lombok.Setter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Client-side state for seamless cross-dimension teleports.
 *
 * The only thing the mixin needs to know is whether the next
 * ClientboundRespawnPacket is ours — tracked by {@code pending}.
 *
 * The staging buffer (geometry preload) is tracked separately and is purely
 * cosmetic: geometry arrives asynchronously after the dimension switch and is
 * applied to the portal tile when it lands. It does not block the switch.
 */
@OnlyIn(Dist.CLIENT)
public final class ClientSeamlessTeleportState {

    private static boolean pending = false;

    public static void setPending() {
        pending = true;
    }

    public static boolean isSeamlessPending() {
        return pending;
    }

    public static void clearPending() {
        pending = false;
    }

    @Getter
    @Setter
    private static boolean suppressingLoadingScreen = false;

    @Nullable
    private static volatile UUID currentTeleportId = null;

    private static final Object STAGE_LOCK = new Object();

    @Nullable
    private static List<BotiBlockContainer> stagedContainers = null;

    private static final AtomicInteger stagedTotal    = new AtomicInteger(0);
    private static final AtomicInteger stagedReceived = new AtomicInteger(0);

    /**
     * Opens (or resets) the staging buffer for a new teleport UUID.
     * Called when SeamlessPreparePacket(uuid) arrives.
     */
    public static void openStagingBuffer(UUID teleportId) {
        currentTeleportId = teleportId;
        synchronized (STAGE_LOCK) { stagedContainers = null; }
        stagedTotal.set(0);
        stagedReceived.set(0);
    }

    /**
     * Adds a geometry batch. Drops silently if UUID doesn't match.
     */
    public static void stageContainers(UUID teleportId,
                                        List<BotiBlockContainer> batch,
                                        int index,
                                        int total) {
        if (!teleportId.equals(currentTeleportId)) return;
        synchronized (STAGE_LOCK) {
            if (stagedContainers == null) stagedContainers = new ArrayList<>();
            stagedContainers.addAll(batch);
        }
        stagedTotal.set(total);
        stagedReceived.incrementAndGet();
    }

    public static boolean isStagingComplete() {
        int total = stagedTotal.get();
        return total > 0 && stagedReceived.get() >= total;
    }

    @Nullable
    public static List<BotiBlockContainer> consumeStagedContainers() {
        synchronized (STAGE_LOCK) {
            List<BotiBlockContainer> result = stagedContainers;
            stagedContainers = null;
            return result;
        }
    }
}