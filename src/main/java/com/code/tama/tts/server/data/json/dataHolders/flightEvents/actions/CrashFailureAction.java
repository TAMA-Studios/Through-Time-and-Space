package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

public class CrashFailureAction extends FlightEventAction {
    public CrashFailureAction() {
        super((cap) -> cap.Crash(), "crash");
    }
}
