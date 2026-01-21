/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.tardis;

import java.util.Arrays;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

@Getter
public class EnergyHandler {
	public static Codec<EnergyHandler> CODEC = RecordCodecBuilder.create((i -> i
			.group(Codec.INT.fieldOf("mode").forGetter(e -> e.mode.ordinal()),
					Codec.LONG.fieldOf("potential").forGetter(EnergyHandler::getPotential),
					Codec.LONG.fieldOf("artron").forGetter(EnergyHandler::getPotential),
					Codec.INT.fieldOf("forge").forGetter(e -> e.getEnergyCap().getEnergyStored()))
			.apply(i, EnergyHandler::new)));

	private ITARDISLevel itardisLevel;

	@Setter
	public EnergyMode mode = EnergyMode.FORGE;
	public long potential = 0;
	public long artron = 0;

	@Getter
	private final CustomEnergyStorage EnergyCap = new CustomEnergyStorage(Integer.MAX_VALUE, 50_000, 50_000);

	public EnergyHandler(ITARDISLevel itardisLevel) {
		this.itardisLevel = itardisLevel;
	}

	public EnergyHandler(int mode, long potential, long artron, int forge) {
		this.EnergyCap.setEnergy(forge);
		this.artron = artron;
		this.potential = potential;
		this.mode = (EnergyMode) Arrays.stream(EnergyMode.values()).toArray()[mode];
	}

	public long getEnergy(EnergyMode mode) {
		return switch (mode) {
			case FORGE -> this.EnergyCap.getEnergyStored();
			case ARTRON -> this.artron;
			case POTENTIAL -> this.potential;
			case AUTO -> Math.max(Math.max(this.potential, this.artron), this.EnergyCap.getEnergyStored());
		};
	}

	public long getEnergy() {
		return getEnergy(mode);
	}

	public int getMaxEnergy() {
		return EnergyCap.getMaxEnergyStored();
	}

	/**
	 * Extracts energy based on the currently selected mode.
	 *
	 * @return The amount of energy actually extracted.
	 */
	public long extractEnergy(long amount, boolean simulate) {
		return extractEnergy(mode, amount, simulate);
	}

	/**
	 * Extracts energy based on the mode.
	 *
	 * @return The amount of energy actually extracted.
	 */
	public long extractEnergy(EnergyMode mode, long amount, boolean simulate) {
		if (amount <= 0)
			return 0;

		return switch (mode) {
			case FORGE -> this.EnergyCap.extractEnergy((int) Math.min(amount, Integer.MAX_VALUE), simulate);
			case ARTRON -> {
				long extracted = Math.min(this.artron, amount);
				if (!simulate)
					this.artron -= extracted;
				yield extracted;
			}
			case POTENTIAL -> {
				long extracted = Math.min(this.potential, amount);
				if (!simulate)
					this.potential -= extracted;
				yield extracted;
			}
			case AUTO -> extractEnergy(getDominantMode(), amount, simulate);
		};
	}

	public long receiveEnergy(long amount, boolean simulate) {
		return receiveEnergy(mode, amount, simulate);
	}

	public long receiveEnergy(EnergyMode mode, long amount, boolean simulate) {
		if (amount <= 0)
			return 0;

		return switch (mode) {
			case FORGE -> this.EnergyCap.receiveEnergy((int) Math.min(amount, Integer.MAX_VALUE), simulate);
			case ARTRON -> {
				if (!simulate)
					this.artron += amount;
				yield amount;
			}
			case POTENTIAL -> {
				if (!simulate)
					this.potential += amount;
				yield amount;
			}
			case AUTO -> receiveEnergy(getDominantMode(), amount, simulate);
		};
	}

	/**
	 * Determines which energy source is currently the highest.
	 */
	private EnergyMode getDominantMode() {
		long fe = this.EnergyCap.getEnergyStored();
		if (this.potential >= this.artron && this.potential >= fe)
			return EnergyMode.POTENTIAL;
		if (this.artron >= fe)
			return EnergyMode.ARTRON;
		return EnergyMode.FORGE;
	}

	public void saveNBT(CompoundTag tag) {
		tag.putInt("EnergyFE", this.EnergyCap.getEnergyStored());
		tag.putLong("EnergyArtron", this.artron);
		tag.putLong("EnergyPotential", this.potential);
		tag.putString("EnergyMode", this.mode.name());
	}

	public void loadNBT(CompoundTag tag) {
		this.EnergyCap.setEnergy(tag.getInt("EnergyFE"));
		this.artron = tag.getLong("EnergyArtron");
		this.potential = tag.getLong("EnergyPotential");
		if (tag.contains("EnergyMode")) {
			this.mode = EnergyMode.valueOf(tag.getString("EnergyMode"));
		}
	}

	public static class CustomEnergyStorage extends EnergyStorage {
		public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract) {
			super(capacity, maxReceive, maxExtract);
		}
		public void setEnergy(int energy) {
			this.energy = energy;
		}
	}
}