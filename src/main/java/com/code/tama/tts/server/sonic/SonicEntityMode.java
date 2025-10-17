/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.sonic;

import com.code.tama.tts.server.misc.ClientUtil;

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
		Player player = context.getPlayer();
		assert player != null;
		if (player.level().isClientSide())
			return;

		Entity lookingAtEntity = ClientUtil.GetEntityClientIsLookingAt();

		if (lookingAtEntity instanceof Creeper creeper) {
			creeper.ignite();
			return;
		}

		if (lookingAtEntity instanceof Skeleton skeleton) {
			ItemEntity item = EntityType.ITEM.create(skeleton.level());
			assert item != null;
			skeleton.level().addFreshEntity(item);
			item.setPos(skeleton.getX(), skeleton.getY(), skeleton.getZ());
			item.setItem(Items.BONE.getDefaultInstance());
			skeleton.kill();
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
