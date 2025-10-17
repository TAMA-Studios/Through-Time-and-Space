/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;
import com.code.tama.triggerapi.rendering.VortexRenderer;

@Getter
@Setter
@OnlyIn(Dist.CLIENT)
public class TARDISClientData {
	public final TARDISLevelCapability TARDIS;
	public AbstractJSONRenderer exteriorRenderer;
	public VortexRenderer vortex;
	private ExteriorModelContainer exterior;

	public TARDISClientData(TARDISLevelCapability cap) {
		this.TARDIS = cap;
		cap.UpdateClient(DataUpdateValues.RENDERING);
	}

	public JavaJSONRenderer getExteriorBOTI() {
		return getExteriorRenderer().getJavaJSON().getPart("BOTI");
	}

	public JavaJSONRenderer getExteriorPartialBOTI() {
		return getExteriorRenderer().getJavaJSON().getPart("PartialBOTI");
	}

	public AbstractJSONRenderer getExteriorRenderer() {
		if (this.exteriorRenderer == null || !this.exterior.equals(TARDIS.GetData().getExteriorModel())) {
			TARDIS.UpdateClient(DataUpdateValues.RENDERING);
			this.exterior = TARDIS.GetData().getExteriorModel();
			return this.exteriorRenderer = new AbstractJSONRenderer(TARDIS.GetData().getExteriorModel().getModel());
		}
		return this.exteriorRenderer;
	}

	public JavaJSONRenderer getInteriorBOTI() {
		return getExteriorRenderer().getJavaJSON().getPart("InteriorBOTI");
	}

	public JavaJSONRenderer getInteriorDoor() {
		return getExteriorRenderer().getJavaJSON().getPart("InteriorDoor");
	}

	public VortexRenderer getVortex() {
		if (vortex == null
				|| vortex.textureLayers.get(VortexRenderer.LayerType.BASE).equals(new ResourceLocation("minecraft", ""))
				|| vortex.textureLayers.get(VortexRenderer.LayerType.BASE) != TARDIS.GetData().getVortex()) {
			TARDIS.UpdateClient(DataUpdateValues.RENDERING);
			return this.vortex = new VortexRenderer(
					new ResourceLocation(TTSMod.MODID, "textures/rift/infiniteabyssofnothingness.png"));
		}
		return vortex;
	}

	public void setupInteriorDoorPose() {
		this.getExteriorRenderer().getJavaJSON()
				.getPart("IntRightDoor").yRot = (float) (TARDIS.GetData().getDoorData().getDoorsOpen() == 2
						? -Math.toRadians(90)
						: Math.toRadians(0));
		this.getExteriorRenderer().getJavaJSON()
				.getPart("IntLeftDoor").yRot = (float) (TARDIS.GetData().getDoorData().getDoorsOpen() >= 1
						? Math.toRadians(90)
						: Math.toRadians(0));
	}
}
