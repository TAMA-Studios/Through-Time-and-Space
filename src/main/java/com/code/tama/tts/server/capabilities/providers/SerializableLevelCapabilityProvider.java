/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.providers;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerializableLevelCapabilityProvider<T extends INBTSerializable<C>, C extends Tag, I extends T> implements ICapabilitySerializable<C> {

    private final T CapabilityInterface;
    private final LazyOptional<T> lazyOptional;
    private final Capability<I> INSTANCE;

    public SerializableLevelCapabilityProvider(Capability<I> instance, T CapInterface){
        this.INSTANCE = instance;
        this.CapabilityInterface = CapInterface;
        this.lazyOptional = LazyOptional.of(() -> this.CapabilityInterface);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == this.INSTANCE ? this.lazyOptional.cast() : LazyOptional.empty();
    }

    @Override
    public C serializeNBT() {
        return this.CapabilityInterface.serializeNBT();
    }

    @Override
    public void deserializeNBT(C nbt) {
        this.CapabilityInterface.deserializeNBT(nbt);
    }
}

