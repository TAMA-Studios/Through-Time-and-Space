package com.code.tama.mtm.client.events;

import com.code.tama.mtm.client.ExteriorModelsHandler;
import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import lombok.Getter;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

import static com.code.tama.mtm.client.ExteriorModelsHandler.ModelMap;

@Deprecated
@Getter
@ApiStatus.ScheduledForRemoval
public class RegisterExteriorModelsEvent extends Event implements IModBusEvent {
    private final BlockEntityRendererProvider.Context Context;
    private final ExteriorModelsHandler<HierarchicalExteriorModel> ExteriorHandler;

    public RegisterExteriorModelsEvent(ExteriorModelsHandler<HierarchicalExteriorModel> exteriorModelsHandler, BlockEntityRendererProvider.Context context) {
        this.ExteriorHandler = exteriorModelsHandler;
        this.Context = context;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    public void AddModel(Class<? extends HierarchicalExteriorModel> model, ModelLayerLocation location) {
        try {
            ModelMap.put(model, location);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
