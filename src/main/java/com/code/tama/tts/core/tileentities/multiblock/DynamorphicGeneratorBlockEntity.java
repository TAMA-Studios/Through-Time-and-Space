/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.DynamorphicGeneratorStack;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DynamorphicGeneratorBlockEntity extends AbstractCircuitBlockEntity {

	public DynamorphicGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, new DynamorphicGeneratorStack());
	}

	@Override
	public List<BlockPos> getPositions() {
		return List.of();
	}
}
