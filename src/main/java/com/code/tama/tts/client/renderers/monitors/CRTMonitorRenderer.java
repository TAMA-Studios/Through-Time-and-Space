/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import com.code.tama.tts.core.tileentities.monitors.CRTMonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CRTMonitorRenderer extends AbstractMonitorRenderer<CRTMonitorTile> {
	public CRTMonitorRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	public void ApplyCustomTransforms(PoseStack stack) {
		stack.scale((float) 1 - ((float) 4 / 16), 1 - ((float) 6 / 16), 1 - ((float) 4 / 16));
		stack.translate(0, 1.1 * 16, -8);
	}

	public float Offset() {
		return 41f;
	}
}
