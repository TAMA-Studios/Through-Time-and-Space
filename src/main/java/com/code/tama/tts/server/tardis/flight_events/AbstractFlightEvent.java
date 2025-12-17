package com.code.tama.tts.server.tardis.flight_events;

import com.code.tama.tts.server.tardis.controls.AbstractControl;
import lombok.Getter;

import java.util.List;

@Getter
public class AbstractFlightEvent {
    private final List<AbstractControl> RequiredToComplete;
    private final String name;

    public AbstractFlightEvent(List<AbstractControl> requiredToComplete, String name) {
        this.RequiredToComplete = requiredToComplete;
        this.name = name;
    }

    public String getTranslatableKey() {
        return "tts.flight_event." + this.name;
    }
}
