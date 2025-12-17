/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import com.code.tama.tts.server.misc.containers.FlightTerminationProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.EmergencyStopProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.PoliteTerminusProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.QuickStopProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.UrgentStopProtocol;
import com.mojang.serialization.Codec;

import java.util.ArrayList;

public class FlightTerminationProtocolRegistry {
	public static final Codec<FlightTerminationProtocol> CODEC = Codec.STRING
			.xmap(FlightTerminationProtocolRegistry::GetByName, FlightTerminationProtocol::getName);

	public static ArrayList<FlightTerminationProtocol> FLIGHT_TERMINATION_PROTOCOLS = new ArrayList<>();

	public static final FlightTerminationProtocol POLITE_TERMINUS = AddProtocol(new PoliteTerminusProtocol());

	public static final FlightTerminationProtocol EMERGENCY_STOP = AddProtocol(new EmergencyStopProtocol());

	public static final FlightTerminationProtocol QUICK_STOP = AddProtocol(new QuickStopProtocol());

	public static final FlightTerminationProtocol URGENT_STOP = AddProtocol(new UrgentStopProtocol());

	public static FlightTerminationProtocol GetProtocol(int ID) {
		return FLIGHT_TERMINATION_PROTOCOLS.get(ID);
	}

	public static FlightTerminationProtocol AddProtocol(FlightTerminationProtocol structure) {
		FLIGHT_TERMINATION_PROTOCOLS.add(structure);
		return structure;
	}

	public static FlightTerminationProtocol CycleProt(FlightTerminationProtocol structure) {
		for (int i = 0; i < FLIGHT_TERMINATION_PROTOCOLS.size() - 1; i++) {
			if (GetProtocol(i).equals(structure))
				return GetProtocol(i + 1);
		}
		return GetProtocol(0);
	}

	public static FlightTerminationProtocol GetByName(String name) {
		for (FlightTerminationProtocol protocol : FLIGHT_TERMINATION_PROTOCOLS) {
			if (protocol.name.equals(name))
				return protocol;
		}
		return FLIGHT_TERMINATION_PROTOCOLS.get(0);
	}
}
