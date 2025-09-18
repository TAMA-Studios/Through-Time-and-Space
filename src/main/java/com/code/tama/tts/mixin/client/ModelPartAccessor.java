/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import java.util.List;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ModelPart.class)
public interface ModelPartAccessor {
    @Accessor
    List<ModelPart.Cube> getCubes();

    @Accessor
    Map<String, ModelPart> getChildren();
}
