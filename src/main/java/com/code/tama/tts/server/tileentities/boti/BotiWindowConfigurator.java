/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.boti;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BotiWindowConfigurator extends Item {

    public BotiWindowConfigurator(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Player player = ctx.getPlayer();

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof BotiWindowTile clickedTile)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        // Find the master of this tile's cluster
        BlockPos masterPos = clickedTile.getMasterPos();
        if (masterPos == null) return InteractionResult.FAIL;

        BlockEntity masterBe = level.getBlockEntity(masterPos);
        if (!(masterBe instanceof BotiWindowTile master)) return InteractionResult.FAIL;

        // Cycle rotation on the master
        master.cycleRotation();

        int newRotation = master.getRotationDegrees();
        if (player != null) {
            player.displayClientMessage(
                    Component.literal("BOTI Window rotation: " + newRotation + "°"),
                    true // action bar
            );
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level,
                                @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(Component.literal("Right-click a BOTI Window to cycle rotation by 45°"));
        tooltip.add(Component.literal("Rotation is stored on the cluster master."));
    }
}