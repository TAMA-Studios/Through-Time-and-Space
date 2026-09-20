/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DematCircuitBlockEntity extends AbstractCircuitBlockEntity {

	public DematCircuitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, new DematerializationCircuit());
	}

	@Override
	public List<BlockPos> getPositions() {
		BlockPos Z = BlockPos.ZERO;
		return List.of(Z.south(), Z.east(), Z.west(),

				Z.east().north(),

				Z.west().north(),

				Z.west().south(),

				Z.east().south());
	}
}
