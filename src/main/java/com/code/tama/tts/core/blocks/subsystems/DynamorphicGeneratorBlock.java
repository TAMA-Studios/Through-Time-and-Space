/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.subsystems;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicGeneratorStack;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DynamorphicGeneratorBlock extends AbstractSubsystemBlock implements EntityBlock {
	public DynamorphicGeneratorBlock(Properties p_49795_) {
		super(p_49795_, new DynamorphicGeneratorStack());
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TTSTileEntities.DYNAMO_GEN.create(p_153215_, p_153216_);
	}
}
