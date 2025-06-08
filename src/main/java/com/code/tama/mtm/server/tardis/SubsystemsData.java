package com.code.tama.mtm.server.tardis;

import com.code.tama.mtm.server.blocks.subsystems.DematerializationCircuitSubsystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

@Getter @Setter
public class SubsystemsData implements INBTSerializable<CompoundTag> {
    public DematerializationCircuitSubsystem DematerializationCircuit = new DematerializationCircuitSubsystem();

    public SubsystemsData(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("DematerializationCircuit", this.DematerializationCircuit.serializeNBT());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.DematerializationCircuit.deserializeNBT(nbt.getCompound("DematerializationCircuit"));
    }
}
