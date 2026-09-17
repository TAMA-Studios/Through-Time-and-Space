/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class ThrottleControl extends AbstractControl {
	ITARDISLevel itardisLevel;

	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return this.itardisLevel != null
				? itardisLevel.GetFlightData().isInFlight() ? TTSSounds.THROTTLE_ON.get() : TTSSounds.THROTTLE_OFF.get()
				: TTSSounds.THROTTLE_OFF.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player) {
		// Controls run on both client (prediction) and server (authoritative).
		// Only the server should ever actually trigger a stage transition -
		// Dematerialize()/Rematerialize() already bail on the client too, but
		// bailing here as well avoids needlessly touching animation state on the
		// client copy of the capability.
		if (itardisLevel.GetLevel().isClientSide())
			return InteractionResult.SUCCESS;

		this.itardisLevel = itardisLevel;
		this.SetNeedsUpdate(true);
		this.SetAnimationState(0.0f);

		// Rematerialize() itself now guards on isInFlight(), but checking here too
		// means we don't even bother posting the Land event for a TARDIS that
		// isn't flying.
		if (itardisLevel.GetFlightData().isInFlight()) {
			itardisLevel.Rematerialize();
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		if (itardisLevel.GetLevel().isClientSide())
			return InteractionResult.SUCCESS;

		this.itardisLevel = itardisLevel;
		this.SetAnimationState(1.0f);

		if (!itardisLevel.GetFlightData().IsTakingOff() && !itardisLevel.GetFlightData().isInFlight()) {
			// Dematerialize() (via PhysicalStateManager) now owns playing the
			// takeoff sound itself - don't call .Play()/.SetFinished() on it here,
			// that bypassed the sound-completion tracking PhysicalStateManager
			// relies on to know when to actually leave the Taking Off stage.
			itardisLevel.Dematerialize();
			itardisLevel.UpdateClient(DataUpdateValues.FLIGHT);
		}

		this.SetNeedsUpdate(true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("throttle");
	}
}