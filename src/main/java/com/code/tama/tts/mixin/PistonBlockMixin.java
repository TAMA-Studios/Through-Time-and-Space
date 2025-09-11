/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import static com.code.tama.tts.server.misc.BlockStateProperties.SONICD;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBaseBlock.class)
public abstract class PistonBlockMixin extends DirectionalBlock {
    @Shadow
    @Final
    public static BooleanProperty EXTENDED;

    protected PistonBlockMixin(Properties p_52591_) {
        super(p_52591_);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void init(CallbackInfo ci) {
        this.registerDefaultState(this.stateDefinition
                .any()
                .setValue(FACING, Direction.NORTH)
                .setValue(EXTENDED, false)
                .setValue(SONICD, false));
    }

    @Inject(method = "createBlockStateDefinition", at = @At("TAIL"))
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> state, CallbackInfo ci) {
        state.add(SONICD);
    }

    @Inject(method = "getNeighborSignal", at = @At(value = "HEAD"), cancellable = true)
    private void getNeighborSignal(
            SignalGetter signalGetter, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (signalGetter.getBlockState(pos).getValue(SONICD)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
