/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import java.util.Set;

import com.code.tama.tts.client.TTSSounds;
import com.code.tama.tts.core.entities.TardisFlightEntity;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.tardis.exteriorViewing.EnvironmentViewerUtils;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import com.code.tama.triggerapi.universal.UniversalCommon;

/**
 * Console control for "Spatial Flight" / "Real World Flight" - converts the
 * TARDIS' landed {@link ExteriorTile} into a rideable
 * {@link TardisFlightEntity}, teleports the pressing player to it, and mounts
 * them up.
 *
 * <p>
 * Deliberately doesn't add any new methods to {@code TARDISLevelCapability} -
 * everything needed is already exposed on {@link ITARDISLevel}
 * ({@code CanFly()}, {@code GetFlightData()}, {@code GetExteriorTile()}), and
 * the block&lt;-&gt;entity conversion lives entirely on
 * {@link TardisFlightEntity} itself.
 * </p>
 */
public class SpatialFlightControl extends AbstractControl {

	private static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public SoundEvent GetFailSound() {
		return SoundEvents.ANVIL_BREAK;
	}

	@Override
	public SoundEvent GetSuccessSound() {
		return TTSSounds.BUTTON_CLICK_01.get();
	}

	@Override
	public InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player) {
		if (player.level().isClientSide)
			return InteractionResult.PASS;

		if (!(player instanceof ServerPlayer serverPlayer))
			return InteractionResult.PASS;

		LOGGER.info("[SpatialFlight] --- start --- itardisLevel.GetLevel()={} player.level()={}",
				itardisLevel.GetLevel().dimension().location(), player.level().dimension().location());

		if (!itardisLevel.CanFly() || itardisLevel.GetFlightData().isInFlight())
			return InteractionResult.FAIL;

		ExteriorTile tile = itardisLevel.GetExteriorTile();
		if (tile == null) {
			LOGGER.info("[SpatialFlight] GetExteriorTile() returned null, aborting");
			return InteractionResult.FAIL;
		}

		LOGGER.info("[SpatialFlight] tile.getBlockPos()={} tile.getLevel()={}", tile.getBlockPos(),
				tile.getLevel() == null ? "null" : tile.getLevel().dimension().location());

		SpaceTimeCoordinate exteriorLocation = itardisLevel.GetNavigationalData().GetExteriorLocation();
		if (exteriorLocation == null) {
			LOGGER.info("[SpatialFlight] GetExteriorLocation() returned null, aborting");
			return InteractionResult.FAIL;
		}

		LOGGER.info("[SpatialFlight] exteriorLocation: level={} pos={}",
				exteriorLocation.getLevel() == null ? "null" : exteriorLocation.getLevel().dimension().location(),
				exteriorLocation.GetBlockPos());

		if ((exteriorLocation.getLevel().isClientSide))
			return InteractionResult.FAIL;

		if (tile.getLevel() != null && tile.getLevel() != exteriorLocation.getLevel()) {
			LOGGER.warn(
					"[SpatialFlight] ExteriorTile.getLevel() ({}) disagrees with GetExteriorLocation() ({}) - using the latter.",
					tile.getLevel().dimension().location(), exteriorLocation.getLevel().dimension().location());
		}

		TardisFlightEntity flightEntity = TardisFlightEntity.fromTile(tile, exteriorLocation.getLevel());

		LOGGER.info("[SpatialFlight] spawned flightEntity: level={} pos={}",
				flightEntity.level().dimension().location(), flightEntity.position());
		LOGGER.info("[SpatialFlight] player BEFORE teleport: level={} pos={}",
				serverPlayer.level().dimension().location(), serverPlayer.position());

		serverPlayer.teleportTo(exteriorLocation.getLevel(), flightEntity.getX(), flightEntity.getY() + 0.1D,
				flightEntity.getZ(), Set.of(), serverPlayer.getYRot(), serverPlayer.getXRot());

		LOGGER.info("[SpatialFlight] player AFTER teleport: level={} pos={}",
				serverPlayer.level().dimension().location(), serverPlayer.position());

		boolean mounted = serverPlayer.startRiding(flightEntity, true);

		LOGGER.info(
				"[SpatialFlight] startRiding returned {} - player AFTER mount: level={} pos={} | flightEntity level={} pos={}",
				mounted, serverPlayer.level().dimension().location(), serverPlayer.position(),
				flightEntity.level().dimension().location(), flightEntity.position());

		// Reuse the exact invisible/no-collision/no-gravity treatment the
		// Environment Scanner already gives spectating players - it's the right
		// fit here too: third-person view, can't be grabbed by mobs or blocks.
		EnvironmentViewerUtils.updatePlayerAbilities(serverPlayer, serverPlayer.getAbilities(), true);
		serverPlayer.setInvisible(true);
		serverPlayer.onUpdateAbilities();

		return InteractionResult.SUCCESS;
	}

	@Override
	public InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity entity) {
		return InteractionResult.PASS;
	}

	@Override
	public ResourceLocation id() {
		return UniversalCommon.modRL("spatial_flight");
	}
}