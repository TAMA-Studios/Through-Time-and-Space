/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import static com.code.tama.tts.server.misc.BlockStateProperties.SONICD;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class RedstoneSonicMode extends SonicMode {

  @Override
  public Item getIcon() {
    return Items.REDSTONE_TORCH;
  }

  @Override
  public String getName() {
    return "Redstone Pulse";
  }

  @Override
  public void onUse(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos pos = context.getClickedPos();
    BlockState state = level.getBlockState(pos);
    Block block = state.getBlock();

    if (level.isClientSide) return;

    if (state.hasProperty(SONICD)) {
      boolean currentlyPowered = state.getValue(SONICD);
      level.setBlock(pos, state.setValue(SONICD, !currentlyPowered), 3);
      if (state.getBlock() instanceof PistonBaseBlock)
        state.getBlock().neighborChanged(state, level, pos, block, pos, false);
      return;
    }

    if (block instanceof PressurePlateBlock) {
      level.setBlock(pos, state.cycle(PressurePlateBlock.POWERED), 3);

      // Schedule turning it off again (pulse)
      //                if (level instanceof ServerLevel serverLevel) {
      //                    serverLevel.scheduleTick(pos, block, 20); // 20 ticks = 1 second
      //                }
      return;
    }

    if (block == Blocks.TNT) {
      // Ignite TNT
      TntBlock.explode(level, pos);
      level.removeBlock(pos, false);
      return;
    }

    if (block instanceof DoorBlock) {
      state = state.cycle(DoorBlock.OPEN);
      level.setBlock(pos, state, 3);
    } else if (block instanceof TrapDoorBlock) {
      state = state.cycle(TrapDoorBlock.OPEN);
      level.setBlock(pos, state, 3);
    }

    // Handle blocks that have a POWERED property (buttons, levers, etc.)
    if (state.hasProperty(BlockStateProperties.POWERED)) {
      boolean currentlyPowered = state.getValue(BlockStateProperties.POWERED);
      level.setBlock(pos, state.setValue(BlockStateProperties.POWERED, !currentlyPowered), 3);

      // Schedule turning it off again (pulse)
      if (level instanceof ServerLevel serverLevel) {
        serverLevel.scheduleTick(pos, block, 20);
      }
      return;
    }

    if (state.hasProperty(BlockStateProperties.POWER)) {
      boolean currentlyPowered = state.getValue(BlockStateProperties.POWER) != 0;
      if (!currentlyPowered) {
        BlockState poweredState = state.setValue(BlockStateProperties.POWER, 15);
        level.setBlock(pos, poweredState, 3);

        // Schedule turning it off again (pulse)
        if (level instanceof ServerLevel serverLevel) {
          serverLevel.scheduleTick(pos, block, 20); // 20 ticks = 1 second
        }
      }
      return;
    }

    if (block instanceof RedstoneLampBlock) {
      boolean lit = state.getValue(RedstoneLampBlock.LIT);
      level.setBlock(pos, state.setValue(RedstoneLampBlock.LIT, !lit), 3);
      return;
    }
  }
}
