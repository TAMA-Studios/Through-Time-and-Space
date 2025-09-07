/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import com.code.tama.tts.server.tardis.terminationprotocol.EmergencyStopProtocolHandler;
import com.code.tama.tts.server.tardis.terminationprotocol.PoliteTerminusProtocolHandler;
import com.code.tama.tts.server.tardis.terminationprotocol.QuickStopProtocolHandler;
import com.code.tama.tts.server.tardis.terminationprotocol.UrgentStopProtocolHandler;
import java.util.ArrayList;

public class FlightTerminationProtocolRegistry {
    public static ArrayList<FlightTerminationProtocol> FLIGHT_TERMINATION_PROTOCOLS = new ArrayList<>();

    public static final FlightTerminationProtocol POLITE_TERMINUS = AddProtocol(FlightTerminationProtocol.builder()
            .name("polite_terminus")
            .LandShakeAmount(0.1f)
            .TakeoffShakeAmount(0.1f)
            .Accuracy(0.8f)
            .Speed(0.5f)
            .IsSelectable(true)
            .terminationProtocolHandler(new PoliteTerminusProtocolHandler())
            .build());

    public static final FlightTerminationProtocol EMERGENCY_STOP = AddProtocol(FlightTerminationProtocol.builder()
            .name("emergency_stop")
            .LandShakeAmount(1.0f)
            .Accuracy(0.3f)
            .terminationProtocolHandler(new EmergencyStopProtocolHandler())
            .build());

    public static final FlightTerminationProtocol QUICK_STOP = AddProtocol(FlightTerminationProtocol.builder()
            .name("quick_stop")
            .LandShakeAmount(0.2f)
            .TakeoffShakeAmount(0.7f)
            .Accuracy(0.7f)
            .Speed(0.7f)
            .IsSelectable(true)
            .terminationProtocolHandler(new QuickStopProtocolHandler())
            .build());

    public static final FlightTerminationProtocol URGENT_STOP = AddProtocol(FlightTerminationProtocol.builder()
            .name("urgent_stop")
            .LandShakeAmount(0.2f)
            .TakeoffShakeAmount(1.0f)
            .Accuracy(1.0f)
            .Speed(0.9f)
            .IsSelectable(true)
            .terminationProtocolHandler(new UrgentStopProtocolHandler())
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
