/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import com.code.tama.tts.server.tileentities.monitors.MonitorPanelTile;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class MonitorPanelRenderer extends AbstractMonitorRenderer<MonitorPanelTile> {
  public MonitorPanelRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  public float Offset() {
    return -39.75f;
  }
}
