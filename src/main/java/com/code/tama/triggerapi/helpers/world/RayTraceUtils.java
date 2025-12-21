/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.world;

import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;

public class RayTraceUtils {
	public static BlockHitResult getLookingAtBlock(double reachDistance) {
		Minecraft mc = Minecraft.getInstance();

		if (mc.player == null || mc.level == null) {
			return null;
		}

		Vec3 eyePosition = mc.player.getEyePosition(1.0F);
		Vec3 lookVector = mc.player.getViewVector(1.0F);
		Vec3 reachVector = eyePosition.add(lookVector.scale(reachDistance));

		// Proper ray trace using ClipContext
		BlockHitResult rayTrace = mc.level.clip(new ClipContext(eyePosition, // start
				reachVector, // end
				ClipContext.Block.OUTLINE, // what blocks to hit
				ClipContext.Fluid.NONE, // ignore fluids
				mc.player // the entity doing the tracing
		));

		if (rayTrace.getType() == HitResult.Type.BLOCK) {
			return rayTrace;
		}

		return null;
	}

	public static EntityHitResult rayTraceEntity(Entity source, double maxDistance) {
		Vec3 start = source.getEyePosition(1.0F);
		Vec3 look = source.getViewVector(1.0F);
		Vec3 end = start.add(look.scale(maxDistance));
		return source.level()
				.getEntities(source, source.getBoundingBox().expandTowards(look.scale(maxDistance)).inflate(1.0),
						e -> true)
				.stream().map(e -> new EntityHitResult(e, start.add(look.scale(start.distanceTo(e.getPosition(1.0F))))))
				.filter(hit -> hit.getEntity().isPickable())
				.min((a, b) -> (int) (start.distanceToSqr(a.getLocation()) - start.distanceToSqr(b.getLocation())))
				.orElse(null);
	}

	public static HitResult rayTraceFromEntity(Entity entity, double maxDistance, boolean hitFluids) {
		Vec3 eyePos = entity.getEyePosition(1.0F);
		Vec3 lookVec = entity.getViewVector(1.0F);
		Vec3 endPos = eyePos.add(lookVec.x * maxDistance, lookVec.y * maxDistance, lookVec.z * maxDistance);
		return entity.level().clip(new ClipContext(eyePos, endPos, ClipContext.Block.OUTLINE,
				hitFluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, entity));
	}

	public static EntityHitResult getPlayerPOVHitResult(Player player) {
		float playerRotX = player.getXRot();
		float playerRotY = player.getYRot();
		Vec3 startPos = player.getEyePosition();
		float f2 = Mth.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		float f3 = Mth.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
		float f4 = -Mth.cos(-playerRotX * ((float) Math.PI / 180F));
		float additionY = Mth.sin(-playerRotX * ((float) Math.PI / 180F));
		float additionX = f3 * f4;
		float additionZ = f2 * f4;
		double d0 = 250;
		Vec3 endVec = startPos.add((double) additionX * d0, (double) additionY * d0, (double) additionZ * d0);
		AABB startEndBox = new AABB(startPos, endVec);
		Entity entity = null;
		for (Entity entity1 : player.level().getEntities(player, startEndBox, (val) -> true)) {
			AABB aabb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
			Optional<Vec3> optional = aabb.clip(startPos, endVec);
			if (aabb.contains(startPos)) {
				if (d0 >= 0.0D) {
					entity = entity1;
					startPos = optional.orElse(startPos);
					d0 = 0.0D;
				}
			} else if (optional.isPresent()) {
				Vec3 vec31 = optional.get();
				double d1 = startPos.distanceToSqr(vec31);
				if (d1 < d0 || d0 == 0.0D) {
					if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.canRiderInteract()) {
						if (d0 == 0.0D) {
							entity = entity1;
							startPos = vec31;
						}
					} else {
						entity = entity1;
						startPos = vec31;
						d0 = d1;
					}
				}
			}
		}

		return (entity == null) ? null : new EntityHitResult(entity);
	}
}
