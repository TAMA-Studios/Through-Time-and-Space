/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.tileentities;

import java.util.Optional;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class TARDISEnergyPortBlockEntity extends BlockEntity {

	private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(this::createEnergyStorage);

	public TARDISEnergyPortBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	private IEnergyStorage createEnergyStorage() {
		return new IEnergyStorage() {

			@Override
			public int receiveEnergy(int receive, boolean simulate) {
				return Math.toIntExact(getTardis().map(t -> t.getEnergy().receiveEnergy(receive, simulate)).orElse(0L));
			}

			@Override
			public int extractEnergy(int extract, boolean simulate) {
				return Math.toIntExact(getTardis().map(t -> t.getEnergy().extractEnergy(extract, simulate)).orElse(0L));
			}

			@Override
			public int getEnergyStored() {
				return Math.toIntExact(getTardis().map(t -> t.getEnergy().getEnergy()).orElse(0L));
			}

			@Override
			public int getMaxEnergyStored() {
				return getTardis().map(t -> t.getEnergy().getMaxEnergy()).orElse(0);
			}

			@Override
			public boolean canExtract() {
				return true;
			}

			@Override
			public boolean canReceive() {
				return true;
			}
		};
	}

	private Optional<ITARDISLevel> getTardis() {
		if (level == null || level.isClientSide)
			return Optional.empty();

		return TARDISLevelCapability.GetTARDISCapSupplier(level).resolve();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY)
			return energyCap.cast();

		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		energyCap.invalidate();
	}
}
