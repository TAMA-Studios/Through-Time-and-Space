/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis;

import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

@Getter
@Setter
public class SubsystemsData implements INBTSerializable<CompoundTag> {
    public DematerializationCircuit DematerializationCircuit = null;
    public NetherReactorCoreSubsystem NetherReactorCoreSubsystem = null;

    public SubsystemsData() {
    }

    public SubsystemsData(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        if (this.DematerializationCircuit != null)
            compoundTag.put("DematerializationCircuit", this.DematerializationCircuit.serializeNBT());

        if (this.NetherReactorCoreSubsystem != null)
            compoundTag.put("NetherReactorCoreSubsystem", this.NetherReactorCoreSubsystem.serializeNBT());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("DematerializationCircuit"))
            this.DematerializationCircuit.deserializeNBT(nbt.getCompound("DematerializationCircuit"));

        if (nbt.contains("NetherReactorCoreSubsystem"))
            this.NetherReactorCoreSubsystem.deserializeNBT(nbt.getCompound("NetherReactorCoreSubsystem"));
    }
}
