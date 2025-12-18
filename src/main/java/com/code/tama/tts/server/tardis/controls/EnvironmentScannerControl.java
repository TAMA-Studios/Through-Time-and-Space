/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.tardis.exteriorViewing.EnvironmentViewerUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EnvironmentScannerControl extends AbstractControl {
	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.ANVIL_BREAK;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return TTSSounds.BUTTON_CLICK_01.get();
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
		if (itardisLevel.GetLevel().isClientSide)
			return InteractionResult.PASS;
		if (entity instanceof ServerPlayer player)
			EnvironmentViewerUtils.startSpectateExt(player, itardisLevel,
					itardisLevel.GetNavigationalData().GetExteriorLocation());

		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		if (player.level().isClientSide)
			return InteractionResult.PASS;
		if (player instanceof ServerPlayer serverPlayer)
			EnvironmentViewerUtils.startSpectateExt(serverPlayer, itardisLevel,
					itardisLevel.GetNavigationalData().GetExteriorLocation());

		return InteractionResult.SUCCESS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("environment_scanner");
	}
}
