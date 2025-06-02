package com.code.tama.mtm.server.tardis.exteriors;

import com.code.tama.mtm.core.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

import static com.code.tama.mtm.MTMMod.MODID;

public class ModernExterior extends AbstractExterior {
    public ModernExterior() {
        super(new ArrayList<>(), Constants.ExteriorModelNames.ModernBox);
        this.ExteriorVariants.add(new ResourceLocation(MODID, "tiles/exterior/colin_richmond/box/mof_11a"));
    }
}
