/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class SonicBlockMode extends SonicMode {
    public Item getIcon() {
        return Blocks.GRASS_BLOCK.asItem();
    }

    public String getName() {
        return "block_mode";
    }

    public void onUse(UseOnContext context) {
        //        BlockState State = player.level().getBlockState(usedPos);
        //        Level Level = player.level();
        //
        //        if (State.getBlock().equals(TTSBlocks.EXTERIOR_BLOCK.get())) {
        //            if (Level.getBlockEntity(usedPos) instanceof ExteriorTile exteriorTile) {
        //                if (exteriorTile.GetInterior() != null)
        //                    ServerLifecycleHooks.getCurrentServer()
        //                            .getLevel(exteriorTile.GetInterior())
        //                            .getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
        //                            .ifPresent(ITARDISLevel::Dematerialize);
        //            }
        //        }
        //
        //        if (State.getBlock().equals(Blocks.IRON_DOOR)) {
        //            State = State.cycle(DoorBlock.OPEN);
        //            Level.setBlockAndUpdate(usedPos, State);
        //            this.playDoorSound(
        //                    player, Level, usedPos, State.getValue(BlockStateProperties.OPEN), (DoorBlock)
        //                            State.getBlock());
        //            Level.gameEvent(
        //                    player,
        //                    ((DoorBlock) State.getBlock()).isOpen(State) ? GameEvent.BLOCK_OPEN :
        // GameEvent.BLOCK_CLOSE,
        //                    usedPos);
        //            return;
        //        }
        //
        //        if (State.getBlock() instanceof RedstoneLampBlock) {
        //            if (!Level.isClientSide) Level.setBlockAndUpdate(usedPos, State.cycle(RedstoneLampBlock.LIT));
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.IRON_TRAPDOOR)) {
        //            Level.setBlockAndUpdate(usedPos, State.cycle(TrapDoorBlock.OPEN));
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.TNT)) {
        //            TntBlock.explode(Level, usedPos);
        //            Level.removeBlock(usedPos, false);
        //            return;
        //        }
        //
        //        if (State.getBlock() instanceof SandBlock) {
        //            Level.setBlockAndUpdate(usedPos, Blocks.GLASS.defaultBlockState());
        //            return;
        //        }
        //
        //        if (State.getBlock() instanceof GlassBlock) {
        //            Level.removeBlock(usedPos, false);
        //            Level.playSound(
        //                    player,
        //                    usedPos,
        //                    SoundEvents.GLASS_BREAK,
        //                    SoundSource.BLOCKS,
        //                    1.0F,
        //                    Level.getRandom().nextFloat() * 0.1F + 0.9F);
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.BRICKS)) {
        //            Level.removeBlock(usedPos, false);
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.BRICK_SLAB)) {
        //            Level.removeBlock(usedPos, false);
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.BRICK_WALL)) {
        //            Level.removeBlock(usedPos, false);
        //            return;
        //        }
        //
        //        if (State.getBlock().equals(Blocks.BRICK_STAIRS)) {
        //            Level.removeBlock(usedPos, false);
        //            return;
        //        }
        //
        //        if (State.getBlock() instanceof PistonBaseBlock pistonBaseBlock) {
        //            pistonBaseBlock.triggerEvent(State, Level, usedPos, 1, 2);
        //        }
        //
        //        return InteractionResult.PASS;
    }
}
