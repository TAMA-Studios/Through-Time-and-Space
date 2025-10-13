/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

public interface IHelpWithPlayerMixin {
    String GetViewedTARDIS();

    void SetViewedTARDIS(String tardis);

    PlayerPosition GetLastPosition();

    void SetLastPlayerPosition(PlayerPosition playerPosition);
}
