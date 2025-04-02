package com.code.tama.mtm.client.renderers.worlds;

import com.code.tama.mtm.server.worlds.dimension.MDimensions;
import net.minecraft.resources.ResourceLocation;

public class VarosSkyRenderer extends BasicSkyRenderer {
    public VarosSkyRenderer() {
        super(new int[] {255, 21, 21});
    }

    @Override
    ResourceLocation EffectsLocation() {
        return MDimensions.DimensionEffects.VAROS_EFFECTS;
    }

    @Override
    boolean ShouldRenderVoid() {
        return false;
    }
}
