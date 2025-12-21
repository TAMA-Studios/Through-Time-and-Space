/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

public class CrashFailureAction extends FlightEventFailureAction {
	public CrashFailureAction() {
		super(ITARDISLevel::Crash, "crash");
	}
}
