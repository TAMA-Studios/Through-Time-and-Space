/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.mavity;

import com.code.tama.triggerapi.helpers.GravityHelper;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGravityMixin extends Entity implements Attackable, net.minecraftforge.common.extensions.IForgeLivingEntity {

	public LivingEntityGravityMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
	private double injected(double d0) {
		return (double) GravityHelper.getGravity(this.level());
	}

//	@ModifyConstant(
//			method = "travel",
//			constant = @Constant(doubleValue = 0.08D)
//	)	private double injected(double d0) {
//		return d0 * (double) GravityHelper.getGravity(this.level());
//	}

//	@Inject(method = "travel", at = @At("HEAD"))
//	private void tts$applyCustomGravity(Vec3 travelVector, CallbackInfo ci) {
//		LivingEntity self = (LivingEntity) (Object) this;
//
//		// Skip gravity if flagged no-gravity
//		if (self.isNoGravity())
//			return;
//
//		// Skip creative/spectator flight
//		if (self instanceof Player player) {
//			if (player.getAbilities().flying || player.isSpectator())
//				return;
//		}
//
//		// Optional: skip when submerged or grounded
//		if (self.isInWater() || self.isInLava() || self.onGround())
//			return;
//
//		double gravity = GravityHelper.getGravity(self.level());
//		self.setDeltaMovement(self.getDeltaMovement().add(0.0D, -gravity, 0.0D));
//	}
}
