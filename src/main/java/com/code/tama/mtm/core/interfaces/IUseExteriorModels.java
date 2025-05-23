package com.code.tama.mtm.core.interfaces;

import com.code.tama.mtm.MTMMod;
import com.code.tama.mtm.client.ExteriorModelsHandler;
import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import lombok.Getter;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Getter
public class IUseExteriorModels {
    private final ExteriorModelsHandler<HierarchicalExteriorModel> handler = new ExteriorModelsHandler<>();
    public IUseExteriorModels(BlockEntityRendererProvider.Context context) {
        MTMMod.getExteriorModelsHandler().Initialize();
        handler.InitInstanceList();
    }
}
