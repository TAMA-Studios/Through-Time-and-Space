/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.triggerapi.helpers.MathUtils;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class HelmicRegulatorControl extends AbstractControl {
	private static boolean lastState = false;
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		lastState = !lastState;
		return lastState ? TTSSounds.BUTTON_CLICK_01.get() : TTSSounds.KEYBOARD_PRESS_01.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
		itardisLevel.GetData().getControlData().setHelmicRegulator(MathUtils.clamp(itardisLevel.GetData().getControlData().getHelmicRegulator() + 0.1f, 0.0f, 1.0f));
		player.playSound(GetSuccessSound());
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		itardisLevel.GetData().getControlData().setHelmicRegulator(MathUtils.clamp(itardisLevel.GetData().getControlData().getHelmicRegulator() - 0.1f, 0.0f, 1.0f));
		player.playSound(GetSuccessSound());
		return InteractionResult.PASS;
	}

	@Override
	public String name() {
		return "helmic_regulator";
	}
}
