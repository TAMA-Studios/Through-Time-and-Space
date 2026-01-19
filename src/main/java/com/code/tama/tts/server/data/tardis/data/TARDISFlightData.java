/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.tts.config.TTSConfig;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.DecoyFlightEvent;
import com.code.tama.tts.server.data.json.dataHolders.flightEvents.FlightEvent;
import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
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
			.group(FlightEvent.CODEC.fieldOf("flight_event").forGetter(TARDISFlightData::getFlightEvent),
					Codec.INT.fieldOf("ticksInFlight").forGetter(TARDISFlightData::getTicksInFlight),
					Codec.INT.fieldOf("drift").forGetter(TARDISFlightData::getDrift),
					FlightTerminationProtocolRegistry.CODEC.fieldOf("flightTerminationProtocol")
							.forGetter(TARDISFlightData::getFlightTerminationProtocol),
					FlightSoundSchemesRegistry.CODEC.fieldOf("flightSoundScheme")
							.forGetter(TARDISFlightData::getFlightSoundScheme),
					Codec.BOOL.fieldOf("isInFlight").forGetter(TARDISFlightData::isInFlight),
					Codec.BOOL.fieldOf("shouldPlayRotorAnimation").forGetter(TARDISFlightData::isPlayRotorAnimation))
			.apply(instance, TARDISFlightData::new));

	AbstractSoundScheme FlightSoundScheme = new SmithSoundScheme();

	ITARDISLevel TARDIS;
	private FlightEvent flightEvent = new DecoyFlightEvent();
	int TicksInFlight, Drift;
	FlightTerminationProtocol flightTerminationProtocol = FlightTerminationProtocolRegistry.POLITE_TERMINUS;
	boolean inFlight, PlayRotorAnimation;

	public TARDISFlightData(TARDISLevelCapability TARDIS) {
		this.TARDIS = TARDIS;
	}

	public TARDISFlightData(FlightEvent flightEvent, int ticksInFlight, int drift,
			FlightTerminationProtocol flightTerminationProtocol, AbstractSoundScheme flightSoundScheme,
			boolean inFlight, boolean playRotorAnimation) {
		this.TicksInFlight = ticksInFlight;
		this.Drift = drift;
		this.flightTerminationProtocol = flightTerminationProtocol;
		this.FlightSoundScheme = flightSoundScheme;
		this.inFlight = inFlight;
		this.PlayRotorAnimation = playRotorAnimation;
	}

	public boolean IsTakingOff() {
		return this.PlayRotorAnimation && !this.inFlight;
	}

	public SpaceTimeCoordinate distanceToLoc() {
		return new SpaceTimeCoordinate(this.TARDIS.GetNavigationalData().getDestination().GetBlockPos().getCenter()
				.subtract(this.TARDIS.GetNavigationalData().getLocation().GetBlockPos().getCenter()));
	}

	public long getTicksUntilArrival() {
		if (this.TARDIS.GetData().getControlData().isVortexAnchor())
			return Long.MAX_VALUE;

		SpaceTimeCoordinate delta = this.distanceToLoc();

		double speed = TTSConfig.ServerConfig.BLOCKS_PER_TICK.get()
				+ this.TARDIS.GetData().getControlData().GetArtronPacketOutput();

		if (speed <= 0)
			return Long.MAX_VALUE;

		double ticksX = Math.abs(delta.GetX()) / speed;
		double ticksY = Math.abs(delta.GetY()) / speed;
		double ticksZ = Math.abs(delta.GetZ()) / speed;

		double ticks = Math.max(ticksX, Math.max(ticksY, ticksZ));

		return Math.max(0L, (long) Math.ceil(ticks));
	}
}
