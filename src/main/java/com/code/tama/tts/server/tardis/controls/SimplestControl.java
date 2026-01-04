/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import java.util.function.Consumer;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class SimplestControl extends AbstractControl {
	private final Consumer<ITARDISLevel> onInteract;
	private final Consumer<ITARDISLevel> onInteractLeft;
	private final String name;

	public SimplestControl(String name, Consumer<ITARDISLevel> onInteract) {
		this.name = name;
		this.onInteract = onInteract;
		this.onInteractLeft = onInteract;
	}

	public SimplestControl(String name, Consumer<ITARDISLevel> onInteract, Consumer<ITARDISLevel> onInteractLeft) {
		this.name = name;
		this.onInteract = onInteract;
		this.onInteractLeft = onInteractLeft;
	}

	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return TTSSounds.BUTTON_CLICK_01.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
		this.onInteractLeft.accept(itardisLevel);
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		this.onInteract.accept(itardisLevel);

		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL(this.name);
	}
}
