/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class ThrottleControl extends AbstractControl {
    ITARDISLevel itardisLevel;
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        this.itardisLevel = itardisLevel;
        this.SetNeedsUpdate(true);
        if(!itardisLevel.IsInFlight()) {
            this.SetAnimationState(1.0f);
            itardisLevel.GetFlightScheme().GetTakeoff().Play(itardisLevel.GetLevel(), new BlockPos(0, 0, 0));
            itardisLevel.Dematerialize();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        this.itardisLevel = itardisLevel;
        this.SetNeedsUpdate(true);
        if(itardisLevel.IsInFlight()) {
            this.SetAnimationState(0.0f);
            itardisLevel.GetFlightScheme().GetLanding().Play(itardisLevel.GetLevel(), new BlockPos(0, 0, 0));
            itardisLevel.Land();
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return this.itardisLevel != null ? itardisLevel.IsInFlight() ? TTSSounds.THROTTLE_OFF.get() : TTSSounds.THROTTLE_OFF.get() : TTSSounds.THROTTLE_ON.get();
    }

    @Override
    public String GetName() {
        return "throttle";
    }

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }
}