/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.registries.TTSBlocks;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.server.ServerLifecycleHooks;

public class SonicBlockMode extends SonicMode {
    public Item getIcon() {
        return Blocks.GRASS_BLOCK.asItem();
    }

    public String getName() {
        return "block_mode";
    }

    public void onUse(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos usedPos = context.getClickedPos();
        assert player != null;
        BlockState state = player.level().getBlockState(usedPos);
        Level level = player.level();

        if (state.getBlock().equals(TTSBlocks.EXTERIOR_BLOCK.get())) {
            if (level.getBlockEntity(usedPos) instanceof ExteriorTile exteriorTile) {
                if (exteriorTile.GetInterior() != null)
                    ServerLifecycleHooks.getCurrentServer()
                            .getLevel(exteriorTile.GetInterior())
                            .getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY)
                            .ifPresent(ITARDISLevel::Dematerialize);
            }
        }

        if (state.getBlock() instanceof SandBlock) {
            level.setBlockAndUpdate(usedPos, Blocks.GLASS.defaultBlockState());
            return;
        }

        if (state.getBlock() instanceof GlassBlock) {
            level.removeBlock(usedPos, false);
            level.playSound(
                    player,
                    usedPos,
                    SoundEvents.GLASS_BREAK,
                    SoundSource.BLOCKS,
                    1.0F,
                    level.getRandom().nextFloat() * 0.1F + 0.9F);
            return;
        }
        //
        if (state.getBlock().equals(Blocks.BRICKS)) {
            level.removeBlock(usedPos, false);
            ItemEntity item = EntityType.ITEM.create(level);
            assert item != null;
            item.setItem(Items.BRICK.getDefaultInstance());
            level.addFreshEntity(item);
            item.setPos(usedPos.getCenter());
            return;
        }

        if (state.getBlock().equals(Blocks.BRICK_SLAB)) {
            ItemEntity item = EntityType.ITEM.create(level);
            assert item != null;
            item.setItem(Items.BRICK.getDefaultInstance());
            level.addFreshEntity(item);
            item.setPos(usedPos.getCenter());
            return;
        }

        if (state.getBlock().equals(Blocks.BRICK_WALL)) {
            ItemEntity item = EntityType.ITEM.create(level);
            assert item != null;
            item.setItem(Items.BRICK.getDefaultInstance());
            level.addFreshEntity(item);
            item.setPos(usedPos.getCenter());
            return;
        }

        if (state.getBlock().equals(Blocks.BRICK_STAIRS)) {
            ItemEntity item = EntityType.ITEM.create(level);
            assert item != null;
            item.setItem(Items.BRICK.getDefaultInstance());
            level.addFreshEntity(item);
            item.setPos(usedPos.getCenter());
            return;
        }
        //
        //        if (State.getBlock() instanceof PistonBaseBlock pistonBaseBlock) {
        //            pistonBaseBlock.triggerEvent(State, Level, usedPos, 1, 2);
        //        }
        //
        //        return InteractionResult.PASS;
    }
}
