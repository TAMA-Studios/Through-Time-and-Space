// (C) TAMA Studios 2025
package com.code.tama.tts.mixin.mavity;

import com.code.tama.triggerapi.helpers.GravityHelper;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockGravityMixin {

    @ModifyConstant(
        method = "tick",
        constant = @Constant(doubleValue = -0.04D)
    )
    private double tts$replaceFallingBlockGravity(double original) {
        FallingBlockEntity self = (FallingBlockEntity)(Object)this;
        return -GravityHelper.getGravity(self.level());
    }
}
