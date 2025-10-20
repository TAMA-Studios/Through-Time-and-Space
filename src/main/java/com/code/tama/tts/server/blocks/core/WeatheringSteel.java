/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.core;

import java.util.Optional;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface WeatheringSteel extends ChangeOverTimeBlock<WeatheringSteel.WeatherState> {
	BiMap<RegistryObject<Block>, RegistryObject<Block>> NEXT_BY_BLOCK = ImmutableBiMap
			.<RegistryObject<Block>, RegistryObject<Block>>builder()
			.put(TTSBlocks.STRUCTURAL_STEEL, TTSBlocks.STRUCTURAL_STEEL_WEATHERED)
			.put(TTSBlocks.STRUCTURAL_STEEL_WEATHERED, TTSBlocks.STRUCTURAL_STEEL_RUSTED).build();

	BiMap<RegistryObject<Block>, RegistryObject<Block>> PREVIOUS_BY_BLOCK = NEXT_BY_BLOCK.inverse();

	@Nullable private static RegistryObject<Block> RegFromBlock(Block block) {
		RegistryObject<Block> reg = RegistryObject.create(ForgeRegistries.BLOCKS.getKey(block), ForgeRegistries.BLOCKS);
		return reg.get() == Blocks.AIR
				? null
				: RegistryObject.create(ForgeRegistries.BLOCKS.getKey(block), ForgeRegistries.BLOCKS);
	}

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
		if (!nextExists(block))
			return null;

		Block next = NEXT_BY_BLOCK.get(RegFromBlock(block)).get();
		return next == Blocks.AIR ? null : next;
	}

	@Nullable static Block getPrevious(Block block) {
		if (!prevExists(block))
			return null;

		Block prev = PREVIOUS_BY_BLOCK.get(RegFromBlock(block)).get();
		return prev == Blocks.AIR ? null : prev;
	}

	@Nullable static BlockState getPrevious(BlockState state) {
		if (!prevExists(state.getBlock()))
			return null;

		Block prev = getPrevious(state.getBlock());
		return prev != null ? prev.withPropertiesOf(state) : null;
	}

	static boolean nextExists(@NotNull Block block) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get(RegFromBlock(block))).isPresent();
	}

	static boolean prevExists(@NotNull Block block) {
		return Optional.ofNullable(PREVIOUS_BY_BLOCK.get(RegFromBlock(block))).isPresent();
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
