/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class FastReturnControl extends AbstractControl {
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return SoundEvents.NOTE_BLOCK_BIT.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
		itardisLevel.GetNavigationalData().setDestination(itardisLevel.GetNavigationalData().GetExteriorLocation());
		if (entity instanceof Player player)
			player.displayClientMessage(Component.literal("Destination set to Current Location: "
					+ itardisLevel.GetNavigationalData().getDestination().ReadableString()), true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		itardisLevel.GetNavigationalData().setDestination(itardisLevel.GetNavigationalData().GetPreviousLocation());

		player.displayClientMessage(
				Component.literal(
						"Destination set to: " + itardisLevel.GetNavigationalData().getDestination().ReadableString()),
				true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("fast_return");
	}
}
