/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.enums.tardis;

import com.code.tama.tts.server.tardis.terminationprotocol.*;

public enum FlightTerminationProtocolEnum {
    POLITE("polite_terminus", 0.1f, 0.1f, 0.8f, 0.5f, new PoliteTerminusProtocol(), true),
    QUICK_STOP("quick_stop", 0.2f, 0.7f, 0.7f, 0.7f, new QuickStopProtocol(), true),
    URGENT_STOP("urgent_stop", 0.2f, 1.0f, 1.0f, 0.9f, new UrgentStopProtocol(), true),
    EMERGENCY_STOP("emergency_stop", 1.0f, 0.3f, new EmergencyStopProtocol());

    private final String name;
    // How much the floaterior shakes during demat
    private final float TakeoffShakeAmount;
    // How much the floaterior shakes during remat
    private final float LandShakeAmount;
    // The probability of the TARDIS landing off course
    private final float Accuracy;
    // Modifier used in calculations for how fast the TARDIS flies
    private final float Speed;
    // Whether the Pilot is able to select the termination protocol
    private final boolean IsSelectable;
    // Stores the termination protocol which stores code that gets executed on landing
    private final TerminationProtocol terminationProtocol;

    FlightTerminationProtocolEnum(String name, float TakeoffShakeAmount, float LandShakeAmount, float Accuracy, float Speed, TerminationProtocol terminationProtocol, boolean IsSelectable) {
        this.TakeoffShakeAmount = TakeoffShakeAmount;
        this.LandShakeAmount = LandShakeAmount;
        this.name = name;
        this.Accuracy = Accuracy;
        this.Speed = Speed;
        this.IsSelectable = IsSelectable;
        this.terminationProtocol = terminationProtocol;
    }

    FlightTerminationProtocolEnum(String name, float ShakeAmount, float Accuracy, TerminationProtocol terminationProtocol) {
        this.TakeoffShakeAmount = ShakeAmount;
        this.LandShakeAmount = ShakeAmount;
        this.name = name;
        this.Accuracy = Accuracy;
        this.Speed = 0.0f;
        this.IsSelectable = false;
        this.terminationProtocol = terminationProtocol;
    }

    public String GetName() {
        return this.name;
    }

    public float GetTakeoffShakeAmount() {
        return this.TakeoffShakeAmount;
    }

    public float GetLandShakeAmount() {
        return this.LandShakeAmount;
    }

    public float GetAccuracy() {
        return this.Accuracy;
    }

    public float GetSpeed() {
        return this.Speed;
    }

    public TerminationProtocol GetProtocol() {
        return this.terminationProtocol;
    }

    public static FlightTerminationProtocolEnum GetFromName(String name) {
        for(FlightTerminationProtocolEnum protocol : FlightTerminationProtocolEnum.values()) {
            if(protocol.GetName().equals(name))
                return protocol;
        }
        return FlightTerminationProtocolEnum.values()[0];
    }
}
