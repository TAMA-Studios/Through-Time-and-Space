/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;

public class UrgentStopProtocol extends FlightTerminationProtocol {
	public UrgentStopProtocol() {
		super(1.0f, 0.7f, true, 0.9f, 1.0f, "urgent_stop");
	}
}
