package com.code.tama.mtm.client;

import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ExteriorModelsHandler<T extends HierarchicalExteriorModel> {
    public static Map<Class<? extends HierarchicalExteriorModel>, ModelLayerLocation> ModelMap = new HashMap<>();

    public ArrayList<HierarchicalExteriorModel> InstanceModels = new ArrayList<>();

    public void InitInstanceList() {
        if(ModelMap.isEmpty()) {
            Initialize();
            return;
        }
        ModelMap.forEach((model, layer) -> {
            try {
                HierarchicalExteriorModel exteriorModel = model.getDeclaredConstructor(ModelPart.class).newInstance(RenderingHelper.GetTileRendererContext().bakeLayer(layer));
                this.InstanceModels.add(exteriorModel);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void Initialize() {
    }

    @SuppressWarnings("unchecked")
    public void AddModel(Class<? extends HierarchicalExteriorModel> model, ModelLayerLocation location) {
        try {
//            HierarchicalExteriorModel exteriorModel = model.getDeclaredConstructor(ModelPart.class).newInstance(RenderingHelper.GetTileRendererContext().bakeLayer(location));
            ModelMap.put(model, location);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static ExteriorModelsHandler<HierarchicalExteriorModel> GetInstance() {
        return ClientSetup.getExteriorModelsHandler();
    }
}