/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.events;

import com.code.tama.tts.core.entities.TardisFlightEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Prevents a player piloting a {@link TardisFlightEntity} from being ejected by
 * an incidental sneak/dismount trigger mid-flight. The only legitimate way off
 * this entity is
 * {@link TardisFlightEntity#Land(net.minecraft.server.level.ServerPlayer)},
 * which briefly flips {@link TardisFlightEntity#allowDismount} to true around
 * its own {@code stopRiding()} call - any dismount attempt seen while that flag
 * is false gets cancelled outright.
 */
public class TardisFlightEventHandler {

	@SubscribeEvent
	public static void onMount(EntityMountEvent event) {
		if (event.isMounting())
			return; // only care about dismounts

		if (!(event.getEntityBeingMounted() instanceof TardisFlightEntity flightEntity))
			return;

		if (!(event.getEntityMounting() instanceof Player))
			return;

		if (!flightEntity.allowDismount) {
			event.setCanceled(true);
		}
	}
}