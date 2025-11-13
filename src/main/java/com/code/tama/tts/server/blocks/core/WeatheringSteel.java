/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.core;

import java.util.Optional;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface WeatheringSteel extends ChangeOverTimeBlock<WeatheringSteel.WeatherState> {
	static Block getFirst(Block block) {
		Block prev;
		while ((prev = getPrevious(block)) != null) {
			block = prev;
		}
		return block;
	}

	static BlockState getFirst(BlockState state) {
		return getFirst(state.getBlock()).withPropertiesOf(state);
	}

	@Nullable static Block getNext(Block block) {
		if (block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL.get()))
			return TTSBlocks.BRUSHED_STRUCTURAL_STEEL_WEATHERED.get();
		if (block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_WEATHERED.get()))
			return TTSBlocks.BRUSHED_STRUCTURAL_STEEL_RUSTED.get();
		return null;
	}

	@Nullable static Block getPrevious(Block block) {
		if (block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_RUSTED.get()))
			return TTSBlocks.BRUSHED_STRUCTURAL_STEEL_WEATHERED.get();
		if (block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_WEATHERED.get()))
			return TTSBlocks.BRUSHED_STRUCTURAL_STEEL.get();
		return null;
	}

	@Nullable static BlockState getPrevious(BlockState state) {
		if (!prevExists(state.getBlock()))
			return null;

		Block prev = getPrevious(state.getBlock());
		return prev != null ? prev.withPropertiesOf(state) : null;
	}

	static boolean nextExists(@NotNull Block block) {
		return !block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL_RUSTED.get());
	}

	static boolean prevExists(@NotNull Block block) {
		return !block.equals(TTSBlocks.BRUSHED_STRUCTURAL_STEEL.get());
	}

	default float getChanceModifier() {
		return this.getAge() == WeatheringSteel.WeatherState.UNAFFECTED
				? 0.5F
				: this.getAge() == WeatherState.WEATHERED ? 0.75F : 1.0f;
	}

	default @NotNull Optional<BlockState> getNext(@NotNull BlockState state) {
		return Optional.ofNullable(getNextNV(state));
	}

	default @Nullable BlockState getNextNV(BlockState state) {
		Block next = getNext(state.getBlock());
		return next != null ? next.withPropertiesOf(state) : null;
	}

	enum WeatherState {
		RUSTED, UNAFFECTED, WEATHERED
	}
}
