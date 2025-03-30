package com.code.tama.mtm.server.tardis.controls;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PowerControl extends AbstractControl {
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        itardisLevel.SetPowered(!itardisLevel.IsPoweredOn());
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        itardisLevel.SetPowered(!itardisLevel.IsPoweredOn());
        return InteractionResult.SUCCESS;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return SoundEvents.NOTE_BLOCK_BIT.get();
    }

    @Override
    public String GetName() {
        return "power_control";
    }

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.NOTE_BLOCK_BIT.get();
    }
}