/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.subsystems;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tardis.subsystems.OxygenatorCircuit;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenatorCoreBlock extends AbstractSubsystemBlock implements EntityBlock {
	public OxygenatorCoreBlock(Properties properties) {
		super(properties.strength(1.5f).sound(SoundType.METAL), new OxygenatorCircuit());
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return TTSTileEntities.OXYGENATOR_CIRCUIT.create(p_153215_, p_153216_);
	}
}
