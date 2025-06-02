package com.code.tama.triggerapi;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EntityUtils {
    public static void knockback(Entity entity, float strength, Vec3 direction) {
        if (entity == null) return;
        Vec3 normalized = direction.normalize();
        entity.setDeltaMovement(
                entity.getDeltaMovement()
                        .add(normalized.x * strength, 0.2 * strength, normalized.z * strength));
        entity.hurtMarked = true;
    }

    public static void setHealth(LivingEntity entity, float health) {
        if (entity == null) return;
        entity.setHealth(Math.min(health, entity.getMaxHealth()));
    }

    public static void applyEffect(LivingEntity entity, MobEffect effect, int Duration, int Potency) {
        if (entity == null || effect == null) return;
        entity.addEffect(new MobEffectInstance(effect, Duration, Potency));
    }

    public static boolean isWithinDistance(Entity entity, Vec3 point, double distance) {
        return entity != null && entity.position().distanceTo(point) <= distance;
    }
}