/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.json.dataHolders.flightEvents;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.FlightEventActions;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions.FlightEventFailureAction;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.ResourceLocation;

@Getter
public class FlightEvent {
	public List<ResourceLocation> RequiredControls;
	public String name;
	public int Time;
	public FlightEventFailureAction action;

	public FlightEvent(List<ResourceLocation> requiredControls, String name, int time,
			FlightEventFailureAction action) {
		RequiredControls = requiredControls;
		this.name = name;
		Time = time;
		this.action = action;
	}

	public FlightEvent copy() {
		return new FlightEvent(this.RequiredControls, this.name, this.Time, this.action);
	}

	public FlightEvent(List<ResourceLocation> requiredControls, String name, int time, String action) {
		RequiredControls = new ArrayList<>(requiredControls);
		this.name = name;
		Time = time;
		this.action = FlightEventActions.actions.stream().filter(t -> t.name.equals(action)).toList().get(0);
	}

	public static final Codec<FlightEvent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ResourceLocation.CODEC.listOf().fieldOf("required_controls").forGetter(FlightEvent::getRequiredControls),

			Codec.STRING.fieldOf("name").forGetter(FlightEvent::getName),

			Codec.INT.fieldOf("time").forGetter(FlightEvent::getTime),

			Codec.STRING.fieldOf("action").forGetter(t -> t.action.name)).apply(instance, FlightEvent::new));

	@Override
	public @NotNull String toString() {
		return String.format("DataFlightEvent{requiredControls=%s,name=%s,maxTime=%s,action=%s}", RequiredControls,
				name, Time, action.name);
	}
}
