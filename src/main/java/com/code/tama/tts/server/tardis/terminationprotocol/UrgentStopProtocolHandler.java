/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.terminationprotocol;

import com.code.tama.tts.server.registries.FlightTerminationProtocolRegistry;

public class UrgentStopProtocolHandler extends TerminationProtocolHandler {
  public UrgentStopProtocolHandler() {
    super(FlightTerminationProtocolRegistry.URGENT_STOP);
  }
}
