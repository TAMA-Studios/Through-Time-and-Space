package com.code.tama.mtm.server.tardis.controls;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.client.MTMSounds;
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
        if(itardisLevel.IsInFlight()) {
            this.SetAnimationState(0.0f);
            itardisLevel.GetFlightScheme().GetLanding().Play(itardisLevel.GetLevel(), new BlockPos(0, 0, 0));
            itardisLevel.Land();
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return this.itardisLevel != null ? itardisLevel.IsInFlight() ? MTMSounds.THROTTLE_OFF.get() : MTMSounds.THROTTLE_OFF.get() : MTMSounds.THROTTLE_ON.get();
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