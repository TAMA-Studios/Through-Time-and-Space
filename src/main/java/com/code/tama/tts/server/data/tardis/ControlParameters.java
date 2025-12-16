/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;
import com.code.tama.tts.server.registries.tardis.FlightTerminationProtocolRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ControlParameters {
	public static Codec<ControlParameters> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.FLOAT.fieldOf("helmic_regulator").forGetter(ControlParameters::getHelmicRegulator),
					Codec.BOOL.fieldOf("apc_state").forGetter(ControlParameters::isAPCState),
					Codec.BOOL.fieldOf("brakes").forGetter(ControlParameters::isBrakes),
					Codec.BOOL.fieldOf("simple_mode").forGetter(ControlParameters::isSimpleMode),
					Codec.BOOL.fieldOf("coordinate_lock").forGetter(ControlParameters::isCoordinateLock),
					Codec.INT.fieldOf("artron_packet_output").forGetter(ControlParameters::GetArtronPacketOutput),
					FlightTerminationProtocolRegistry.CODEC.fieldOf("flight_termination_protocol")
							.forGetter(ControlParameters::getFlightTerminationProtocol))
			.apply(instance, ControlParameters::new));

	FlightTerminationProtocol flightTerminationProtocol = FlightTerminationProtocolRegistry.POLITE_TERMINUS;
	public boolean APCState, Brakes, SimpleMode, CoordinateLock;
	public int ArtronPacketOutput;
	public float HelmicRegulator;

	public ControlParameters(Float helmicRegulator, Boolean apcState, Boolean brakes, Boolean simpleMode, Boolean coordinateLock,
			Integer artronPacketOutput, FlightTerminationProtocol flightTerminationProtocol) {
		this.flightTerminationProtocol = flightTerminationProtocol;
		this.APCState = apcState;
		Brakes = brakes;
		SimpleMode = simpleMode;
		CoordinateLock = coordinateLock;
		ArtronPacketOutput = artronPacketOutput;
		HelmicRegulator = helmicRegulator;
	}

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
	 */
	public boolean GetAPC() {
		return this.APCState;
	}

	// TODO: Implement Artron Frequency Controller
	/**
	 * Higher packet output = Smaller Artron Packets = Slower Flight Speed + Slower
	 * power drain <br>
	 * Smaller packet output = Bigger Artron Packets = Faster Flight Speed + Faster
	 * power drain
	 */
	public int GetArtronPacketOutput() {
		return this.ArtronPacketOutput;
	}
}
