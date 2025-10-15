/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.data;

import com.code.tama.triggerapi.rendering.VortexRenderer;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.misc.Exterior;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@Getter
@Setter
@OnlyIn(Dist.CLIENT)
public class TARDISClientData {
    public final TARDISLevelCapability TARDIS;
    public VortexRenderer vortex;
    public AbstractJSONRenderer exteriorRenderer;
    private Exterior exterior;

    public TARDISClientData(TARDISLevelCapability cap) {
        this.TARDIS = cap;
        cap.UpdateClient(DataUpdateValues.RENDERING);
    }

    public VortexRenderer getVortex() {
        if (vortex == null || vortex.textureLayers.get(VortexRenderer.LayerType.BASE).equals(new ResourceLocation("minecraft", "")) || vortex.textureLayers.get(VortexRenderer.LayerType.BASE) != TARDIS.GetData().getVortex()) {
            TARDIS.UpdateClient(DataUpdateValues.RENDERING);
            return this.vortex = new VortexRenderer(
                    new ResourceLocation(TTSMod.MODID, "textures/rift/infiniteabyssofnothingness.png"));
        }
        return vortex;
    }

    public AbstractJSONRenderer getRenderer() {
        if (this.exteriorRenderer == null
                || !this.exterior.equals(TARDIS.GetData().getExteriorModel())) {
            TARDIS.UpdateClient(DataUpdateValues.RENDERING);
            this.exterior = TARDIS.GetData().getExteriorModel();
            return this.exteriorRenderer =
                    new AbstractJSONRenderer(TARDIS.GetData().getExteriorModel().getModel());
        }
        return this.exteriorRenderer;
    }
}
