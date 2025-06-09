/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

@Getter
@Setter
public class SubsystemsData implements INBTSerializable<CompoundTag> {
    public DematerializationCircuit DematerializationCircuit = new DematerializationCircuit();
    public NetherReactorCoreSubsystem NetherReactorCoreSubsystem = new NetherReactorCoreSubsystem();

    public SubsystemsData() {}

    public SubsystemsData(CompoundTag compoundTag) {
        this.deserializeNBT(compoundTag);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("DematerializationCircuit"))
            this.DematerializationCircuit.deserializeNBT(nbt.getCompound("DematerializationCircuit"));

        if (nbt.contains("NetherReactorCoreSubsystem"))
            this.NetherReactorCoreSubsystem.deserializeNBT(nbt.getCompound("NetherReactorCoreSubsystem"));
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
}
