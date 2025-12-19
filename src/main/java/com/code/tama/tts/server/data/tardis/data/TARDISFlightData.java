/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DataFlightEvent;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DecoyFlightEvent;
import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;
import com.code.tama.tts.server.registries.tardis.FlightSoundSchemesRegistry;
import com.code.tama.tts.server.registries.tardis.FlightTerminationProtocolRegistry;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.tardis.flightsoundschemes.SmithSoundScheme;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TARDISFlightData {
	public static final Codec<TARDISFlightData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(DataFlightEvent.CODEC.fieldOf("flight_event").forGetter(TARDISFlightData::getFlightEvent),
					Codec.INT.fieldOf("ticksInFlight").forGetter(TARDISFlightData::getTicksInFlight),
					Codec.INT.fieldOf("drift").forGetter(TARDISFlightData::getDrift),
					Codec.INT.fieldOf("ticksTillDestination").forGetter(TARDISFlightData::getTicksTillDestination),
					FlightTerminationProtocolRegistry.CODEC.fieldOf("flightTerminationProtocol")
							.forGetter(TARDISFlightData::getFlightTerminationProtocol),
					FlightSoundSchemesRegistry.CODEC.fieldOf("flightSoundScheme")
							.forGetter(TARDISFlightData::getFlightSoundScheme),
					Codec.BOOL.fieldOf("isInFlight").forGetter(TARDISFlightData::isInFlight),
					Codec.BOOL.fieldOf("shouldPlayRotorAnimation").forGetter(TARDISFlightData::isPlayRotorAnimation))
			.apply(instance, TARDISFlightData::new));

	AbstractSoundScheme FlightSoundScheme = new SmithSoundScheme();

	ITARDISLevel TARDIS;
	private DataFlightEvent flightEvent = new DecoyFlightEvent();
	int TicksInFlight, TicksTillDestination, Drift;
	FlightTerminationProtocol flightTerminationProtocol = FlightTerminationProtocolRegistry.POLITE_TERMINUS;
	boolean inFlight, PlayRotorAnimation;

	public TARDISFlightData(TARDISLevelCapability TARDIS) {
		this.TARDIS = TARDIS;
	}

	public TARDISFlightData(DataFlightEvent flightEvent, int ticksInFlight, int drift, int ticksTillDestination,
			FlightTerminationProtocol flightTerminationProtocol, AbstractSoundScheme flightSoundScheme,
			boolean inFlight, boolean playRotorAnimation) {
		this.TicksInFlight = ticksInFlight;
		this.Drift = drift;
		this.TicksTillDestination = ticksTillDestination;
		this.flightTerminationProtocol = flightTerminationProtocol;
		this.FlightSoundScheme = flightSoundScheme;
		this.inFlight = inFlight;
		this.PlayRotorAnimation = playRotorAnimation;
	}

	public boolean IsTakingOff() {
		return this.PlayRotorAnimation && !this.inFlight;
	}
}
