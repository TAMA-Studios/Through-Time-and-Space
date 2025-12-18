/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.FlightEventAction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record DataFlightEvent(List<ResourceLocation> RequiredControls, String name, int Time, FlightEventAction action) {

	@Override
	public @NotNull String toString() {
		return String.format("DataFlightEvent{requiredControls=%s,name=%s,maxTime=%s,action=%s}", RequiredControls, name, Time, action.name);
	}
}
