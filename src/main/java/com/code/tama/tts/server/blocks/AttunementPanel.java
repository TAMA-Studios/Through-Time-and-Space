/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.items.SonicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class AttunementPanel extends Block {
    public AttunementPanel(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(
            @NotNull BlockState p_60503_,
            Level level,
            @NotNull BlockPos p_60505_,
            @NotNull Player player,
            @NotNull InteractionHand p_60507_,
            @NotNull BlockHitResult p_60508_) {
        level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(tardis -> {
            if (player.getMainHandItem().getItem() instanceof SonicItem sonicItem) {
                ItemStack sonic = player.getMainHandItem();
                sonicItem.Attune(tardis, sonic);
                player.displayClientMessage(Component.literal(String.format("Attuned %s", sonicItem)), true);
            }
        });
        return super.use(p_60503_, level, p_60505_, player, p_60507_, p_60508_);
    }
}
