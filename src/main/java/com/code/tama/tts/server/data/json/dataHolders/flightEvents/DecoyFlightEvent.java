package com.code.tama.tts.server.data.json.dataHolders.flightEvents;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.DoNothingAction;

import java.util.List;

public class DecoyFlightEvent extends DataFlightEvent {

    public DecoyFlightEvent() {
        super(List.of(), "none", 0, new DoNothingAction());
    }
}
