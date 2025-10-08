/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import com.code.tama.tts.server.registries.FlightTerminationProtocolRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ControlParameters {
    public static Codec<ControlParameters> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.FLOAT.fieldOf("helmic_regulator").forGetter(ControlParameters::getHelmicRegulator),
                    Codec.BOOL.fieldOf("apc_state").forGetter(ControlParameters::isAPCState),
                    Codec.BOOL.fieldOf("brakes").forGetter(ControlParameters::isBrakes),
                    Codec.BOOL.fieldOf("simple_mode").forGetter(ControlParameters::isSimpleMode),
                    Codec.INT.fieldOf("artron_packet_output").forGetter(ControlParameters::GetArtronPacketOutput),
                    FlightTerminationProtocolRegistry.CODEC
                            .fieldOf("flight_termination_protocol")
                            .forGetter(ControlParameters::getFlightTerminationProtocol))
            .apply(instance, ControlParameters::new));

    public float HelmicRegulator;
    public boolean APCState, Brakes, SimpleMode;
    public int ArtronPacketOutput;
    FlightTerminationProtocol flightTerminationProtocol = FlightTerminationProtocolRegistry.POLITE_TERMINUS;

    // TODO: Implement Automatic Power Cue
    /**
     * Automatic power cue (APC). The APC function greatly improves the stability
     * and safety of dematerialization and landing. Without the APC, the power drive
     * will switch on and immediately operate at the required energy level for
     * flight, and will shut down just prior to landing. This can result in violent
     * jerks or shudders occurring during a materialization sequence. If the TARDIS
     * is stuck in a tractor beam, a non-APC flight might be able to break the
     * tractor beam a non-APC landing may enable the TARDIS to be snared out of the
     * vortex to an undesirable landing coordinate.
     **/
    public boolean GetAPC() {
        return this.APCState;
    }

    // TODO: Implement Artron Frequency Controller
    /**
     * Higher packet output = Smaller Artron Packets = Slower Flight Speed + Slower
     * power drain <br />
     * Smaller packet output = Bigger Artron Packets = Faster Flight Speed + Faster
     * power drain
     **/
    public int GetArtronPacketOutput() {
        return this.ArtronPacketOutput;
    }
}
