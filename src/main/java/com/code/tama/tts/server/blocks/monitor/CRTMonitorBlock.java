/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.monitor;

import com.code.tama.tts.server.blocks.VoxelRotatedShape;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.registries.TTSTileEntities;
import com.code.tama.tts.server.registries.UICategoryRegistry;
import com.code.tama.tts.server.registries.UIComponentRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.math.Axis;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CRTMonitorBlock extends AbstractMonitorBlock {
    private final VoxelRotatedShape SHAPE = new VoxelRotatedShape(createShape().optimize());

    public CRTMonitorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public VoxelShape createShape() {
        return Stream.of(
                        Block.box(0, 0, 0, 10, 16, 1),
                        Block.box(0, 0, 1, 10, 4, 15),
                        Block.box(0, 15, 1, 10, 16, 15),
                        Block.box(1, 4, 1, 10, 15, 15),
                        Block.box(0, 0, 15, 10, 16, 16),
                        Block.box(10, 0, 0, 16, 13, 16))
                .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
                .get();
    }

    public @NotNull VoxelShape getShape(
            @NotNull BlockState state,
            @NotNull BlockGetter getter,
            @NotNull BlockPos pos,
            @NotNull CollisionContext context) {
        return SHAPE.GetShapeFromRotation(state.getValue(FACING).getClockWise());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return TTSTileEntities.CRT_MONITOR_TILE.get().create(blockPos, blockState);
    }

    @Override
    public @NotNull InteractionResult use(
            @NotNull BlockState state,
            @NotNull Level world,
            @NotNull BlockPos pos,
            @NotNull Player player,
            @NotNull InteractionHand hand,
            @NotNull BlockHitResult hit) {

        // Get the coordinates the player clicked on the screen
        Vec3 hitVec = hit.getLocation();
        double mouseX = hitVec.x() - (double) pos.getX();
        double mouseZ = hitVec.z() - (double) pos.getZ();
        double mouseY = hitVec.y() - (double) pos.getY();

        // Define shrink and offset values
        double xzShrink = 2.0;
        double yShrink = 5.0;
        double xzOffset = 1.0;
        double yOffset = -0.5;

        // Convert pixel values to normalized block values
        double xzShrinkNorm = xzShrink / 16.0;
        double yShrinkNorm = yShrink / 16.0;
        double xzOffsetNorm = xzOffset / 16.0;
        double yOffsetNorm = yOffset / 16.0;

        // Convert world-relative click position to scaled pixel space
        // Convert the coordinates to be a max of 16 instead of just 1
        mouseX = ((mouseX - xzOffsetNorm) / (1.0 - xzShrinkNorm)) * 16.0;
        mouseZ = ((mouseZ - xzOffsetNorm) / (1.0 - xzShrinkNorm)) * 16.0;
        mouseY = ((mouseY + yOffsetNorm) / (1.0 - yShrinkNorm)) * 16.0;

        // Flip the Y coordinate (Why did I do this? I'm an HTML boy.)
        mouseY = 16.0 - mouseY;
        // Flip the X because it's backwards for no reason
        mouseX = 16.0 - mouseX;

        mouseY += yShrink;

        // Adjust coordinates based on how the block is facing
        switch (state.getValue(FACING)) {
            case NORTH -> {
                break;
                //            case NORTH -> mouseX = 16 - mouseX;
            }
            case SOUTH -> mouseX = 16 - mouseX;
            case WEST -> mouseX = mouseZ;
            case EAST -> mouseX = 16 - mouseZ;
        }
        System.out.printf("X: %s Y: %s%n", mouseX, mouseY);

        // They need to be "final or effectively final" otherwise the JDK freaks out
        double finalMouseX = mouseX;
        double finalMouseY = mouseY;

        UIComponentRegistry.UI_COMPONENTS.getEntries().forEach(reg -> {
            if (reg.get().category.getID() == ((AbstractMonitorTile) world.getBlockEntity(pos)).getCategoryID()
                    || reg.get().category.equals(UICategoryRegistry.ALL.get())) {
                if (reg.get().XYBounds().get(Axis.XP)[0] <= finalMouseX
                        && reg.get().XYBounds().get(Axis.XP)[1] >= finalMouseX) {
                    if (reg.get().XYBounds().get(Axis.YP)[0] <= finalMouseY
                            && reg.get().XYBounds().get(Axis.YP)[1] >= finalMouseY) {
                        reg.get().onInteract(player, ((AbstractMonitorTile) world.getBlockEntity(pos)));
                    }
                }
            }
        });

        world.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {});
        return InteractionResult.SUCCESS;
    }
}
