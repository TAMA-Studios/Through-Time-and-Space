/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.flight_events;

import java.util.List;

import com.code.tama.tts.server.tardis.controls.AbstractControl;
import lombok.Getter;

@Getter
public abstract class AbstractFlightEvent {
	private final List<AbstractControl> RequiredToComplete;
	private final String name;

	public AbstractFlightEvent(List<AbstractControl> requiredToComplete, String name) {
		this.RequiredToComplete = requiredToComplete;
		this.name = name;
	}

	public String getTranslatableKey() {
		return "tts.flight_event." + this.name;
	}

	public abstract void onFail();
	public abstract void onSuccess();
}
