/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

public class TakeEnergyAction extends FlightEventFailureAction {
	public TakeEnergyAction(int amountToTake) {
		super((cap) -> cap.getEnergy().extractPower(amountToTake, false), "take_artron");
	}
}
