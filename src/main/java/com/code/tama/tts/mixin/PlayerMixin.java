/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import com.code.tama.tts.server.capabilities.Capabilities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "travel(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"), cancellable = true)
    private void travel(Vec3 vec3, CallbackInfo info) {
        Capabilities.getCap(
                        Capabilities.TARDIS_LEVEL_CAPABILITY, TTS$GetPlayer().level())
                .ifPresent(tardis -> {
                    if (!tardis.GetData().IsViewingTARDIS(TTS$GetPlayer().getUUID())) return;
                    TTS$GetPlayer().setTicksFrozen(1);
                    TTS$GetPlayer().setDeltaMovement(Vec3.ZERO);
                    TTS$GetPlayer().fallDistance = 0.0F;
                    info.cancel();
                });

        // TODO: mavity
    }

    @Unique private Player TTS$GetPlayer() {
        return ((Player) (Object) this);
    }
}
