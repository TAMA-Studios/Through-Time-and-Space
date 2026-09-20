/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.tileentities.multiblock;

import java.util.List;

import com.code.tama.tts.core.registries.forge.TTSBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ImAMultiblock {
	/** Called when a slave multiblock is removed */
	void onSlaveRemoved();

	/** Removes the entire multiblock, all slaves and the master. */
	default void removeThis(Level level, BlockPos masterPos) {
		getPositions().forEach((localPos) -> {
			BlockPos worldPos = masterPos.offset(localPos);
			level.removeBlock(worldPos, false);
		});
		level.removeBlock(masterPos, false);
	}

	default void onCreate(BlockPos masterPos, Level level) {
		getPositions().forEach((localPos) -> {
			BlockPos worldPos = masterPos.offset(localPos);
			if (worldPos.equals(masterPos))
				return;
			level.setBlockAndUpdate(worldPos, TTSBlocks.MULTIBLOCK_SLAVE.getDefaultState());
			if (level.getBlockEntity(worldPos) instanceof AverageMultiblockSlaveTile t)
				t.setMaster(masterPos);
		});
	}

	/**
	 * @return A map of relative positions to this multiblock where the decoy blocks
	 *         should be
	 */
	List<BlockPos> getPositions();
}
