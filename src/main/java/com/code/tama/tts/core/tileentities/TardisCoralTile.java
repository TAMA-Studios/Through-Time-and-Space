/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TardisCoralTile extends BlockEntity {
	public UUID placerUUID;
	public String placerName;

	public TardisCoralTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
