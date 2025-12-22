/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.GrammarNazi;
import com.code.tama.triggerapi.helpers.world.WorldHelper;
import com.code.tama.triggerapi.universal.UniversalCommon;

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
			if (itardisLevel.GetData().getSubSystemsData().NetherReactorCoreSubsystem.isActivated())
				return InteractionResult.FAIL;

			SpaceTimeCoordinate coordinate = itardisLevel.GetNavigationalData().getDestination();
			coordinate.setLevel(
					WorldHelper.getNextDimension(itardisLevel.GetNavigationalData().getDestination().getLevelKey()));
			itardisLevel.GetNavigationalData().setDestination(coordinate);

			itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);

			if (entity instanceof Player player)
				player.displayClientMessage(Component.literal("Destination Level = " + GrammarNazi.CleanString(
						itardisLevel.GetNavigationalData().getDestination().getLevelKey().location().getPath())),
						false);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		if (!itardisLevel.GetLevel().isClientSide()) {
			if (itardisLevel.GetData().getSubSystemsData().NetherReactorCoreSubsystem.isActivated())
				return InteractionResult.FAIL;

			SpaceTimeCoordinate coordinate = itardisLevel.GetNavigationalData().getDestination();
			coordinate.setLevel(
					WorldHelper.getNextDimension(itardisLevel.GetNavigationalData().getDestination().getLevelKey()));
			itardisLevel.GetNavigationalData().setDestination(coordinate);

			itardisLevel.UpdateClient(DataUpdateValues.NAVIGATIONAL);

			player.displayClientMessage(
					Component.literal("Destination Level = " + GrammarNazi.CleanString(
							itardisLevel.GetNavigationalData().getDestination().getLevelKey().location().getPath())),
					true);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("dimension_control");
	}
}
