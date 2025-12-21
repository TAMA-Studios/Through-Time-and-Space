/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents;

import java.util.List;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.DoNothingAction;

public class DecoyFlightEvent extends DataFlightEvent {

	public DecoyFlightEvent() {
		super(List.of(), "none", 0, new DoNothingAction());
	}
}
