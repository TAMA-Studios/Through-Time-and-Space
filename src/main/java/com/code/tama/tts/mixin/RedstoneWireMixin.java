/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import static com.code.tama.tts.server.misc.BlockStateProperties.SONICD;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.POWER;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RedStoneWireBlock.class)
public abstract class RedstoneWireMixin extends Block {
  public RedstoneWireMixin(Properties p_49795_) {
    super(p_49795_);
  }

  @Inject(method = "<init>", at = @At(value = "TAIL"))
  private void init(CallbackInfo ci) {
    this.registerDefaultState(
        this.stateDefinition
            .any()
            .setValue(RedStoneWireBlock.NORTH, RedstoneSide.NONE)
            .setValue(RedStoneWireBlock.EAST, RedstoneSide.NONE)
            .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.NONE)
            .setValue(RedStoneWireBlock.WEST, RedstoneSide.NONE)
            .setValue(POWER, 0)
            .setValue(SONICD, false));
  }

  @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
  protected void createBlockStateDefinition(
      StateDefinition.Builder<Block, BlockState> state, CallbackInfo ci) {
    state.add(SONICD);
  }

  @Inject(method = "getSignal", at = @At("HEAD"), cancellable = true)
  public void getSignal(
      BlockState state,
      BlockGetter blockGetter,
      BlockPos pos,
      Direction direction,
      CallbackInfoReturnable<Integer> cir) {
    if (state.getValue(SONICD)) {
      cir.setReturnValue(15);
      cir.cancel();
    }
  }
}
