/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions;

import lombok.Getter;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fires when a dimension/level is about to be unregistered.<br>
 * This event fires on
 * {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS} and is not
 * cancellable.<br>
 */
@Getter
public class UnregisterDimensionEvent extends Event {
	/** The level that is about to be unregistered. */
	private final ServerLevel level;

	public UnregisterDimensionEvent(ServerLevel level) {
		this.level = level;
	}
}
