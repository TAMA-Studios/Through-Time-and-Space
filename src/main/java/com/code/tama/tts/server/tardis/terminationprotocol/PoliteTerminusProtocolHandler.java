/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.server.registries.tardis.FlightTerminationProtocolRegistry;

public class PoliteTerminusProtocolHandler extends TerminationProtocolHandler {
    public PoliteTerminusProtocolHandler() {
        super(FlightTerminationProtocolRegistry.POLITE_TERMINUS);
    }
}
