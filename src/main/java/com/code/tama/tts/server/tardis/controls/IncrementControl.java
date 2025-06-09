/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.client.TTSSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class IncrementControl extends AbstractControl {
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
            itardisLevel.SetIncrement(itardisLevel.GetNextIncrement());
        player.displayClientMessage(Component.literal("Coordinate Increment = " + Integer.toString(itardisLevel.GetIncrement())), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
        itardisLevel.SetIncrement(itardisLevel.GetPreviousIncrement());
        if(entity instanceof Player player) player.displayClientMessage(Component.literal("Coordinate Increment = " + Integer.toString(itardisLevel.GetIncrement())), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return TTSSounds.BUTTON_CLICK_01.get();
    }

    @Override
    public String GetName() {
        return "increment_control";
    }

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }
}
