/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class X_Control extends AbstractControl {
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        itardisLevel.SetDestination(
                itardisLevel.GetDestination().AddX(
                        player.isCrouching() ? -itardisLevel.GetIncrement() : itardisLevel.GetIncrement()));
        player.displayClientMessage(Component.literal("Current Destination = " + itardisLevel.GetDestination().ReadableString()), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
        itardisLevel.SetDestination(
                itardisLevel.GetDestination().AddX(
                        entity.isCrouching() ? itardisLevel.GetIncrement() : -itardisLevel.GetIncrement()));
        if(entity instanceof Player player) player.displayClientMessage(Component.literal("Current Destination = " + itardisLevel.GetDestination().ReadableString()), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public String GetName() {
        return "x_control";
    }

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return TTSSounds.BUTTON_CLICK_01.get();
    }

}