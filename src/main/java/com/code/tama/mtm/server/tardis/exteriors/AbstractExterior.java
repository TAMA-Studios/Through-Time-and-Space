package com.code.tama.mtm.server.tardis.exteriors;

import com.code.tama.mtm.client.ExteriorModelsBakery;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

@Getter @Setter
public abstract class AbstractExterior {
    public ArrayList<ResourceLocation> ExteriorVariants;
    public ResourceLocation ModelName;
    public int ID;

    public AbstractExterior(ArrayList<ResourceLocation> exteriorVariants, ResourceLocation modelName) {
        this.ExteriorVariants = exteriorVariants;
        this.ModelName = modelName;
        this.ID = ExteriorModelsBakery.GetNextID();
    }
}