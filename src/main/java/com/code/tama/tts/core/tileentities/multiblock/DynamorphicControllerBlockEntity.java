/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DynamorphicControllerBlockEntity extends AbstractCircuitBlockEntity {

	public DynamorphicControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, new DynamorphicController());
	}

	@Override
	public List<BlockPos> getPositions() {
		return List.of();
	}
}
