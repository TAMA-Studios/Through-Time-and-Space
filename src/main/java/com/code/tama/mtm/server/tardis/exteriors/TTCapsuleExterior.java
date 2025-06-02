package com.code.tama.mtm.server.tardis.exteriors;

import com.code.tama.mtm.core.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

import static com.code.tama.mtm.MTMMod.MODID;

public class TTCapsuleExterior extends AbstractExterior {
    public TTCapsuleExterior() {
        super(new ArrayList<>(), Constants.ExteriorModelNames.TTCapsule);
        this.ExteriorVariants.add(new ResourceLocation(MODID, "tiles/exterior/ttcapsule/ttcapsule"));
    }
}
