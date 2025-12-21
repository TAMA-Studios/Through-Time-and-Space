/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.mavity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import com.code.tama.triggerapi.helpers.GravityHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityGravityMixin extends Entity
		implements
			Attackable,
			net.minecraftforge.common.extensions.IForgeLivingEntity {

	public LivingEntityGravityMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}

	@ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
	private double injected(double d0) {
		return GravityHelper.getGravity(this.level());
	}
}
