/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import com.code.tama.triggerapi.boti.packets.SeamlessPreparePacket;
import com.code.tama.triggerapi.boti.teleporting.SeamlessTeleportState;
import com.code.tama.tts.server.networking.Networking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.ITeleporter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {

	// remap = true so Mixin uses the mapped (Mojmap/MCP) method name.
	// The old descriptor with remap = false required the obfuscated SRG name,
	// which caused the inject to silently miss the method and never fire.
	@Inject(
			method = "changeDimension(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraftforge/common/util/ITeleporter;)Lnet/minecraft/world/entity/Entity;",
			at = @At("HEAD"),
			remap = false  // ITeleporter is a Forge class so the whole signature is non-remappable; keep false but use the correct mapped class names
	)
	private void tts_seamless$beforeChangeDimension(ServerLevel destLevel, ITeleporter teleporter,
													CallbackInfoReturnable<Entity> cir) {
		ServerPlayer self = (ServerPlayer) (Object) this;

		if (SeamlessTeleportState.isPending(self)) {
			// Send the prepare packet BEFORE changeDimension triggers
			// the ClientboundRespawnPacket so the client flag is set in time.
			Networking.sendToPlayer(self, new SeamlessPreparePacket());
		}
	}
}