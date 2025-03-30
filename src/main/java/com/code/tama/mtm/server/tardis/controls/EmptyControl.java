package com.code.tama.mtm.server.tardis.controls;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.client.MTMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EmptyControl extends AbstractControl {
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        player.playSound(SoundEvents.DISPENSER_FAIL);
        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        player.playSound(SoundEvents.DISPENSER_FAIL);
        return InteractionResult.PASS;
    }

    @Override
    public String GetName() {
        return "empty";
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return MTMSounds.BUTTON_CLICK_01.get();
    }

    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }
}
