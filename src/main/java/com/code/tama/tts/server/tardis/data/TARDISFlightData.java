/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import com.code.tama.tts.server.registries.FlightSoundSchemesRegistry;
import com.code.tama.tts.server.registries.FlightTerminationProtocolRegistry;
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
    public static final Codec<TARDISFlightData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("ticksInFlight").forGetter(TARDISFlightData::getTicksInFlight),
                    Codec.INT.fieldOf("ticksTillDestination").forGetter(TARDISFlightData::getTicksTillDestination),
                    FlightTerminationProtocolRegistry.CODEC
                            .fieldOf("flightTerminationProtocol")
                            .forGetter(TARDISFlightData::getFlightTerminationProtocol),
                    FlightSoundSchemesRegistry.CODEC
                            .fieldOf("flightSoundScheme")
                            .forGetter(TARDISFlightData::getFlightSoundScheme),
                    Codec.BOOL.fieldOf("isInFlight").forGetter(TARDISFlightData::isInFlight),
                    Codec.BOOL.fieldOf("shouldPlayRotorAnimation").forGetter(TARDISFlightData::isPlayRotorAnimation))
            .apply(instance, TARDISFlightData::new));

    ITARDISLevel TARDIS;

    int TicksInFlight, TicksTillDestination;
    FlightTerminationProtocol flightTerminationProtocol = FlightTerminationProtocolRegistry.POLITE_TERMINUS;
    AbstractSoundScheme FlightSoundScheme = new SmithSoundScheme();
    boolean inFlight, PlayRotorAnimation;

    public TARDISFlightData(TARDISLevelCapability TARDIS) {
        this.TARDIS = TARDIS;
    }

    public TARDISFlightData(
            int ticksInFlight,
            int ticksTillDestination,
            FlightTerminationProtocol flightTerminationProtocol,
            AbstractSoundScheme flightSoundScheme,
            boolean inFlight,
            boolean playRotorAnimation) {
        TicksInFlight = ticksInFlight;
        TicksTillDestination = ticksTillDestination;
        this.flightTerminationProtocol = flightTerminationProtocol;
        FlightSoundScheme = flightSoundScheme;
        this.inFlight = inFlight;
        PlayRotorAnimation = playRotorAnimation;
    }

    public boolean IsTakingOff() {
        return this.PlayRotorAnimation && !this.inFlight;
    }
}
