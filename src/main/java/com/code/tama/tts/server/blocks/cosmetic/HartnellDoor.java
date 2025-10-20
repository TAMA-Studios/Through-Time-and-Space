/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.cosmetic;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.code.tama.tts.server.registries.forge.TTSBlocks;
import com.code.tama.tts.server.tileentities.HartnellDoorTile;
import com.code.tama.tts.server.tileentities.HartnellDoorTilePlaceholder;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class HartnellDoor extends Block implements EntityBlock {
	private boolean IsOpen = false;
	private final Supplier<? extends BlockEntityType<? extends HartnellDoorTile>> tile;

	public HartnellDoor(Supplier<? extends BlockEntityType<? extends HartnellDoorTile>> factory) {
		super(Properties.of().strength(3.0F).requiresCorrectToolForDrops());
		this.tile = factory;
	}

	@Override
	protected boolean isAir(@NotNull BlockState state) {
		return false;
	}

	private boolean placeMultiblockStructure(ServerLevel level, BlockPos pos) {
		BlockState blockToPlace = TTSBlocks.HARTNELL_DOOR_PLACEHOLDER.get().defaultBlockState(); // Use the same block
		// for all parts

		// Check if space is clear
		for (int y = 0; y < 3; y++) { // Height (3)
			for (int x = 0; x < 2; x++) { // Width (2)
				BlockPos placePos = pos.offset(x, y, 0);
				if (placePos != pos)
					if (!level.getBlockState(placePos).isAir()) { // Prevent placing over existing blocks
						return false;
					}
			}
		}

		// Place blocks in a 2x3 structure
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 2; x++) {
				BlockPos placePos = pos.offset(x, y, 0);
				if (placePos != pos) {
					level.setBlockAndUpdate(placePos, blockToPlace);
					((HartnellDoorMultiBlock) blockToPlace.getBlock()).SetController(pos);
					((HartnellDoorTilePlaceholder) level.getBlockEntity(placePos)).Master = pos;
				}
			}
		}

		return true;
	}

	public boolean IsOpen() {
		return this.IsOpen;
	}

	public void SetIsOpen(boolean IsOpen) {
		this.IsOpen = IsOpen;
	}

	@Override
	public void destroy(LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockState state) {
		if (!levelAccessor.isClientSide())
			this.destroyMultiblockStructure((ServerLevel) levelAccessor, blockPos);
		super.destroy(levelAccessor, blockPos, state);
	}

	public boolean destroyMultiblockStructure(ServerLevel level, BlockPos pos) {
		BlockState blockToPlace = Blocks.AIR.defaultBlockState();

		// Check if space is clear
		for (int y = 0; y < 3; y++) { // Height (3)
			for (int x = 0; x < 2; x++) { // Width (2)
				BlockPos placePos = pos.offset(x, y, 0);
				if (placePos != pos)
					if (!level.getBlockState(placePos).isAir()) {
						level.setBlockAndUpdate(placePos, blockToPlace);
						return true;
					}
			}
		}

		return false;
	}

	@Override
	public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter,
			@NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		if (this.IsOpen)
			return Shapes.empty();
		return super.getCollisionShape(state, blockGetter, blockPos, collisionContext);
	}

	@Nullable @Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return this.tile.get().create(pos, state);
	}

	@Override
	public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState,
			boolean isMoving) {
		if (!level.isClientSide) {
			if (placeMultiblockStructure(level.getServer().getLevel(level.dimension()), pos)) {
				level.playSound(null, pos, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
				System.out.println("Multiblock door automatically placed!");
			}
		}
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level,
			@NotNull BlockPos blockPos, @NotNull Player player, InteractionHand p_60507_,
			@NotNull BlockHitResult blockHitResult) {
		if (p_60507_.equals(InteractionHand.OFF_HAND))
			return InteractionResult.PASS;
		if (level.isClientSide)
			return InteractionResult.PASS;

		System.out.println(this.IsOpen);
		this.IsOpen = !this.IsOpen;
		HartnellDoorTile hartnellDoorTile = ((HartnellDoorTile) level.getBlockEntity(blockPos));
		assert hartnellDoorTile != null;
		hartnellDoorTile.SetIsOpen(this.IsOpen);
		level.updateNeighborsAt(blockPos, TTSBlocks.HARTNELL_DOOR_PLACEHOLDER.get());

		level.sendBlockUpdated(blockPos, blockState, blockState, UPDATE_ALL);
		return InteractionResult.SUCCESS;
	}
}
