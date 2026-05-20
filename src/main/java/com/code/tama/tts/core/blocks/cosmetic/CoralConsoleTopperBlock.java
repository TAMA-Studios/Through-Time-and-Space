/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.cosmetic;

import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.core.tileentities.CoralConsoleTopperTile;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Slf4j
public class CoralConsoleTopperBlock extends Block implements EntityBlock {
	public CoralConsoleTopperBlock(Properties blockBehaviour) {
		super(blockBehaviour);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new CoralConsoleTopperTile(TTSTileEntities.CORAL_CONSOLE_TOPPER.get(), pos, state);
	}
}
