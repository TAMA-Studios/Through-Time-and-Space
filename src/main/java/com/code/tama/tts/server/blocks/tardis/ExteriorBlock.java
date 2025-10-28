/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.tardis;

import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.server.blocks.core.VoxelRotatedShape;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.registries.forge.TTSItems;
import com.code.tama.tts.server.registries.forge.TTSTileEntities;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class ExteriorBlock extends FallingBlock implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final VoxelRotatedShape SHAPE_CLOSED = new VoxelRotatedShape(createShapeClosed().optimize());
	public static final VoxelRotatedShape SHAPE_OPEN = new VoxelRotatedShape(createShape().optimize());

	private ResourceKey<Level> LevelKey;

	private final Supplier<? extends BlockEntityType<? extends ExteriorTile>> exteriorType;

	public boolean IsMarkedForRemoval = false;

	public ExteriorBlock(Properties p_49795_, Supplier<? extends BlockEntityType<? extends ExteriorTile>> factory) {
		super(p_49795_);
		this.exteriorType = factory;
		this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
	}

	public static VoxelShape createShape() {
		return Stream
				.of(Block.box(0, 0, 0, 16, 0.5, 16), Block.box(0, 31.5, 0, 16, 32, 16),
						Block.box(0, 0.5, 2.5, 16, 31.5, 16), Block.box(16, 0, -0.5, 16.5, 32, 16.5),
						Block.box(-0.5, 0, -0.5, 0, 32, 16.5), Block.box(0, 0, 16, 16, 32, 16.5))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}

	public static VoxelShape createShapeClosed() {
		return Stream
				.of(Block.box(0, 0, -0.5, 16, 0.5, 16), Block.box(0, 31.5, -0.5, 16, 32, 16),
						Block.box(0, 0.5, -0.5, 16, 31.5, 16), Block.box(16, 0, -0.5, 16.5, 32, 16.5),
						Block.box(-0.5, 0, -0.5, 0, 32, 16.5), Block.box(0, 0, 16, 16, 32, 16.5))
				.reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
		super.createBlockStateDefinition(StateDefinition);
		StateDefinition.add(FACING);
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

	public void SetInteriorKey(ResourceKey<Level> levelKey) {
		this.LevelKey = levelKey;
	}

	public void TeleportToInterior(Entity EntityToTeleport, BlockPos pos) {
		if (EntityToTeleport.level().getBlockEntity(pos) instanceof ExteriorTile exteriorTile
				&& exteriorTile.DoorsOpen() > 0)
			exteriorTile.TeleportToInterior(EntityToTeleport);
	}

	@Override
	public void entityInside(BlockState state, @NotNull Level level, @NotNull BlockPos pos, Entity entity) {
		if (entity.getBoundingBox().intersects(state.getShape(level, pos).bounds().move(pos))) {
			this.TeleportToInterior(entity, pos);
		}
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
		return ((ExteriorTile) getter.getBlockEntity(pos)).DoorsOpen() > 0
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
		if (level.isClientSide || level.getServer() == null)
			return;
		if (level.getBlockEntity(Pos) instanceof ExteriorTile exteriorTile) {
			exteriorTile.ShouldMakeDimOnNextTick = false;
			exteriorTile.IsEmptyShell = true;
			exteriorTile.Placer = livingEntity;
			exteriorTile.isArtificial = true;
		}
		super.setPlacedBy(level, Pos, State, livingEntity, stack);
	}

	@Override
	public boolean skipRendering(@NotNull BlockState state, BlockState adjacentBlockState, @NotNull Direction side) {
		return adjacentBlockState.is(this); // Avoids rendering internal faces
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
			@NotNull RandomSource randomSource) {
		if (this.IsMarkedForRemoval) {
			level.removeBlockEntity(pos);
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			return;
		}
		super.tick(state, level, pos, randomSource);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos,
			@NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
		if (level.getBlockEntity(blockPos) != null
				&& level.getBlockEntity(blockPos) instanceof ExteriorTile exteriorTile) {

			if (exteriorTile.IsEmptyShell && exteriorTile.getWeldProgress() != 100
					&& exteriorTile.GetInterior() == null) {
				ItemStack used = player.getItemInHand(interactionHand);
				if (used.getItem().equals(TTSItems.GROWTH_CAKE.get())) {
					used.shrink(1);
					exteriorTile.ShouldMakeDimOnNextTick = true;
				}

				if (used.getItem().equals(TTSItems.STRUCTURAL_BEAMS.get()) && exteriorTile.StructuralBeams < 4) {
					exteriorTile.StructuralBeams++;
					used.shrink(1);
				}

				if (used.getItem().equals(TTSItems.PLASMIC_SHELL_PLATING.get())
						&& exteriorTile.PlasmicShellPlates < 5) {
					// Make sure you have more structural beams than you do plasmic shell plates
					if (exteriorTile.StructuralBeams - exteriorTile.PlasmicShellPlates >= 1) {
						exteriorTile.PlasmicShellPlates++;
						used.shrink(1);
					}
				}

				if (exteriorTile.PlasmicShellPlates == 4 && exteriorTile.StructuralBeams == 4
						&& exteriorTile.getWeldProgress() == 100) {
					exteriorTile.ShouldMakeDimOnNextTick = true;
				}

				System.out.printf("Beams: %s, Plates: %s, Weld: %s%n", exteriorTile.StructuralBeams,
						exteriorTile.PlasmicShellPlates, exteriorTile.getWeldProgress());

				return InteractionResult.SUCCESS;
			}

			else if (!level.isClientSide && exteriorTile.GetInterior() != null)
				level.getServer().getLevel(exteriorTile.GetInterior())
						.getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
						.ifPresent(cap -> cap.GetData().getInteriorDoorData().CycleDoor());

			exteriorTile.CycleDoors();
		}
		return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull BlockState state1, boolean simulated) {
		super.onPlace(state, level, pos, state1, simulated);

		if (state.hasBlockEntity()) {
			if (level.getBlockEntity(pos) instanceof ExteriorTile exteriorTile) {
				exteriorTile.isArtificial = true;
			}
		}
	}

	// Rotation-ey stuffs

	public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
}
