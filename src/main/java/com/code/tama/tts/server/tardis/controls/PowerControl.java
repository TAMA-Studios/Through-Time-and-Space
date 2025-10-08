/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PowerControl extends AbstractControl {
    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.NOTE_BLOCK_BIT.get();
    }

    @Override
    public String GetName() {
        return "power_control";
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return SoundEvents.NOTE_BLOCK_BIT.get();
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        itardisLevel.GetData().SetPowered(!itardisLevel.GetData().isPowered());
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        itardisLevel.GetData().SetPowered(!itardisLevel.GetData().isPowered());
        return InteractionResult.SUCCESS;
    }
}
