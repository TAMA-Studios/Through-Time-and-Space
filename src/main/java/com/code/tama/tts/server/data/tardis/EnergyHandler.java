/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.tardis;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler {
	private final EnergyStorage energy = new EnergyStorage(5_000_000, 50_000, 50_000);

	public int getEnergy() {
		return energy.getEnergyStored();
	}

	public int getMaxEnergy() {
		return energy.getMaxEnergyStored();
	}

	public int receiveEnergy(int amount, boolean simulate) {
		return energy.receiveEnergy(amount, simulate);
	}

	public int extractEnergy(int amount, boolean simulate) {
		return energy.extractEnergy(amount, simulate);
	}

	public boolean hasEnergy(int amount) {
		return energy.getEnergyStored() >= amount;
	}

	public void setEnergy(int energy) {
		this.energy.receiveEnergy(energy - this.energy.getEnergyStored(), false);
	}

	public void saveNBT(CompoundTag tag) {
		tag.put("Energy", energy.serializeNBT());
	}

	public void loadNBT(CompoundTag tag) {
		energy.deserializeNBT(tag.getCompound("Energy"));
	}
}
