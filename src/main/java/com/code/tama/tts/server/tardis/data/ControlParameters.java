/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.tts.server.misc.FlightTerminationProtocol;
import com.code.tama.tts.server.registries.FlightTerminationProtocolRegistry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

@NoArgsConstructor
@Getter
@Setter
public class ControlParameters implements INBTSerializable<CompoundTag> {
    public float HelmicRegulator;
    public boolean APCState, Brakes, SimpleMode;
    public int ArtronPacketOutput;
    FlightTerminationProtocol flightTerminationProtocolEnum = FlightTerminationProtocolRegistry.POLITE_TERMINUS;

    public ControlParameters(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
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

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.flightTerminationProtocolEnum =
                FlightTerminationProtocolRegistry.FLIGHT_TERMINATION_PROTOCOLS.get(nbt.getInt("termination_protocol"));
        this.APCState = nbt.getBoolean("APC");
        this.ArtronPacketOutput = nbt.getInt("ArtronPacketOutput");
        this.SimpleMode = nbt.getBoolean("SimpleMode");
        this.HelmicRegulator = nbt.getFloat("HelmicRegulators");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt(
                "termination_protocol",
                FlightTerminationProtocolRegistry.FLIGHT_TERMINATION_PROTOCOLS.indexOf(
                        this.flightTerminationProtocolEnum));
        compoundTag.putBoolean("APC", this.APCState);
        compoundTag.putInt("ArtronPacketOutput", this.ArtronPacketOutput);
        compoundTag.putBoolean("SimpleMode", this.SimpleMode);
        compoundTag.putFloat("HelmicRegulators", this.HelmicRegulator);
        return compoundTag;
    }
}
