/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.tardis;

import java.util.Arrays;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

@Getter
public class PowerHandler {
	public static Codec<PowerHandler> CODEC = RecordCodecBuilder.create((i -> i
			.group(Codec.INT.fieldOf("mode").forGetter(e -> e.mode.ordinal()),
					Codec.LONG.fieldOf("potential").forGetter(PowerHandler::getPotential),
					Codec.LONG.fieldOf("artron").forGetter(PowerHandler::getArtron),
					Codec.INT.fieldOf("forge").forGetter(e -> e.getEnergyCap().getEnergyStored()),

					Codec.LONG.fieldOf("prevPotential").forGetter(PowerHandler::getPrevPotential),
					Codec.LONG.fieldOf("prevArtron").forGetter(PowerHandler::getPrevArtron),
					Codec.INT.fieldOf("prevForge").forGetter(PowerHandler::getPrevForge),

					Codec.LONG.fieldOf("totalEnergyGenerated").forGetter(e -> e.totalEnergyGenerated),
					Codec.LONG.fieldOf("totalEnergyConsumed").forGetter(e -> e.totalEnergyConsumed))
			.apply(i, PowerHandler::new)));

	private ITARDISLevel itardisLevel;

	public EnergyMode mode = EnergyMode.FORGE;
	public long potential, artron, prevPotential, prevArtron, lastRequestTime;
	int prevForge;

	// Real-world energy tracking
	private long totalEnergyGenerated = 0;
	private long totalEnergyConsumed = 0;
	private long lastTickTime = 0;
	private double efficiency = 0.95; // 95% efficiency by default

	// Capacitor characteristics (in FE)
	private static final int MAX_CAPACITY = Integer.MAX_VALUE;
	private static final int MAX_TRANSFER_RATE = 100_000; // FE/tick

	@Getter
	private final CustomEnergyStorage EnergyCap = new CustomEnergyStorage(MAX_CAPACITY, MAX_TRANSFER_RATE,
			MAX_TRANSFER_RATE);

	public PowerHandler(ITARDISLevel itardisLevel) {
		this.itardisLevel = itardisLevel;
		this.lastTickTime = itardisLevel.getTicks();
	}

	public PowerHandler(int mode, long potential, long artron, int forge, long prevPotential, long prevArtron,
			int prevForge, long totalGenerated, long totalConsumed) {
		this.EnergyCap.setEnergy(forge);
		this.artron = artron;
		this.potential = potential;
		this.prevForge = prevForge;
		this.prevArtron = prevArtron;
		this.prevPotential = prevPotential;
		this.mode = (EnergyMode) Arrays.stream(EnergyMode.values()).toArray()[mode];
		this.totalEnergyGenerated = totalGenerated;
		this.totalEnergyConsumed = totalConsumed;
	}

	/**
	 * Gets the stored power in the specified mode (all in FE equivalents)
	 */
	public long getPower(EnergyMode mode) {
		return switch (mode) {
			case FORGE -> this.EnergyCap.getEnergyStored();
			case ARTRON -> convertArtronToFE(this.artron);
			case POTENTIAL -> convertPotentialToFE(this.potential);
			case AUTO -> Math.max(Math.max(convertPotentialToFE(this.potential), convertArtronToFE(this.artron)),
					this.EnergyCap.getEnergyStored());
		};
	}

	public long getPower() {
		return getPower(mode);
	}

	public int getMaxPower() {
		return EnergyCap.getMaxEnergyStored();
	}

	/**
	 * Gets the current power flow (FE/tick) for the specified mode
	 */
	public long getPowerFlow(EnergyMode mode) {
		long currentTick = itardisLevel.getTicks();
		if (currentTick - 1 == lastRequestTime) {
			return switch (mode) {
				case FORGE -> this.EnergyCap.getEnergyStored() - prevForge;
				case ARTRON -> convertArtronToFE(this.artron) - convertArtronToFE(this.prevArtron);
				case POTENTIAL -> convertPotentialToFE(this.potential) - convertPotentialToFE(this.prevPotential);
				case AUTO -> getPowerFlow(getDominantMode());
			};
		}
		return 0L;
	}

	public long getPowerFlow() {
		return getPowerFlow(mode);
	}

	/**
	 * Extracts energy based on the currently selected mode with efficiency loss.
	 *
	 * @param amount
	 *            Amount of power to extract (in FE/tick)
	 * @param simulate
	 *            If true, doesn't actually extract
	 * @return The amount of energy actually extracted
	 */
	public long extractPower(long amount, boolean simulate) {
		return extractPower(mode, amount, simulate);
	}

	/**
	 * Extracts energy based on the specified mode with efficiency loss. Real-world
	 * power systems lose energy during transmission/conversion.
	 */
	public long extractPower(EnergyMode mode, long amount, boolean simulate) {
		if (amount <= 0)
			return 0;

		long actualExtracted = switch (mode) {
			case FORGE -> this.EnergyCap.extractEnergy((int) Math.min(amount, Integer.MAX_VALUE), simulate);
			case ARTRON -> {
				long feEquivalent = convertArtronToFE(this.artron);
				long extracted = Math.min(feEquivalent, amount);
				if (!simulate) {
					this.artron -= convertFEToArtron(extracted);
					this.totalEnergyConsumed += extracted;
				}
				yield extracted;
			}
			case POTENTIAL -> {
				long feEquivalent = convertPotentialToFE(this.potential);
				long extracted = Math.min(feEquivalent, amount);
				if (!simulate) {
					this.potential -= convertFEToPotential(extracted);
					this.totalEnergyConsumed += extracted;
				}
				yield extracted;
			}
			case AUTO -> extractPower(getDominantMode(), amount, simulate);
		};

		if (!simulate) {
			this.totalEnergyConsumed += actualExtracted;
		}
		return actualExtracted;
	}

	/**
	 * Receives energy based on the currently selected mode with efficiency gains.
	 */
	public long receivePower(long amount, boolean simulate) {
		return receivePower(mode, amount, simulate);
	}

	/**
	 * Receives energy based on the specified mode. In real-world systems, charging
	 * has efficiency losses too.
	 */
	public long receivePower(EnergyMode mode, long amount, boolean simulate) {
		if (amount <= 0)
			return 0;

		long actualReceived = switch (mode) {
			case FORGE -> this.EnergyCap.receiveEnergy((int) Math.min(amount, Integer.MAX_VALUE), simulate);
			case ARTRON -> {
				// Apply efficiency when storing energy
				long efficient = Math.round(amount * efficiency);
				if (!simulate) {
					this.artron += convertFEToArtron(efficient);
					this.totalEnergyGenerated += efficient;
				}
				yield efficient;
			}
			case POTENTIAL -> {
				// Apply efficiency when storing energy
				long efficient = Math.round(amount * efficiency);
				if (!simulate) {
					this.potential += convertFEToPotential(efficient);
					this.totalEnergyGenerated += efficient;
				}
				yield efficient;
			}
			case AUTO -> receivePower(getDominantMode(), amount, simulate);
		};

		return actualReceived;
	}

	/**
	 * Gets the current voltage (FE/t at max capacity)
	 */
	public double getVoltage() {
		return (double) getPower() / getMaxPower() * 10000; // Normalized to 10000V at full capacity
	}

	/**
	 * Gets the current voltage (FE/t at max capacity)
	 */
	public int getVoltageInt() {
		return Math.toIntExact(getPower() / getMaxPower() * 10000); // Normalized to 10000V at full capacity
	}

	/**
	 * Gets system efficiency (0.0 - 1.0)
	 */
	public double getEfficiency() {
		if (totalEnergyGenerated == 0)
			return efficiency;
		return (double) totalEnergyConsumed / totalEnergyGenerated;
	}

	/**
	 * Sets system efficiency for losses during conversion
	 */
	public void setEfficiency(double eff) {
		this.efficiency = Math.max(0.0, Math.min(1.0, eff));
	}

	/**
	 * Determines which energy source is currently the highest.
	 */
	private EnergyMode getDominantMode() {
		long fe = this.EnergyCap.getEnergyStored();
		long artronFE = convertArtronToFE(this.artron);
		long potentialFE = convertPotentialToFE(this.potential);

		if (potentialFE >= artronFE && potentialFE >= fe)
			return EnergyMode.POTENTIAL;
		if (artronFE >= fe)
			return EnergyMode.ARTRON;
		return EnergyMode.FORGE;
	}

	// Energy conversion factors
	private long convertArtronToFE(long artron) {
		return artron / 2; // 1 Artron = 0.5 FE (adjust ratio as needed)
	}

	private long convertFEToArtron(long fe) {
		return fe * 2; // 1 FE = 2 Artron
	}

	private long convertPotentialToFE(long potential) {
		return potential / 4; // 1 Potential = 0.25 FE
	}

	private long convertFEToPotential(long fe) {
		return fe * 4; // 1 FE = 4 Potential
	}

	public void saveNBT(CompoundTag tag) {
		tag.putInt("EnergyFE", this.EnergyCap.getEnergyStored());
		tag.putLong("EnergyArtron", this.artron);
		tag.putLong("EnergyPotential", this.potential);
		tag.putString("EnergyMode", this.mode.name());
		tag.putLong("TotalGenerated", this.totalEnergyGenerated);
		tag.putLong("TotalConsumed", this.totalEnergyConsumed);
		tag.putDouble("Efficiency", this.efficiency);
	}

	public void loadNBT(CompoundTag tag) {
		this.EnergyCap.setEnergy(tag.getInt("EnergyFE"));
		this.artron = tag.getLong("EnergyArtron");
		this.potential = tag.getLong("EnergyPotential");
		if (tag.contains("EnergyMode")) {
			this.mode = EnergyMode.valueOf(tag.getString("EnergyMode"));
		}
		this.totalEnergyGenerated = tag.getLong("TotalGenerated");
		this.totalEnergyConsumed = tag.getLong("TotalConsumed");
		if (tag.contains("Efficiency")) {
			this.efficiency = tag.getDouble("Efficiency");
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