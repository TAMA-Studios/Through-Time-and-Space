/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.blocks.subsystems.AbstractSubsystemBlock;
import com.code.tama.tts.server.enums.SonicInteractionType;
import com.code.tama.tts.server.items.SonicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FragmentLinksBlock extends Block {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public FragmentLinksBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition
                .any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos currentPos,
            BlockPos neighborPos) {
        boolean canConnect = canConnectTo(level, neighborPos, direction);
        return state.setValue(getPropertyForDirection(direction), canConnect);
    }

    private BooleanProperty getPropertyForDirection(Direction direction) {
        switch (direction) {
            case NORTH:
                return NORTH;
            case EAST:
                return EAST;
            case SOUTH:
                return SOUTH;
            case WEST:
                return WEST;
            case UP:
                return UP;
            case DOWN:
                return DOWN;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private boolean canConnectTo(LevelAccessor level, BlockPos neighborPos, Direction direction) {
        BlockState neighborState = level.getBlockState(neighborPos);
        return neighborState.getBlock() instanceof FragmentLinksBlock
                || neighborState.getBlock() instanceof AbstractSubsystemBlock
                || neighborState.getBlock() instanceof EngineBlock;
    }

    private static void makeParticle(LevelAccessor levelAccessor, BlockPos blockPos) {
        Direction direction = Direction.NORTH;
        Direction direction1 = Direction.NORTH;
        double d0 = (double) blockPos.getX()
                + 0.5D
                + 0.1D * (double) direction.getStepX()
                + 0.2D * (double) direction1.getStepX();
        double d1 = (double) blockPos.getY()
                + 0.5D
                + 0.1D * (double) direction.getStepY()
                + 0.2D * (double) direction1.getStepY();
        double d2 = (double) blockPos.getZ()
                + 0.5D
                + 0.1D * (double) direction.getStepZ()
                + 0.2D * (double) direction1.getStepZ();
        levelAccessor.addParticle(
                new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0F),
                d0,
                d1,
                d2,
                0.0D,
                0.0D,
                0.0D);
    }

    /**
     * @param direction
     *            If this isn't the master link, it WON'T check the opposite
     *            direction to prevent recursion
     * @param Master
     *            If this is the "Master" links, or the very first link that checks
     *            the others.
     */
    public void LoopTest(
            Level level, BlockPos pos, Direction direction, boolean Master, int iteration, boolean isBroken) {
        if (direction == null) direction = Direction.NORTH;
        for (Direction testing : Direction.values()) {
            if (!testing.equals(direction.getOpposite()) || Master) {
                if (level.getBlockState(pos.relative(testing, 1)).getBlock()
                        instanceof FragmentLinksBlock fragmentLinks) {
                    if (iteration > 14) return;

                    fragmentLinks.LoopTest(level, pos.relative(testing, 1), testing, false, iteration + 1, isBroken);
                    makeParticle(level, pos.above());

                    System.out.println(iteration);
                }

                if (level.getBlockState(pos.relative(testing, 1)).getBlock()
                        instanceof AbstractSubsystemBlock abstractSubsystemBlock) {
                    if (!isBroken) abstractSubsystemBlock.OnIntegration(level, pos.relative(testing, 1));
                    else abstractSubsystemBlock.OnDeActivate(level, pos.relative(testing, 1));
                }
            }
        }
    }

    public boolean TestForEngine(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).getBlock() instanceof EngineBlock) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState state, Level level, BlockPos blockPos, BlockState state1, boolean p_60570_) {
        super.onPlace(state, level, blockPos, state1, p_60570_);
        this.LoopTest(level, blockPos, Direction.NORTH, true, 0, false);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState1, boolean b) {
        this.LoopTest(level, blockPos, Direction.NORTH, true, 0, true);
        super.onRemove(blockState, level, blockPos, blockState1, b);
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos blockPos,
            Player player,
            InteractionHand interactionHand,
            BlockHitResult blockHitResult) {
        if (player.getItemInHand(interactionHand).getItem() instanceof SonicItem sonicItem) {
            if (!sonicItem.InteractionType.equals(SonicInteractionType.BLOCKS))
                return super.use(state, level, blockPos, player, interactionHand, blockHitResult);
            if (!this.TestForEngine(level, blockPos))
                return super.use(state, level, blockPos, player, interactionHand, blockHitResult);

            // TODO: Add custom sound
            level.playSound(player, blockPos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1, 1);

            this.LoopTest(level, blockPos, null, true, 0, false);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, blockPos, player, interactionHand, blockHitResult);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(
            @NotNull BlockState p_60555_,
            @NotNull BlockGetter p_60556_,
            @NotNull BlockPos p_60557_,
            @NotNull CollisionContext p_60558_) {
        return Block.box(5, 5, 5, 11, 11, 11);
    }
}
