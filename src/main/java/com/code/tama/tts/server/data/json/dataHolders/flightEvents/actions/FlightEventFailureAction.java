package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import java.util.function.Consumer;

public class FlightEventFailureAction {
    public final Consumer<ITARDISLevel> Action;
    public final String name;

    public FlightEventFailureAction(Consumer<ITARDISLevel> action, String name) {
        this.Action = action;
        this.name = name;
    }
}
