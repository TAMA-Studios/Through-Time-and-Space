/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import com.code.tama.tts.server.tileentities.monitors.MonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class MonitorRenderer extends AbstractMonitorRenderer<MonitorTile> {
	public MonitorRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	public void ApplyCustomTransforms(PoseStack stack) {
	}

	public float Offset() {
		return 36f;
	}
}
