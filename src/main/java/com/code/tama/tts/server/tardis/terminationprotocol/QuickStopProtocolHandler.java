/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.server.registries.tardis.FlightTerminationProtocolRegistry;

public class QuickStopProtocolHandler extends TerminationProtocolHandler {
    public QuickStopProtocolHandler() {
        super(FlightTerminationProtocolRegistry.QUICK_STOP);
    }
}
