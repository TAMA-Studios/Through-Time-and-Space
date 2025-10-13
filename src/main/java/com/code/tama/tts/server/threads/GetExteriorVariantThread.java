/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.Exteriors;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.C2S.exterior.TriggerSyncExteriorVariantPacketC2S;
import com.code.tama.tts.server.tileentities.ExteriorTile;

public class GetExteriorVariantThread extends Thread {
  ExteriorTile tile;

  public GetExteriorVariantThread(ExteriorTile tile) {
    this.setName("Exterior Variant Update Thread");
    this.tile = tile;
  }

  @Override
  public void run() {
    super.run();
    this.tile.ThreadWorking = true;
    if (tile.getLevel() == null) return;
    if (tile.getLevel() == null) tile.Model = Exteriors.Get(0);
    if (tile.getLevel().isClientSide && tile.Model == null)
      Networking.sendToServer(
          new TriggerSyncExteriorVariantPacketC2S(
              tile.getLevel().dimension(),
              tile.getBlockPos().getX(),
              tile.getBlockPos().getY(),
              tile.getBlockPos().getZ()));
    if (tile.Model == null) tile.Model = Exteriors.Get(0);

    this.tile.ThreadWorking = false;
  }
}
