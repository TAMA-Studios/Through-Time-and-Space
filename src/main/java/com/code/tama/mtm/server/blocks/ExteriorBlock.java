package com.code.tama.mtm.server.blocks;

import com.code.tama.mtm.server.MTMTileEntities;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.code.tama.triggerapi.dimensions.DimensionAPI;
import com.code.tama.triggerapi.dimensions.DimensionManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
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
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.code.tama.mtm.MTMMod.MODID;

@SuppressWarnings("deprecation")
public class ExteriorBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private ResourceKey<Level> LevelKey;
    public static final VoxelRotatedShape SHAPE_OPEN = new VoxelRotatedShape(createShape().optimize());
    public static final VoxelRotatedShape SHAPE_CLOSED = new VoxelRotatedShape(createShapeClosed().optimize());
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final Supplier<? extends BlockEntityType<? extends ExteriorTile>> exteriorType;
    public boolean IsMarkedForRemoval = false;

    public ExteriorBlock(Properties p_49795_, Supplier<? extends BlockEntityType<? extends ExteriorTile>> factory) {
        super(p_49795_);
        this.exteriorType = factory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.exteriorType.get().create(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == MTMTileEntities.EXTERIOR_TILE.get() ? ExteriorTile::tick : null;
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos Pos, @NotNull BlockState State, @org.jetbrains.annotations.Nullable LivingEntity livingEntity, @NotNull ItemStack stack) {
        if (level.isClientSide || level.getServer() == null) return;
        level.getServer().execute(() -> {
            ResourceKey<Level> resourceKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(MODID, "tardis_" + UUID.randomUUID()));
            ServerLevel tardisLevel = DimensionAPI.get().getOrCreateLevel(
                    level.getServer(), resourceKey, () -> DimensionManager.CreateTARDISLevelStem(level.getServer()));
            this.SetInteriorKey(tardisLevel.dimension());

            if (level.getBlockEntity(Pos) instanceof ExteriorTile exteriorTile) {
                exteriorTile.Init(tardisLevel.dimension());

                tardisLevel.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent((cap) -> {
                    cap.SetExteriorTile(exteriorTile);
                    cap.SetCurrentLevel(exteriorTile.getLevel().dimension());
                    cap.SetDestination(new SpaceTimeCoordinate(exteriorTile.getBlockPos()));
                    cap.SetExteriorLocation(new SpaceTimeCoordinate(exteriorTile.getBlockPos()));
                });
            }
        });
        super.setPlacedBy(level, Pos, State, livingEntity, stack);
    }

//    @Override
//    public void onPlace(@NotNull BlockState State, Level level, @NotNull BlockPos Pos, @NotNull BlockState State2, boolean p_60570_) {
//
//        super.onPlace(State, level, Pos, State2, p_60570_);
//    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if(getter.getBlockEntity(pos) == null) return SHAPE_OPEN.GetShapeFromRotation(state.getValue(FACING));
        return ((ExteriorTile) getter.getBlockEntity(pos)).DoorsOpen() > 0 ? SHAPE_OPEN.GetShapeFromRotation(state.getValue(FACING)) : SHAPE_CLOSED.GetShapeFromRotation(state.getValue(FACING));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if(level.getBlockEntity(blockPos) != null) {
            ExteriorTile exteriorTile = ((ExteriorTile) level.getBlockEntity(blockPos));
            exteriorTile.SetDoorsOpen(exteriorTile.DoorsOpen());
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
        super.createBlockStateDefinition(StateDefinition);
        StateDefinition.add(FACING);
    }

    @Override
    public void entityInside(BlockState state, @NotNull Level level, @NotNull BlockPos pos, Entity entity) {
        if (entity.getBoundingBox().intersects(state.getShape(level, pos).bounds().move(pos))) {
            this.TeleportToInterior(entity, pos);
        }
    }

    public void TeleportToInterior(Entity EntityToTeleport, BlockPos pos) {
        if (EntityToTeleport.level().getBlockEntity(pos) instanceof ExteriorTile exteriorTile)
            exteriorTile.TeleportToInterior(EntityToTeleport);
    }

    public void SetInteriorKey(ResourceKey<Level> levelKey) {
        this.LevelKey = levelKey;
    }

    public static VoxelShape createShape() {
        return Stream.of(
                Block.box(0, 0, 0, 16, 0.5, 16),
                Block.box(0, 31.5, 0, 16, 32, 16),
                Block.box(0, 0.5, 2.5, 16, 31.5, 16),
                Block.box(16, 0, -0.5, 16.5, 32, 16.5),
                Block.box(-0.5, 0, -0.5, 0, 32, 16.5),
                Block.box(0, 0, 16, 16, 32, 16.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    public static VoxelShape createShapeClosed() {
        return Stream.of(
                Block.box(0, 0, -0.5, 16, 0.5, 16),
                Block.box(0, 31.5, -0.5, 16, 32, 16),
                Block.box(0, 0.5, -0.5, 16, 31.5, 16),
                Block.box(16, 0, -0.5, 16.5, 32, 16.5),
                Block.box(-0.5, 0, -0.5, 0, 32, 16.5),
                Block.box(0, 0, 16, 16, 32, 16.5)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    }

    public void MarkForRemoval() {
        this.IsMarkedForRemoval = true;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource randomSource) {
        if (this.IsMarkedForRemoval) {
            level.removeBlockEntity(pos);
            level.removeBlock(pos, false);
            return;
        }
        super.tick(state, level, pos, randomSource);
    }

    @Override
    public boolean skipRendering(@NotNull BlockState state, BlockState adjacentBlockState, @NotNull Direction side) {
        return adjacentBlockState.is(this); // Avoids rendering internal faces
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}