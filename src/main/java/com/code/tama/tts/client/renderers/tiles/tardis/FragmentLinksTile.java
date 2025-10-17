/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class FragmentLinksTile extends BlockEntity {

	private final LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energyStorage);

	private final EnergyStorage energyStorage = new EnergyStorage(10000, 1000); // maxEnergy, maxReceive
	public FragmentLinksTile(BlockPos blockPos, BlockState blockState) {
		super(TTSTileEntities.FRAGMENT_LINKS_TILE.get(), blockPos, blockState);
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return energyCap.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		energyCap.invalidate();
	}
}
