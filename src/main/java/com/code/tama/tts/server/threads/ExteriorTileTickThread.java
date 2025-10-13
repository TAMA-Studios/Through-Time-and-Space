/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.threads;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ExteriorTileTickThread extends Thread {
  boolean IsInstantiated = true;
  ExteriorTile exteriorTile;
  Level level;
  BlockPos pos;
  BlockState state;

  public ExteriorTileTickThread() {
    this.setName("Exterior Tick Thread");
    this.IsInstantiated = true;
  }

  public ExteriorTileTickThread(
      Level level, BlockPos pos, BlockState state, ExteriorTile exteriorTile) {
    this.level = level;
    this.pos = pos;
    this.state = state;
    this.exteriorTile = exteriorTile;
    this.IsInstantiated = false;
  }

  public void Init(Level level, BlockPos pos, BlockState state, ExteriorTile exteriorTile) {
    this.level = level;
    this.pos = pos;
    this.state = state;
    this.exteriorTile = exteriorTile;
    this.IsInstantiated = false;
  }

  public boolean IsInstantiated() {
    return this.IsInstantiated;
  }

  @Override
  public void run() {
    if (this.IsInstantiated) return;
    super.run();
    return;
  }
}
