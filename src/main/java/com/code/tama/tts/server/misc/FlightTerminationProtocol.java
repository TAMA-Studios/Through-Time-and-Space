/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.server.tardis.terminationprotocol.TerminationProtocolHandler;
import lombok.Builder;

@Builder
public class FlightTerminationProtocol {
    // The probability of the TARDIS landing off course
    public final float Accuracy;
    // Whether the Pilot is able to select the termination protocol
    public final boolean IsSelectable;
    // How much the floaterior shakes during remat
    public final float LandShakeAmount;
    // Modifier used in calculations for how fast the TARDIS flies
    public final float Speed;
    // How much the exterior shakes during demat
    public final float TakeoffShakeAmount;
    public final String name;
    private final TerminationProtocolHandler terminationProtocolHandler;
}
