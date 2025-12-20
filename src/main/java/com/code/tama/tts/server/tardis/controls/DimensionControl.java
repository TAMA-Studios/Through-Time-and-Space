/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.triggerapi.GrammarNazi;
import com.code.tama.triggerapi.helpers.world.WorldHelper;
import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class DimensionControl extends AbstractControl {
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return SoundEvents.NOTE_BLOCK_CHIME.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
		if (!itardisLevel.GetLevel().isClientSide()) {
			itardisLevel.GetNavigationalData()
					.setDestinationDimensionKey(WorldHelper.cycleDimension(itardisLevel.GetNavigationalData().getDestinationDimensionKey(), true));
			if (entity instanceof Player player)
				player.displayClientMessage(
						Component.literal("Destination Level = " + GrammarNazi.CleanString(
								itardisLevel.GetNavigationalData().getExteriorDimensionKey().location().getPath())),
						false);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		if (!itardisLevel.GetLevel().isClientSide()) {
			itardisLevel.GetNavigationalData()
					.setDestinationDimensionKey(WorldHelper.cycleDimension(itardisLevel.GetNavigationalData().getDestinationDimensionKey(), false));

			player.displayClientMessage(Component.literal("Destination Level = " + GrammarNazi
					.CleanString(itardisLevel.GetNavigationalData().getExteriorDimensionKey().location().getPath())),
					true);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("dimension_control");
	}
}
