package com.code.tama.mtm.server.tardis;

import com.code.tama.mtm.server.enums.tardis.FlightTerminationProtocolEnum;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class ControlParameters implements INBTSerializable<CompoundTag> {
    boolean APCState;
    int ArtronPacketOutput;
    FlightTerminationProtocolEnum flightTerminationProtocolEnum = FlightTerminationProtocolEnum.POLITE;

    public ControlParameters(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("termination_protocol", this.flightTerminationProtocolEnum.ordinal());
        compoundTag.putBoolean("APC", this.APCState);
        compoundTag.putInt("ArtronPacketOutput", this.ArtronPacketOutput);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        //        for(FlightTerminationProtocolEnum Protocol : FlightTerminationProtocolEnum.values())
//            if(Protocol.ordinal() == prot) this.flightTerminationProtocolEnum = Protocol;

        this.flightTerminationProtocolEnum = FlightTerminationProtocolEnum.values()[nbt.getInt("termination_protocol")];
        this.APCState = nbt.getBoolean("APC");
        this.ArtronPacketOutput = nbt.getInt("ArtronPacketOutput");
    }

    //TODO: Implement Artron Frequency Controller
    /** Higher packet output = Smaller Artron Packets = Slower Flight Speed + Slower power drain <br />
     * Smaller packet output = Bigger Artron Packets = Faster Flight Speed + Faster power drain **/
    public int GetArtronPacketOutput() {
        return this.ArtronPacketOutput;
    }

    public void SetArtronPacketOutput(int ArtronPacketOutput) {
        this.ArtronPacketOutput = ArtronPacketOutput;
    }

    //TODO: Implement Automatic Power Cue
    /** Automatic power cue (APC). The APC function greatly improves the stability and safety of dematerialization and landing. Without the APC, the power drive will switch on and immediately operate at the required energy level for flight, and will shut down just prior to landing. This can result in violent jerks or shudders occurring during a materialization sequence.
     * If the TARDIS is stuck in a tractor beam, a non-APC flight might be able to break the tractor beam
     * a non-APC landing may enable the TARDIS to be snared out of the vortex to an undesirable landing coordinate. **/
    public boolean GetAPC() {
        return this.APCState;
    }

    public void SetAPC(boolean APCState) {
        this.APCState = APCState;
    }

    public FlightTerminationProtocolEnum GetTerminationProtocol() {
        return this.flightTerminationProtocolEnum;
    }

    public void SetTerminationProtocol(FlightTerminationProtocolEnum terminationProtocol) {
        this.flightTerminationProtocolEnum = terminationProtocol;
    }
}
