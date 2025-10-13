/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.exterior.ExteriorStatePacket;
import com.code.tama.tts.server.tileentities.ExteriorTile;

public class PhysicalStateManager {

  private final ExteriorTile exteriorTile;
  private final ITARDISLevel itardisLevel;

  public PhysicalStateManager(ITARDISLevel itardisLevel, ExteriorTile exteriorTile) {
    this.itardisLevel = itardisLevel;
    this.exteriorTile = exteriorTile;
  }

  /* ==================== SERVER METHODS ==================== */

  public void serverTakeOff() {
    long tick = exteriorTile.getLevel().getGameTime();
    // send packet to everyone in the dimension
    this.itardisLevel
        .GetLevel()
        .players()
        .forEach(
            player ->
                this.itardisLevel
                    .GetFlightData()
                    .getFlightSoundScheme()
                    .GetTakeoff()
                    .PlayIfFinished(player.level(), player.blockPosition()));
    Networking.sendPacketToDimension(
        new ExteriorStatePacket(
            exteriorTile.getBlockPos(), ExteriorStatePacket.State.TAKEOFF, tick),
        exteriorTile.getLevel());
    // run the animation server-side with authority
    takeOffAnimation(tick, true);
  }

  public void serverLand() {
    itardisLevel.Land();
    long tick = this.itardisLevel.GetLevel().getGameTime();
    this.itardisLevel
        .GetLevel()
        .players()
        .forEach(
            player ->
                this.itardisLevel
                    .GetFlightData()
                    .getFlightSoundScheme()
                    .GetLanding()
                    .PlayIfFinished(player.level(), player.blockPosition()));
    Networking.sendPacketToDimension(
        new ExteriorStatePacket(
            this.itardisLevel.GetNavigationalData().getDestination().GetBlockPos(),
            ExteriorStatePacket.State.LAND,
            tick),
        this.itardisLevel.GetLevel());
    landAnimation(tick, true);
  }

  /* ==================== CLIENT ENTRY POINTS ==================== */

  public void clientTakeOff(long startTick) {
    takeOffAnimation(startTick, false);
  }

  public void clientLand(long startTick) {
    landAnimation(startTick, false);
  }

  /* ==================== ANIMATION CORE ==================== */

  private void takeOffAnimation(long startTick, boolean server) {
    float base = 0.0f;
    float initialAmp = 1.0f;
    float decay = 0.05f;
    float freq = 0.3f;

    while (!itardisLevel.GetFlightData().isInFlight()) {
      long tick = exteriorTile.getLevel().getGameTime() - startTick;
      float amp = (float) (initialAmp * Math.exp(-decay * tick));
      float alpha = base + (amp * (float) Math.abs(Math.sin(freq * tick)));
      exteriorTile.setTransparency(alpha);

      if (amp < 0.05f && alpha < 0.05f) {
        if (server) {
          while (!itardisLevel
              .GetFlightData()
              .getFlightSoundScheme()
              .GetTakeoff()
              .IsFinished()) {} // Wait for the takeoff to be finished
          itardisLevel.Fly();
          break;
        }
      }
    }
  }

  private void landAnimation(long startTick, boolean server) {
    float base = 1.0f;
    float initialAmp = 1.0f;
    float decay = 0.05f;
    float freq = 0.3f;

    while (server && this.itardisLevel.GetFlightData().isInFlight() || !server) {
      long tick = this.itardisLevel.GetLevel().getGameTime() - startTick;
      float amp = (float) (initialAmp * Math.exp(-decay * tick));
      float alpha = base - (amp * (float) Math.abs(Math.sin(freq * tick)));
      exteriorTile.setTransparency(alpha);

      if (amp < 0.05f && alpha > 0.95f) {
        if (server) {
          while (!itardisLevel.GetFlightData().getFlightSoundScheme().GetLanding().IsFinished()) {}
          // TODO: Signal to the Exterior that it's fully landed
        }
        break;
      }
    }
  }
}
