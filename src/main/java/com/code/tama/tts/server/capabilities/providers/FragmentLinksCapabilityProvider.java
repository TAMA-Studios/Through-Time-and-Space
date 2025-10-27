/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.providers;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;

public class FragmentLinksCapabilityProvider implements ICapabilityProvider {

	public EnergyStorage energyStorage = new EnergyStorage(20000, 1000, 1000, 0);
	public LazyOptional<EnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return energyCap.cast();
		}
		return LazyOptional.empty();
	}
}
