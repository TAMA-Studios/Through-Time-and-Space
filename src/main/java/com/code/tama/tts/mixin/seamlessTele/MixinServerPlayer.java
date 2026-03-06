/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import com.code.tama.triggerapi.boti.packets.SeamlessPreparePacket;
import com.code.tama.triggerapi.boti.teleporting.SeamlessTeleportState;
import com.code.tama.tts.server.networking.Networking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {

    @Unique
    private static final Logger LOGGER = LogManager.getLogger("TTS$SeamlessTele#ServerPlayer");

    @Inject(
            method = "changeDimension(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraftforge/common/util/ITeleporter;)Lnet/minecraft/world/entity/Entity;",
            at = @At("HEAD"),
            remap = false
    )
    private void tts_seamless$beforeChangeDimension(ServerLevel destLevel,
                                                     ITeleporter teleporter,
                                                     CallbackInfoReturnable<Entity> cir) {
        ServerPlayer self = (ServerPlayer) (Object) this;
        boolean pending = SeamlessTeleportState.isPending(self);
        LOGGER.info("[SMLS] changeDimension HEAD — isPending={}", pending);
        if (!pending) return;

        LOGGER.info("[SMLS] Sending SeamlessPreparePacket (COMMIT) now...");
        Networking.sendToPlayer(self, new SeamlessPreparePacket());
        // Force flush so it goes out before the respawn packet
        self.connection.connection.channel().flush();
        LOGGER.info("[SMLS] SeamlessPreparePacket sent and channel flushed.");
    }
}