/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import com.code.tama.triggerapi.universal.UniversalServerOnly;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class EntityUtils {
	public static void applyEffect(LivingEntity entity, MobEffect effect, int Duration, int Potency) {
		if (entity == null || effect == null)
			return;
		entity.addEffect(new MobEffectInstance(effect, Duration, Potency));
	}

	public static Player getPlayerByUUID(UUID uuid) {
		if (UniversalServerOnly.getServer() != null) {
			for (Level world : UniversalServerOnly.getServer().getAllLevels()) {
				Player player = world.getPlayerByUUID(uuid);
				if (player != null) {
					return player;
				}
			}
		}
		return null;
	}

	public static boolean isWithinDistance(Entity entity, Vec3 point, double distance) {
		return entity != null && entity.position().distanceTo(point) <= distance;
	}

	public static void knockback(Entity entity, float strength, Vec3 direction) {
		if (entity == null)
			return;
		Vec3 normalized = direction.normalize();
		entity.setDeltaMovement(
				entity.getDeltaMovement().add(normalized.x * strength, 0.2 * strength, normalized.z * strength));
		entity.hurtMarked = true;
	}

	public static void setHealth(LivingEntity entity, float health) {
		if (entity == null)
			return;
		entity.setHealth(Math.min(health, entity.getMaxHealth()));
	}
}
