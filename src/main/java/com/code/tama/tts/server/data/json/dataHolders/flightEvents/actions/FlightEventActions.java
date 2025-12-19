package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

import java.util.ArrayList;
import java.util.List;

public class FlightEventActions {
    public static List<FlightEventFailureAction> actions = new ArrayList<>();

    static {
        actions.add(new TakeArtronAction(100));
        actions.add(new DoNothingAction());
        actions.add(new CrashFailureAction());
    }
}
