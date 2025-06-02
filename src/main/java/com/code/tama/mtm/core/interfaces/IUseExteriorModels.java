package com.code.tama.mtm.core.interfaces;

import com.code.tama.mtm.client.ExteriorModelsBakery;
import lombok.Getter;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@Getter
public class IUseExteriorModels {
    private final ExteriorModelsBakery handler = new ExteriorModelsBakery();
    public IUseExteriorModels(BlockEntityRendererProvider.Context context) {
        handler.InitInstanceList();
    }
}
