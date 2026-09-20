/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.subsystems;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class NetherReactorCoreBlock extends AbstractSubsystemBlock implements EntityBlock {
	public NetherReactorCoreBlock(Properties p_49795_) {
		super(p_49795_, new NetherReactorCoreSubsystem());
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TTSTileEntities.DYNAMO_GEN.create(p_153215_, p_153216_);
	}
}
