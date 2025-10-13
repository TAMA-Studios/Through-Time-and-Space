/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.core;

import com.code.tama.tts.server.registries.TTSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {
  public ModFlammableRotatedPillarBlock(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public int getFireSpreadSpeed(
      BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return 5;
  }

  @Override
  public int getFlammability(
      BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return 5;
  }

  @Override
  public @Nullable BlockState getToolModifiedState(
      BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
    if (context.getItemInHand().getItem() instanceof AxeItem) {
      if (state.is(TTSBlocks.GALLIFREYAN_OAK_LOG.get())) {
        return TTSBlocks.STRIPPED_GALLIFREYAN_OAK_LOG
            .get()
            .defaultBlockState()
            .setValue(AXIS, state.getValue(AXIS));
      }

      if (state.is(TTSBlocks.GALLIFREYAN_OAK_WOOD.get())) {
        return TTSBlocks.STRIPPED_GALLIFREYAN_OAK_WOOD
            .get()
            .defaultBlockState()
            .setValue(AXIS, state.getValue(AXIS));
      }
    }

    return super.getToolModifiedState(state, context, toolAction, simulate);
  }

  @Override
  public boolean isFlammable(
      BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
    return true;
  }
}
