/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.PoliteTerminusProtocol;
import java.util.ArrayList;

public class FlightTerminationProtocolRegistry {
    public static ArrayList<FlightTerminationProtocol> FLIGHT_TERMINATION_PROTOCOLS = new ArrayList<>();

    public static FlightTerminationProtocol POLITE_TERMINUS = AddProtocol(FlightTerminationProtocol.builder()
            .name("polite_terminus")
            .LandShakeAmount(0.1f)
            .TakeoffShakeAmount(0.1f)
            .Accuracy(0.8f)
            .Speed(0.5f)
            .IsSelectable(true)
            .terminationProtocol(new PoliteTerminusProtocol())
            .build());

    public static FlightTerminationProtocol GetProtocol(int ID) {
        return FLIGHT_TERMINATION_PROTOCOLS.get(ID);
    }

    public static FlightTerminationProtocol AddProtocol(FlightTerminationProtocol structure) {
        FLIGHT_TERMINATION_PROTOCOLS.add(structure);
        return structure;
    }

    public static FlightTerminationProtocol CycleProt(FlightTerminationProtocol structure) {
        for (int i = 0; i < FLIGHT_TERMINATION_PROTOCOLS.size() - 1; i++) {
            if (GetProtocol(i).equals(structure)) return GetProtocol(i + 1);
        }
        return GetProtocol(0);
    }

    public static FlightTerminationProtocol GetByName(String name) {
        for (FlightTerminationProtocol protocol : FLIGHT_TERMINATION_PROTOCOLS) {
            if (protocol.name.equals(name)) return protocol;
        }
        return FLIGHT_TERMINATION_PROTOCOLS.get(0);
    }
}
