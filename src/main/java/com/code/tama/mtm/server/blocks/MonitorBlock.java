package com.code.tama.mtm.server.blocks;

import com.code.tama.mtm.server.MTMTileEntities;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class MonitorBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public MonitorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> StateDefinition) {
        super.createBlockStateDefinition(StateDefinition);
        StateDefinition.add(FACING);
    }

    @Override
    public @NotNull BlockState rotate(BlockState p_54125_, Rotation p_54126_) {
        return p_54125_.setValue(FACING, p_54126_.rotate(p_54125_.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState p_54122_, Mirror p_54123_) {
        return p_54122_.rotate(p_54123_.getRotation(p_54122_.getValue(FACING)));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide) {
            System.out.println("Block was hit on face: " + hit.getDirection());


            Vec3 hitVec = hit.getLocation();
            double mouseX = hitVec.x() - (double) pos.getX();
            double mouseZ = hitVec.z() - (double) pos.getZ();

            System.out.println("Hit position X: " + mouseX);

            System.out.println("Hit position Z: " + mouseZ);

            world.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
                player.sendSystemMessage(Component.literal("Location: " + cap.GetExteriorLocation().ReadableString()));
                player.sendSystemMessage(Component.literal("Dimension: " + cap.GetCurrentLevel().location()));
                player.sendSystemMessage(Component.literal("Destination: " + cap.GetDestination().ReadableString()));
            });
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return MTMTileEntities.MONITOR_TILE.get().create(blockPos, blockState);
    }
}