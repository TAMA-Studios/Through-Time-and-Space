/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class HelmicRegulatorControl extends AbstractControl {
	private static boolean lastState = false;
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		// lastState = !lastState;
		// return lastState ? TTSSounds.BUTTON_CLICK_01.get() :
		// TTSSounds.KEYBOARD_PRESS_01.get();
		return SoundEvents.CROSSBOW_SHOOT;
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
		itardisLevel.GetData().getControlData()
				.setHelmicRegulator(Math.min(itardisLevel.GetData().getControlData().getHelmicRegulator() + 1, 10));
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		itardisLevel.GetData().getControlData()
				.setHelmicRegulator(Math.max(itardisLevel.GetData().getControlData().getHelmicRegulator() - 1, 0));
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("helmic_regulator");
	}
}
