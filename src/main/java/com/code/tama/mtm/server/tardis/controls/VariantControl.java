package com.code.tama.mtm.server.tardis.controls;

import com.code.tama.mtm.Exteriors;
import com.code.tama.mtm.client.MTMSounds;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class VariantControl extends AbstractControl {
    @Override
    public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
        itardisLevel.SetExteriorVariant(Exteriors.Cycle(itardisLevel.GetExteriorVariant()));
        itardisLevel.UpdateClient();
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
        itardisLevel.SetExteriorVariant(Exteriors.Cycle(itardisLevel.GetExteriorVariant()));
        itardisLevel.UpdateClient();
        return InteractionResult.SUCCESS;
    }

    @Override
    public SoundEvent GetSuccessSound() {
        return MTMSounds.BUTTON_CLICK_01.get();
    }


    @Override
    public SoundEvent GetFailSound() {
        return SoundEvents.DISPENSER_FAIL;
    }

    @Override
    public String GetName() {
        return "variant_control";
    }
}
