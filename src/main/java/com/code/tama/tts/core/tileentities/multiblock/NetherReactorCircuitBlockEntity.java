/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class NetherReactorCircuitBlockEntity extends AbstractCircuitBlockEntity {

	public NetherReactorCircuitBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state, new NetherReactorCoreSubsystem());
	}

	@Override
	public List<BlockPos> getPositions() {
		return List.of();
	}
}
