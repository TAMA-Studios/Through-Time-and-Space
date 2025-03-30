package com.code.tama.mtm.server.misc;

import com.code.tama.mtm.client.models.ModernBoxModel;
import com.code.tama.mtm.server.enums.tardis.ExteriorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class ExteriorModels {
    public static ModelPart GetModel(BlockEntityRendererProvider.Context context, ExteriorModel exteriorModel) {
        return switch (exteriorModel) {
            case MCGANN -> context.bakeLayer(ModernBoxModel.LAYER_LOCATION);
            case COLIN_RICHMOND -> context.bakeLayer(ModernBoxModel.LAYER_LOCATION);
            default -> context.bakeLayer(ModernBoxModel.LAYER_LOCATION);
        };
    }
}
