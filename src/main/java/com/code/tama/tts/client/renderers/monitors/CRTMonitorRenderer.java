/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import com.code.tama.tts.server.tileentities.monitors.CRTMonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CRTMonitorRenderer extends AbstractMonitorRenderer<CRTMonitorTile> {
    public CRTMonitorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public void ApplyCustomTransforms(PoseStack stack) {
        stack.scale((float) 1 - ((float) 2 / 16), 1 - ((float) 5 / 16), 1 - ((float) 2 / 16));
        stack.translate(0, 0.5 * 16, 0);
    }

    public float Offset() {
        return 40f;
    }
}
