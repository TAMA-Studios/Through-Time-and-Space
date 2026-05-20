/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks;

import java.util.UUID;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import com.code.tama.tts.core.tileentities.TardisCoralTile;
import com.code.tama.tts.server.tardis.ExteriorState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class TardisCoralBlock extends Block implements EntityBlock {
	private boolean Destroy = false;

	public TardisCoralBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity player,
			ItemStack stack) {
		super.setPlacedBy(level, blockPos, blockState, player, stack);

		level.scheduleTick(blockPos, blockState.getBlock(), 12000);
		Destroy = true;

		if (level.getBlockEntity(blockPos) instanceof TardisCoralTile coral) {
			coral.placerName = player.getName().getString();
			coral.placerUUID = player.getUUID();
		}
	}

	@Override
	public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos,
			@NotNull RandomSource randomSource) {
		super.tick(blockState, serverLevel, blockPos, randomSource);
		if (Destroy) {
			placeTARDIS((TardisCoralTile) serverLevel.getBlockEntity(blockPos));
			serverLevel.removeBlock(blockPos, true);
		}
	}

	private static boolean placeTARDIS(TardisCoralTile tardisCoralTile) {
		if (tardisCoralTile == null)
			throw new RuntimeException("Oopsies");
		UUID playerUUID = tardisCoralTile.placerUUID;
		String placerName = tardisCoralTile.placerName;

		BlockPos blockPos = tardisCoralTile.getBlockPos();
		Level level = tardisCoralTile.getLevel();

		BlockState state1 = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();
		ExteriorTile tile = TTSTileEntities.EXTERIOR_TILE.create(blockPos, state1);

		tile.PlacerName = placerName;// player.getName().getString();
		tile.PlacerUUID = playerUUID;// player.getUUID();
		tile.state = ExteriorState.LANDED;
		tile.isArtificial = false;

		level.setBlockAndUpdate(blockPos, state1);
		level.setBlockEntity(tile);

		tile.ShouldMakeDimOnNextTick = true;

		return true;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return TTSTileEntities.TARDIS_CORAL.create(blockPos, blockState);
	}
}
