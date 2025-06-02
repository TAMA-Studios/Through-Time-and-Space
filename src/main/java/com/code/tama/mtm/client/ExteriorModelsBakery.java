package com.code.tama.mtm.client;

import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import com.code.tama.mtm.server.registries.ExteriorRegistry;
import com.code.tama.mtm.server.tardis.exteriors.AbstractExterior;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ExteriorModelsBakery {
    // ResourceLocation = ModelName
    public static Map<ResourceLocation, Map<Class<? extends HierarchicalExteriorModel>, ModelLayerLocation>> ModelMap = new HashMap<>();
    public static Map<Integer, ResourceLocation> NameIDMap = new HashMap<>();
    private static int ID = 0;

    public static Map<ResourceLocation, HierarchicalExteriorModel> InstanceModels = new HashMap<>();

    public void InitInstanceList() {
        if (ModelMap.isEmpty()) {
            return;
        }

        ModelMap.forEach((name, map) -> {
            map.forEach((model, layer) -> {
                try {
                    HierarchicalExteriorModel exteriorModel = model.getDeclaredConstructor(ModelPart.class).newInstance(RenderingHelper.GetTileRendererContext().bakeLayer(layer));
                    InstanceModels.put(name, exteriorModel);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException();
                }
            });
        });
    }

    public void AddModel(ResourceLocation ModelName, Class<? extends HierarchicalExteriorModel> model, ModelLayerLocation location) {
        try {
//            HierarchicalExteriorModel exteriorModel = model.getDeclaredConstructor(ModelPart.class).newInstance(RenderingHelper.GetTileRendererContext().bakeLayer(location));
            ModelMap.put(ModelName, Map.of(model, location));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static ExteriorModelsBakery GetInstance() {
        return ClientSetup.getExteriorModelsHandler();
    }

    public static int GetNextID() {
        return ID++;
    }

    public static AbstractExterior GetExteriorFromID(int ID) {
        return ExteriorRegistry.EXTERIORS.getEntries()
                .stream()
                .filter(exterior -> exterior.get().getID() == ID)
                .map(RegistryObject::get)
                .findFirst()
                .orElse(null);
    }

    public static AbstractExterior GetExteriorFromName(ResourceLocation name) {
        return ExteriorRegistry.EXTERIORS.getEntries()
                .stream()
                .filter(exterior -> exterior.get().getModelName().equals(name))
                .map(RegistryObject::get)
                .findFirst()
                .orElse(null);
    }
}