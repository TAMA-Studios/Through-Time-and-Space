/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.tileEntities;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class EnergyHoldingTile extends BlockEntity {
	public abstract int capacity();
	public abstract int maxReceive();
	public abstract int maxTransmit();
	public abstract boolean canExtract();
	public abstract boolean canReceive();

	@Getter
	@Setter
	int storedEnergy = 0;

	private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(this::createEnergyStorage);

	public EnergyHoldingTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	private IEnergyStorage createEnergyStorage() {
		EnergyHoldingTile ent = this; // Store this so we can call "ent.canExtract()" and etc inside the new
										// IEnergyStorage without recursing infinitely

		return new IEnergyStorage() {

			@Override
			public int receiveEnergy(int receive, boolean simulate) {
				if (!canReceive())
					return 0;

				int energyReceived = Math.min(capacity() - storedEnergy, Math.min(capacity(), receive));
				if (!simulate)
					storedEnergy += energyReceived;
				return energyReceived;
			}

			@Override
			public int extractEnergy(int extract, boolean simulate) {
				if (!canExtract())
					return 0;

				int energyExtracted = Math.min(storedEnergy, Math.min(capacity(), extract));
				if (!simulate)
					storedEnergy -= energyExtracted;
				return energyExtracted;
			}

			@Override
			public int getEnergyStored() {
				return ent.storedEnergy;
			}

			@Override
			public int getMaxEnergyStored() {
				return ent.capacity();
			}

			@Override
			public boolean canExtract() {
				return ent.canExtract();
			}

			@Override
			public boolean canReceive() {
				return ent.canReceive();
			}
		};
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY)
			return energyCap.cast();

		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energyCap.invalidate();
	}

	@Override
	protected void saveAdditional(CompoundTag p_187471_) {
		p_187471_.putInt("energy", this.storedEnergy);
		super.saveAdditional(p_187471_);
	}

	@Override
	public void load(CompoundTag p_155245_) {
		this.storedEnergy = p_155245_.getInt("energy");
		super.load(p_155245_);
	}
}
