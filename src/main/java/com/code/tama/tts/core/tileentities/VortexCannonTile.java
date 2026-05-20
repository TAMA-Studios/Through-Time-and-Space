/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.tileentities;

import java.util.UUID;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tardis.ExteriorState;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import com.code.tama.triggerapi.boti.teleporting.TickScheduler;

public class VortexCannonTile extends BlockEntity {
	public AnimationState state = new AnimationState();
	public VortexCannonTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public void FIREAnimation() {
		this.state.startIfStopped((int) level.getGameTime());
	}

	public void FIRE(Player launcher) {
		FIREAnimation();

		if (this.level.isClientSide)
			return;

		TickScheduler.runAfter(600, () -> {
			placeTARDIS(launcher.getUUID(), launcher.getName().getString());
		});
		// this.level.getServer().tell(new TickTask(600, () -> {
		// placeTARDIS(launcher.getUUID(), launcher.getName().getString());
		// }));
	}

	private boolean placeTARDIS(UUID playerUUID, String placerName) {

		BlockPos blockPos = this.getBlockPos().north(3);
		Level level = this.getLevel();

		BlockState state1 = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();
		ExteriorTile tile = TTSTileEntities.EXTERIOR_TILE.create(blockPos, state1);

		tile.PlacerName = placerName;
		tile.PlacerUUID = playerUUID;
		tile.state = ExteriorState.LANDED;
		tile.isArtificial = false;

		level.setBlockAndUpdate(blockPos, state1);
		level.setBlockEntity(tile);

		tile.ShouldMakeDimOnNextTick = true;

		return true;
	}
}
