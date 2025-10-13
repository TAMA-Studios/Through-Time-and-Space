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

public class IncrementControl extends AbstractControl {
    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }

    @Override
    public String GetName() {
        return "increment_control";
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return TTSSounds.BUTTON_CLICK_01.get();
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
        itardisLevel
                .GetNavigationalData()
                .setIncrement(itardisLevel.GetNavigationalData().GetPreviousIncrement());
        if (entity instanceof Player player)
            player.displayClientMessage(
                    Component.literal("Coordinate Increment = "
                            + itardisLevel.GetNavigationalData().getIncrement()),
                    true);
        this.SetAnimationState((float) itardisLevel.GetNavigationalData().getIncrement() / 10000);
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        itardisLevel
                .GetNavigationalData()
                .setIncrement(itardisLevel.GetNavigationalData().GetNextIncrement());
        player.displayClientMessage(
                Component.literal("Coordinate Increment = "
                        + itardisLevel.GetNavigationalData().getIncrement()),
                true);
        this.SetAnimationState((float) itardisLevel.GetNavigationalData().getIncrement() / 10000);
        return InteractionResult.SUCCESS;
    }
}
