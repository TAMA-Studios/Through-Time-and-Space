package com.code.tama.mtm.server.tardis.exteriors;

import com.code.tama.mtm.core.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class AWJExterior extends AbstractExterior {
    public AWJExterior() {
        super(new ArrayList<>(), Constants.ExteriorModelNames.Whittaker);
        this.ExteriorVariants.add(new ResourceLocation("mtm", "tiles/exterior/awj/awj"));
    }
}