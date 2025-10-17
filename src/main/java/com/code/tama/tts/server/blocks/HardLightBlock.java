/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class HardLightBlock extends Block {
	private boolean Destroy = false;

	public HardLightBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	public void onPlace(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos,
			@NotNull BlockState blockState1, boolean b) {
		super.onPlace(blockState, level, blockPos, blockState1, b);
		level.scheduleTick(blockPos, blockState.getBlock(), 1200);
		Destroy = true;
	}

	@Override
	public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos,
			@NotNull RandomSource randomSource) {
		super.tick(blockState, serverLevel, blockPos, randomSource);
		if (Destroy)
			serverLevel.removeBlock(blockPos, true);
	}
}
