/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;


import com.code.tama.tts.server.tileentities.MonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class MonitorRenderer extends AbstractMonitorRenderer<MonitorTile> {
    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    public float Offset() {
        return 44.3f;
    }

    public void ApplyCustomTransforms(PoseStack stack, MultiBufferSource bufferSource) {

    }
}