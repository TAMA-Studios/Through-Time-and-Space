/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;

public class QuickStopProtocol extends FlightTerminationProtocol {
	public QuickStopProtocol() {
		super(0.7f, 0.9f, true, 0.7f, 0.7f, "quick_stop");
	}
}
