package com.code.tama.mtm.server.blocks;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.registries.UICategoryRegistry;
import com.code.tama.mtm.server.registries.UIComponentRegistry;
import com.code.tama.mtm.server.tileentities.AbstractMonitorTile;
import com.mojang.math.Axis;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Setter
@Getter
@SuppressWarnings("deprecation")
public abstract class AbstractMonitorBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public AbstractMonitorBlock(Properties p_49795_) {
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

        // Get the coordinates the player clicked on the screen
        Vec3 hitVec = hit.getLocation();
        double mouseX = hitVec.x() - (double) pos.getX();
        double mouseZ = hitVec.z() - (double) pos.getZ();
        double mouseY = hitVec.y() - (double) pos.getY();

        // Convert the coordinates to be a max of 16 instead of just 1
        mouseX *= 16;
        mouseZ *= 16;
        mouseY *= 16;
        // Flip the Y coordinate (Why did I do this? I'm an HTML boy.)
        mouseY = 16 - mouseY;

        // Adjust coordinates based on how the block is facing
        switch (state.getValue(FACING)) {
            case NORTH -> mouseX = 16 - mouseX;
            case SOUTH -> mouseX = mouseX; // <- Yes, I know this line seems redundant, but if I don't include it, mouseX is just always 16.0f, dunno why, don't care, if it ain't broke don't fix it and if it works it ain't stupid.
            case WEST -> mouseX = mouseZ;
            case EAST -> mouseX = 16 - mouseZ;
        }

        // They need to be "final or effectively final" otherwise the JDK freaks out
        double finalMouseX = mouseX;
        double finalMouseY = mouseY;

        UIComponentRegistry.UI_COMPONENTS.getEntries().forEach(reg -> {
            if (reg.get().category.getID() == ((AbstractMonitorTile) world.getBlockEntity(pos)).getCategoryID() || reg.get().category.equals(UICategoryRegistry.ALL.get())) {
                if (reg.get().XYBounds().get(Axis.XP)[0] <= finalMouseX && reg.get().XYBounds().get(Axis.XP)[1] >= finalMouseX) {
                    if (reg.get().XYBounds().get(Axis.YP)[0] <= finalMouseY && reg.get().XYBounds().get(Axis.YP)[1] >= finalMouseY) {
                        reg.get().onInteract(player, ((AbstractMonitorTile) world.getBlockEntity(pos)));
                    }
                }
            }
        });

        world.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
        });
        return InteractionResult.SUCCESS;
    }
}