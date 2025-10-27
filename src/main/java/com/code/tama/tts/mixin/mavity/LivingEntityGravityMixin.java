/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.mavity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.helpers.GravityHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGravityMixin {

	@Inject(method = "travel", at = @At("HEAD"))
	private void tts$applyCustomGravity(Vec3 travelVector, CallbackInfo ci) {
		LivingEntity self = (LivingEntity) (Object) this;

		// Skip gravity if flagged no-gravity
		if (self.isNoGravity())
			return;

		// Skip creative/spectator flight
		if (self instanceof Player player) {
			if (player.getAbilities().flying || player.isSpectator())
				return;
		}

		// Optional: skip when submerged or grounded
		if (self.isInWater() || self.isInLava() || self.onGround())
			return;

		double gravity = GravityHelper.getGravity(self.level());
		self.setDeltaMovement(self.getDeltaMovement().add(0.0D, -gravity, 0.0D));
	}
}
