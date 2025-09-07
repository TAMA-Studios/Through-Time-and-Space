/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.models.core;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class HierarchicalExteriorModel extends HierarchicalModel<Entity>
        implements IExteriorModel<ExteriorTile> {
    static boolean innited = false;

    public HierarchicalExteriorModel(ModelPart root, ResourceLocation name, ModelLayerLocation location) {
        // if(!innited)
        // ExteriorModelsHandler.GetInstance().AddModel(this.getClass(), location);
        // innited = true;
    }

    /**
     * Modid + name of the exterior <br />
     * If you're adding a custom exterior with an addon just use your modid +
     * exterior name
     **/
    public abstract ResourceLocation GetModelName();

    @Override
    public void setupAnim(@NotNull Entity entity, float v, float v1, float v2, float v3, float v4) {}
}
