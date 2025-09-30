/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class ThrottleControl extends AbstractControl {
    ITARDISLevel itardisLevel;

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }

    @Override
    public String GetName() {
        return "throttle";
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return this.itardisLevel != null
                ? itardisLevel.IsInFlight() ? TTSSounds.THROTTLE_ON.get() : TTSSounds.THROTTLE_OFF.get()
                : TTSSounds.THROTTLE_OFF.get();
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        this.itardisLevel = itardisLevel;
        this.SetNeedsUpdate(true);
        this.SetAnimationState(0.0f);
        if (itardisLevel.IsInFlight()) {
            itardisLevel.GetFlightScheme().GetLanding().Play(itardisLevel.GetLevel(), player.blockPosition());
            itardisLevel.Rematerialize();
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        this.itardisLevel = itardisLevel;
        this.SetAnimationState(1.0f);

        if (!itardisLevel.IsInFlight() && !itardisLevel.ShouldPlayRotorAnimation()) {
            itardisLevel.GetFlightScheme().GetTakeoff().SetFinished(true);
            itardisLevel.Dematerialize();
            itardisLevel.GetFlightScheme().GetTakeoff().Play(itardisLevel.GetLevel(), player.blockPosition());
            itardisLevel.UpdateClient();
        }
        this.SetNeedsUpdate(true);
        return InteractionResult.SUCCESS;
    }
}
