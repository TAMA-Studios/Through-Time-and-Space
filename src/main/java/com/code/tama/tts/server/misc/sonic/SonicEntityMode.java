package com.code.tama.tts.server.misc.sonic;

import com.code.tama.tts.server.misc.ClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class SonicEntityMode extends SonicMode{

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public void onUse(Player player, BlockState usedOn, BlockPos usedPos) {
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
