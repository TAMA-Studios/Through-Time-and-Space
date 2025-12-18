package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import java.util.function.Consumer;

public class FlightEventAction {
    public final Consumer<ITARDISLevel> Action;
    public final String name;

    public FlightEventAction(Consumer<ITARDISLevel> action, String name) {
        this.Action = action;
        this.name = name;
    }
}
