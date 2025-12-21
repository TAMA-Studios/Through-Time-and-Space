/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.sonic;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import com.code.tama.triggerapi.helpers.world.RayTraceUtils;

public class SonicEntityMode extends SonicMode {

	@Override
	public Item getIcon() {
		return Items.CREEPER_HEAD;
	}

	@Override
	public String getName() {
		return "entity_mode";
	}

	@Override
	public void onUse(UseOnContext context) {
		if (context.getLevel().isClientSide() || context.getHand().equals(InteractionHand.OFF_HAND))
			return;
		if (context.getLevel().isClientSide())
			return;
		Player player = context.getPlayer();
		Level level = context.getLevel();

		assert player != null;
		EntityHitResult result = RayTraceUtils.getPlayerPOVHitResult(player);
		if (result.getEntity() == null)
			return;
		Entity lookingAtEntity = result.getEntity();

		if (lookingAtEntity instanceof Creeper creeper) {
			creeper.ignite();
			return;
		}

		if (lookingAtEntity instanceof Skeleton skeleton) {
			ItemEntity item = EntityType.ITEM.create(skeleton.level());
			assert item != null;
			level.addFreshEntity(item);
			item.setPos(skeleton.getX(), skeleton.getY(), skeleton.getZ());
			item.setItem(Items.BONE.getDefaultInstance());
			skeleton.setRemoved(Entity.RemovalReason.KILLED);
			return;
		}
		if (lookingAtEntity instanceof ZombieVillager zombieVillager) {
			ExperienceOrb exp = EntityType.EXPERIENCE_ORB.create(zombieVillager.level());
			assert exp != null;
			zombieVillager.level().addFreshEntity(exp);
			exp.setPos(zombieVillager.getX(), zombieVillager.getY(), zombieVillager.getZ());
			exp.value = 5;
			zombieVillager.convertTo(EntityType.VILLAGER, true);
			return;
		}
	}
}
