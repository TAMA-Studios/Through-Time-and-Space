/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.tardis;

import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.core.blocks.core.VoxelRotatedShape;
import com.code.tama.tts.core.registries.forge.TTSTileEntities;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.core.tileentities.DecoyExteriorTile;
import com.code.tama.tts.core.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class DecoyExteriorBlock extends Block implements EntityBlock {

	public static final BooleanProperty DOORS = BooleanProperty.create("doors");
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final VoxelRotatedShape SHAPE_CLOSED = new VoxelRotatedShape(createShapeClosed().optimize());
	public static final VoxelRotatedShape SHAPE_OPEN = new VoxelRotatedShape(createShape().optimize());

	private final Supplier<? extends BlockEntityType<? extends DecoyExteriorTile>> exteriorType;

	public boolean IsMarkedForRemoval = false;

	public DecoyExteriorBlock(Properties p_49795_,
			Supplier<? extends BlockEntityType<? extends DecoyExteriorTile>> factory) {
		super(p_49795_);
		this.exteriorType = factory;
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(DOORS, false));
	}

	public static VoxelShape createShape() {
		return Stream
				.of(Block.box(0, 0, 0, 16, 0.5, 16), Block.box(0, 31.5, 0, 16, 32, 16),
						Block.box(0, 0.5, 2.5, 16, 31.5, 16), Block.box(16, 0, -0.5, 16.5, 32, 16.5),
						Block.box(-0.5, 0, -0.5, 0, 32, 16.5), Block.box(0, 0, 16, 16, 32, 16.5))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}

	public static VoxelShape createShapeClosed() {
		// Bottom half (y 0–16)
		VoxelShape bottom = Stream
				.of(Block.box(0, 0, -0.5, 16, 0.5, 16), Block.box(0, 15.5, -0.5, 16, 16, 16),
						Block.box(0, 0.5, -0.5, 16, 15.5, 16), Block.box(16, 0, -0.5, 16.5, 16, 16.5),
						Block.box(-0.5, 0, -0.5, 0, 16, 16.5), Block.box(0, 0, 16, 16, 16, 16.5))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

		// Top half (y 16–32)
		VoxelShape top = Stream
				.of(Block.box(0, 16, -0.5, 16, 16.5, 16), Block.box(0, 31.5, -0.5, 16, 32, 16),
						Block.box(0, 16.5, -0.5, 16, 31.5, 16), Block.box(16, 16, -0.5, 16.5, 32, 16.5),
						Block.box(-0.5, 16, -0.5, 0, 32, 16.5), Block.box(0, 16, 16, 16, 32, 16.5))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

		return Shapes.join(bottom, top, BooleanOp.OR);
	}

	public static VoxelShape createShapeB() {
		return Stream
				.of(Block.box(0, 0, 0, 1, 32, 16), Block.box(15, 0, 0, 16, 32, 16), Block.box(1, 0, 15, 15, 32, 16),
						Block.box(1, 0, 0, 15, 1, 15), Block.box(1, 31, 0, 15, 32, 15))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}

	@Override
	public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter,
			@NotNull BlockPos pos, @NotNull CollisionContext context) {
		// Reuse the same logic as getShape, collision needs the split shapes too
		return getShape(state, getter, pos, context);
	}

	public static VoxelShape createShapeClosedB() {
		return Block.box(0, 0, 0, 16, 32, 16).optimize();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
		super.createBlockStateDefinition(StateDefinition);
		StateDefinition.add(FACING);
		StateDefinition.add(DOORS);
	}

	// @Override
	// public void onPlace(@NotNull BlockState State, Level level, @NotNull BlockPos
	// Pos, @NotNull BlockState State2, boolean p_60570_) {
	//
	// super.onPlace(State, level, Pos, State2, p_60570_);
	// }

	public void MarkForRemoval() {
		this.IsMarkedForRemoval = true;
	}

	@Override
	public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos,
			@NotNull CollisionContext context) {
		if (getter.getBlockEntity(pos) == null)
			return SHAPE_OPEN.GetShapeFromRotation(state.getValue(FACING));
		return state.getValue(DOORS)
				? SHAPE_OPEN.GetShapeFromRotation(state.getValue(FACING)).move(0,
						BlockUtils.getReverseHeightModifier(getter.getBlockState(pos.below())), 0)
				: SHAPE_CLOSED.GetShapeFromRotation(state.getValue(FACING)).move(0,
						BlockUtils.getReverseHeightModifier(getter.getBlockState(pos.below())), 0);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state,
			@NotNull BlockEntityType<T> type) {
		return type == TTSTileEntities.EXTERIOR_TILE.get() ? ExteriorTile::tick : null;
	}

	@Nullable @Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return this.exteriorType.get().create(pos, state);
	}

	@Override
	public void setPlacedBy(Level level, @NotNull BlockPos Pos, @NotNull BlockState State,
			@Nullable LivingEntity livingEntity, @NotNull ItemStack stack) {
		if (level.getBlockEntity(Pos) instanceof DecoyExteriorTile exteriorTile) {
			exteriorTile.setModel(ThreadLocalRandom.current().nextInt(ExteriorsRegistry.EXTERIORS.size()));
		}
		super.setPlacedBy(level, Pos, State, livingEntity, stack);
	}

	@Override
	public boolean skipRendering(@NotNull BlockState state, BlockState adjacentBlockState, @NotNull Direction side) {
		return adjacentBlockState.is(this); // Avoids rendering internal faces
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos,
			@NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {

		if (level.getBlockEntity(blockPos) != null
				&& level.getBlockEntity(blockPos) instanceof DecoyExteriorTile exteriorTile) {
			exteriorTile.CycleDoors();

			if (!level.isClientSide) {
				exteriorTile.CycleDoors();
				level.setBlockAndUpdate(blockPos, blockState.setValue(DOORS, exteriorTile.DoorsOpen() != 0));
			}
		}
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}

	// Rotation-ey stuffs

	public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}
