/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import com.code.tama.tts.server.misc.ClientUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
    public void onUse(UseOnContext context) {
        Player player = context.getPlayer();
        if (player.level().isClientSide()) return;

        Entity lookingAtEntity = ClientUtil.GetEntityClientIsLookingAt();

        if (lookingAtEntity instanceof Creeper creeper) {
            creeper.ignite();
            return;
        }

        if (lookingAtEntity instanceof Skeleton skeleton) {
            skeleton.kill();
            return;
        }
        if (lookingAtEntity instanceof ZombieVillager zombieVillager) {
            zombieVillager.convertTo(EntityType.VILLAGER, true);
            return;
        }
    }
}
