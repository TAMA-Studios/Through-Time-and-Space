/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class Y_Control extends AbstractControl {
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.DISPENSER_FAIL;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return TTSSounds.BUTTON_CLICK_01.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
		itardisLevel.GetNavigationalData()
				.setDestination(itardisLevel.GetNavigationalData().getDestination()
						.AddY(entity.isCrouching()
								? itardisLevel.GetNavigationalData().getIncrement()
								: -itardisLevel.GetNavigationalData().getIncrement()));
		if (entity instanceof Player player)
			player.displayClientMessage(Component.literal(
					"Current Destination = " + itardisLevel.GetNavigationalData().getDestination().ReadableString()),
					true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		itardisLevel.GetNavigationalData()
				.setDestination(itardisLevel.GetNavigationalData().getDestination()
						.AddY(player.isCrouching()
								? -itardisLevel.GetNavigationalData().getIncrement()
								: itardisLevel.GetNavigationalData().getIncrement()));
		player.displayClientMessage(Component.literal(
				"Current Destination = " + itardisLevel.GetNavigationalData().getDestination().ReadableString()), true);
		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("y_control");
	}
}
