/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

public class DoNothingAction extends FlightEventFailureAction {

	public DoNothingAction() {
		super((t) -> {
		}, "nothing");
	}
}
