/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.compat.cct.blocks;

import com.code.tama.tts.core.compat.cct.registry.CCTRegistry;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TardisCCInterfaceBlock extends Block implements EntityBlock {
	public TardisCCInterfaceBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return CCTRegistry.TARDIS_INTERFACE_TILE.create(p_153215_, p_153216_);
	}
}
