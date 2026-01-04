/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import java.util.function.Supplier;

import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.TARDISEnergyPortBlockEntity;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TARDISEnergyPort extends Block implements EntityBlock {

	public TARDISEnergyPort(Properties p_49795_) {
		super(p_49795_);
	}

	public TARDISEnergyPort(Properties p_49795_,
			Supplier<? extends BlockEntityType<? extends TARDISEnergyPortBlockEntity>> factory) {
		super(p_49795_);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TTSTileEntities.TARDIS_ENERGY_PORT.get().create(p_153215_, p_153216_);
	}
}
